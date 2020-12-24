package com.markyang.framework.app.base.aop;

import com.markyang.framework.app.base.annotation.PaymentRequest;
import com.markyang.framework.app.base.exception.PaymentRequestException;
import com.markyang.framework.pojo.constant.CacheConstants;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.web.ResultVo;
import com.markyang.framework.service.payment.CompositePaymentService;
import com.markyang.framework.service.payment.PaymentService;
import com.markyang.framework.util.ClassOperationUtils;
import com.markyang.framework.util.RedisUtils;
import com.markyang.framework.util.TypeCastUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * 支付请求增强
 * @author yangchangliang
 */
@Component
@ConditionalOnBean(CompositePaymentService.class)
@Aspect
@Slf4j
@Order(3)
public class PaymentRequestHandlerAop {

	@Pointcut("execution(* com.zxrj.framework.app..*.*(..)) " +
		"&& @annotation(com.markyang.framework.app.base.annotation.PaymentRequest)")
	public void pointcut() {}

	@Around("pointcut()")
	public Object paying(ProceedingJoinPoint joinPoint) throws Throwable {
		if (!(joinPoint.getSignature() instanceof MethodSignature)) {
			// 不是方法代理，抛出错误
			throw new PaymentRequestException("只能并发控制代理的方法");
		}
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		PaymentRequest paymentRequest = method.getDeclaredAnnotation(PaymentRequest.class);

		String signature = joinPoint.getTarget().getClass().getName() + "." + method.getName();

		if (StringUtils.isBlank(paymentRequest.businessKey())) {
			throw new PaymentRequestException("方法[" + signature + "]注解@PaymentRequest中businessKey业务标识符必须提供");
		}
		// 处理Redis缓存
		String key = this.getLockingKey(signature);
		String cacheKey = CacheConstants.PAYMENT_INFO_CACHE_KEY_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR +
			paymentRequest.businessKey() + CacheConstants.CACHE_KEY_SEPARATOR + key;
		Map<String, Object> paymentInfo = RedisUtils.get(cacheKey);

		if (Objects.nonNull(paymentInfo)) {
			return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, paymentInfo);
		}

		// 不存在缓存
		Object result = joinPoint.proceed();
		if (!(result instanceof ResultVo)) {
			throw new PaymentRequestException("支付请求方法[" + signature + "]返回PaymentInfo必须是ResultVo<Map<String, Object>>类型");
		}
		ResolvableType resolvableType = ResolvableType.forMethodReturnType(method).getGeneric(0);
		Object data = ((ResultVo<?>) result).getData();
		if (!(data instanceof Map) || !ClassOperationUtils.isStringClass(resolvableType.getGeneric(0).resolve())
			|| !ClassOperationUtils.isObjectClass(resolvableType.getGeneric(1).resolve())) {
			throw new PaymentRequestException("支付请求方法[" + signature + "]返回PaymentInfo必须是Map<String, Object>类型");
		}
		// 拿到paymentId
		String paymentId = MapUtils.getString(TypeCastUtils.cast(data), PaymentService.PAYMENT_ID_KEY, "");
		if (StringUtils.isBlank(paymentId)) {
			// 没有PaymentId
			throw new PaymentRequestException("支付请求方法[" + signature + "]返回PaymentInfo中不存在支付ID：" + PaymentService.PAYMENT_ID_KEY);
		}
		// 将cacheKey缓存到Redis
		RedisUtils.setString(CacheConstants.PAYMENT_INFO_CACHE_KEY_CACHE_KEY_PREFIX +
			CacheConstants.CACHE_KEY_SEPARATOR + paymentId, cacheKey, CacheConstants.PAYMENT_DURATION_TIME);
		// 加入缓存
		RedisUtils.set(cacheKey, data, CacheConstants.PAYMENT_DURATION_TIME);
		return result;
	}

	/**
	 * 获取支付锁定唯一记录的Key
	 * @param signature 方法签名字符串
	 * @return key
	 */
	private String getLockingKey(String signature) {
		if (Objects.isNull(RequestContextHolder.getRequestAttributes())) {
			throw new PaymentRequestException("HttpServletRequest对象不存在，请考虑是否在Web环境下？");
		}
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Object lockingKey = request.getAttribute(ConcurrenceLockingAop.LOCKING_KEY_ATTR_KEY);
		if (Objects.isNull(lockingKey) || StringUtils.isBlank((CharSequence) lockingKey)) {
			throw new PaymentRequestException("支付请求找不到LockingKey，请检查方法[" + signature + "]是否存在注解@RedisLock和方法参数上存在@LockingKey注解");
		}
		return (String) lockingKey;
	}

}
