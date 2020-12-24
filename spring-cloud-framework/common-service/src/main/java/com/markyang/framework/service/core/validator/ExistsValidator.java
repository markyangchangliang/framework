package com.markyang.framework.service.core.validator;

import com.markyang.framework.service.core.ReflectionCache;
import com.markyang.framework.service.core.validator.annotation.Exists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 记录是否存在验证器
 * @author yangchangliang
 */
public class ExistsValidator implements ConstraintValidator<Exists, Long>, ApplicationContextAware {

	/**
	 * 判断存在的方法名称
	 */
	public static final String EXISTS_METHOD = "exists";
	private String serviceName;

	private Class<?> service;

	private ApplicationContext applicationContext;

	/**
	 * Initializes the validator in preparation for
	 * {@link #(Object, ConstraintValidatorContext)} calls.
	 * The constraint annotation for a given constraint declaration
	 * is passed.
	 * <p>
	 * This method is guaranteed to be called before any use of this instance for
	 * validation.
	 * <p>
	 * The default implementation is a no-op.
	 *
	 * @param constraintAnnotation annotation instance for a given constraint declaration
	 */
	@Override
	public void initialize(Exists constraintAnnotation) {
		this.serviceName = constraintAnnotation.serviceName();
		this.service = constraintAnnotation.service();
	}

	/**
	 * Implements the validation logic.
	 * The state of {@code value} must not be altered.
	 * <p>
	 * This method can be accessed concurrently, thread-safety must be ensured
	 * by the implementation.
	 *
	 * @param value   object to validate
	 * @param context context in which the constraint is evaluated
	 * @return {@code false} if {@code value} does not pass the constraint
	 */
	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		if (value == null) {
			return false;
		}
		Object instance;
		if (StringUtils.isNotBlank(this.serviceName)) {
			instance = this.applicationContext.getBean(this.serviceName, this.service);
		} else {
			instance = this.applicationContext.getBean(this.service);
		}
		// 调用service的exist方法
		Method method;
		Optional<Method> cachedMethod = ReflectionCache.getCachedMethod(this.service, EXISTS_METHOD);
		if (!cachedMethod.isPresent()) {
			method = ReflectionUtils.findMethod(this.service, EXISTS_METHOD, Object.class);
			if (method == null) {
				// 找不到exist方法
				return false;
			}
			// 缓存
			ReflectionCache.cacheMethod(this.service, method);
		} else {
			method = cachedMethod.get();
		}

		Object result = ReflectionUtils.invokeMethod(method, instance, value);
		if (result instanceof Boolean) {
			return (Boolean) result;
		}
		return false;
	}

	@Override
	public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
