package com.markyang.framework.app.base.aop;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.markyang.framework.app.base.annotation.ApplyPostGetHook;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.entity.BaseEntity;
import com.markyang.framework.pojo.web.PageVo;
import com.markyang.framework.service.core.service.hook.ServiceHook;
import com.markyang.framework.service.core.service.support.HookContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 钩子应用Aop
 * @author yangchangliang
 */
@Component
@Aspect
@Slf4j
@AllArgsConstructor
public class PostGetHookApplyAop {

	private final List<ServiceHook> serviceHooks;

	@Pointcut("execution(* com.markyang.framework.app..*(..)) " +
		"&& @annotation(com.markyang.framework.app.base.annotation.ApplyPostGetHook)")
	public void condition() {}

	@AfterReturning(value = "condition()", returning = "result")
	public Object applyHook(JoinPoint joinPoint, Object result) {
		if (!(joinPoint.getSignature() instanceof MethodSignature)) {
			// 不是方法代理
			return result;
		}
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		if (!method.isAnnotationPresent(ApplyPostGetHook.class)) {
			return result;
		}
		// 获取注解信息
		ApplyPostGetHook applyPostGetHook = method.getAnnotation(ApplyPostGetHook.class);
		if (result instanceof IPage) {
			// 分页对象
			this.handlePageResult((IPage<?>) result, method, applyPostGetHook);
		} else if (result instanceof PageVo) {
			this.handlePageVoResult((PageVo<?>) result, method, applyPostGetHook);
		} else if (result instanceof Collection) {
			this.handleCollectionResult((Collection<?>) result, method, applyPostGetHook);
		} else if (result instanceof Map) {
			this.handleMapResult((Map<?, ?>) result, method, applyPostGetHook);
		} else {
			// 单个对象
			if (this.isApplicableObjectClass(result.getClass())) {
				this.doApplyHook(Lists.newArrayList(result), applyPostGetHook);
			}
		}
		return result;
	}

	/**
	 * 找到需要应用的钩子
	 * @param applyPostGetHook 注解信息
	 * @return 钩子
	 */
	private List<ServiceHook> resolveApplicableHooks(ApplyPostGetHook applyPostGetHook) {
		if (ArrayUtils.isNotEmpty(applyPostGetHook.includes())) {
			// 只应用这几个钩子
			return this.serviceHooks.parallelStream()
				.filter(serviceHook -> ArrayUtils.contains(applyPostGetHook.includes(), serviceHook.getClass()))
				.collect(Collectors.toList());
		} else if (ArrayUtils.isNotEmpty(applyPostGetHook.excludes())) {
			// 排除这几个钩子
			return this.serviceHooks.parallelStream()
				.filter(serviceHook -> !ArrayUtils.contains(applyPostGetHook.excludes(), serviceHook.getClass()))
				.collect(Collectors.toList());
		} else {
			// 应用所有钩子
			return this.serviceHooks;
		}
	}

	/**
	 * 应用钩子
	 * @param objects 对象列表
	 * @param applyPostGetHook 注解信息
	 */
	private void doApplyHook(Collection<?> objects, ApplyPostGetHook applyPostGetHook) {
		List<ServiceHook> serviceHooks = this.resolveApplicableHooks(applyPostGetHook);
		HookContext hookContext = HookContext.of();
		for (Object object : objects) {
			for (ServiceHook serviceHook : serviceHooks) {
				serviceHook.postGet(object, hookContext);
			}
		}
	}

	/**
	 * 判断是否是可应用钩子的对象
	 * @param clazz 类
	 * @return bool
	 */
	private boolean isApplicableObjectClass(Class<?> clazz) {
		return Objects.nonNull(clazz) && (ClassUtils.isAssignable(clazz, BaseEntity.class)
			|| StringUtils.endsWith(clazz.getSimpleName(), FrameworkConstants.DATA_TRANSFER_OBJECT_CLASS_SUFFIX));
	}

	/**
	 * 处理分页结果对象
	 * @param result 结果对象
	 * @param method 方法对象
	 * @param applyPostGetHook 注解信息
	 */
	private void handlePageResult(IPage<?> result, Method method, ApplyPostGetHook applyPostGetHook) {
		Class<?> clazz = ResolvableType.forMethodReturnType(method).getGeneric(0).resolve();
		if (this.isApplicableObjectClass(clazz)) {
			this.doApplyHook(result.getRecords(), applyPostGetHook);
		}
	}

	/**
	 * 处理分页结果对象
	 * @param result 结果对象
	 * @param method 方法对象
	 * @param applyPostGetHook 注解信息
	 */
	private void handlePageVoResult(PageVo<?> result, Method method, ApplyPostGetHook applyPostGetHook) {
		Class<?> clazz = ResolvableType.forMethodReturnType(method).getGeneric(0).resolve();
		if (this.isApplicableObjectClass(clazz)) {
			this.doApplyHook(result.getContent(), applyPostGetHook);
		}
	}

	/**
	 * 处理集合结果对象
	 * @param result 结果对象
	 * @param method 方法对象
	 * @param applyPostGetHook 注解信息
	 */
	private void handleCollectionResult(Collection<?> result, Method method, ApplyPostGetHook applyPostGetHook) {
		Class<?> clazz = ResolvableType.forMethodReturnType(method).getGeneric(0).resolve();
		if (this.isApplicableObjectClass(clazz)) {
			this.doApplyHook(result, applyPostGetHook);
		}
	}

	/**
	 * 处理Map结果对象
	 * @param result 结果对象
	 * @param method 方法对象
	 * @param applyPostGetHook 注解信息
	 */
	private void handleMapResult(Map<?, ?> result, Method method, ApplyPostGetHook applyPostGetHook) {
		Class<?> clazz = ResolvableType.forMethodReturnType(method).getGeneric(0).resolve();
		if (isApplicableObjectClass(clazz)) {
			this.doApplyHook(result.keySet(), applyPostGetHook);
		}
		clazz = ResolvableType.forMethodReturnType(method).getGeneric(1).resolve();
		if (isApplicableObjectClass(clazz)) {
			this.doApplyHook(result.values(), applyPostGetHook);
		}
	}
}
