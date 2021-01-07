package com.markyang.framework.app.base.aop;

import com.markyang.framework.app.base.annotation.LockingKey;
import com.markyang.framework.app.base.annotation.RedisLock;
import com.markyang.framework.app.base.cache.redis.key.FrameworkKeyGenerator;
import com.markyang.framework.app.base.exception.ConcurrenceLockingException;
import com.markyang.framework.pojo.constant.CacheConstants;
import com.markyang.framework.util.RedisLockUtils;
import com.markyang.framework.util.ReflectionOperationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 并发控制
 * @author yangchangliang
 */
@Component
@Aspect
@Slf4j
@Order(2)
public class ConcurrenceLockingAop {

	/**
	 * 存放锁key值的键值
	 */
	public static final String LOCKING_KEY_ATTR_KEY = "REDIS_LOCK_LOCKING_KEY";

	@Pointcut("execution(* com.markyang.framework.app..*.*(..)) " +
		"&& @annotation(com.markyang.framework.app.base.annotation.RedisLock)")
	public void pointcut() {}

	@Around("pointcut()")
	public Object locking(ProceedingJoinPoint joinPoint) throws Throwable {
		if (!(joinPoint.getSignature() instanceof MethodSignature)) {
			// 不是方法代理，抛出错误
			throw new ConcurrenceLockingException("只能并发控制代理的方法");
		}
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		Object[] args = joinPoint.getArgs();
		RedisLock redisLock = method.getDeclaredAnnotation(RedisLock.class);
		Object[] keyInfo = this.extractKeyInfo(method, args);
		// 处理 key
		String key = joinPoint.getTarget().getClass().getName() + "." + method.getName() +
			CacheConstants.CACHE_KEY_SEPARATOR + this.getLockingKey((LockingKey) keyInfo[0], keyInfo[1]);
		// 存储key 以备后续aop会用到
		this.storeLockingKeyInRequest(key);
		// 并发控制
		boolean acquired = RedisLockUtils.lock(key, redisLock.timeout(), redisLock.expiredTime());
		if (!acquired) {
			// 抢占锁失败
			throw new ConcurrenceLockingException("太挤了，缓缓再试吧");
		}
		// 抢锁成功，执行逻辑
		try {
			return joinPoint.proceed(args);
		} finally {
			// 释放锁
			RedisLockUtils.unlock(key);
		}
	}

	/**
	 * 抽取注解信息
	 * @param method 连接方法
	 * @param args 方法参数
	 * @return 注解信息
	 */
	private Object[] extractKeyInfo(Method method, Object[] args) {
		return ReflectionOperationUtils.getMethodParameterAnnotation(method, LockingKey.class)
			.map(lockingKeyParameterAnnotation ->
				new Object[] {lockingKeyParameterAnnotation.getAnnotation(),
					args[lockingKeyParameterAnnotation.getParameterIndex()]})
			.orElseThrow(() -> new ConcurrenceLockingException("锁标识不能为空"));
	}

	/**
	 * 存储锁键名
	 * @param key 键
	 */
	private void storeLockingKeyInRequest(String key) {
		if (RequestContextHolder.getRequestAttributes() == null) {
			throw new ConcurrenceLockingException("获取不到请求对象");
		}
		HttpServletRequest request = ((ServletRequestAttributes)
			RequestContextHolder.getRequestAttributes()).getRequest();
		request.setAttribute(LOCKING_KEY_ATTR_KEY, key);
	}

	/**
	 * 处理锁键值
	 * @param lockingKey 锁键值注解对象
	 * @param keyObj 锁目标对象
	 * @return 键名
	 */
	private String getLockingKey(LockingKey lockingKey, Object keyObj) {
		String key = "";
		String[] fieldNames = lockingKey.fields();
		String[] methodNames = lockingKey.methods();
		final Object finalKeyObj = keyObj;
		if (ArrayUtils.isNotEmpty(fieldNames)) {
			// 处理属性
			key = key + Arrays.stream(fieldNames).parallel()
				.map(fieldName -> ReflectionOperationUtils.getFieldValue(fieldName, finalKeyObj)
					.map(FrameworkKeyGenerator::convertToString)
					.orElse(null))
				.filter(Objects::nonNull)
				.collect(Collectors.joining("."));
		}
		if (ArrayUtils.isNotEmpty(methodNames)) {
			key = key + Arrays.stream(methodNames)
				.parallel()
				.map(methodName -> ReflectionOperationUtils.invokeMethod(methodName, finalKeyObj)
					.map(FrameworkKeyGenerator::convertToString)
					.orElse(null))
				.filter(Objects::nonNull)
				.collect(Collectors.joining("."));
		}
		if (StringUtils.isBlank(key)) {
			key = FrameworkKeyGenerator.convertToString(keyObj);
		}
		return key;
	}

}
