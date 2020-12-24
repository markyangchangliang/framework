package com.markyang.framework.service.core.validator.aop;

import com.markyang.framework.service.core.form.BaseForm;
import com.markyang.framework.service.core.validator.core.FormValidate;
import com.markyang.framework.service.exception.CrudValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * 表单验证
 * @author yangchangliang
 */
@Component
@Order(1)
@Aspect
@Slf4j
@AllArgsConstructor
public class FormValidatorAop implements InitializingBean {

	private static Validator VALIDATOR;

	private final ValidatorFactory validatorFactory;

	@Pointcut("@target(org.springframework.web.bind.annotation.RestController) && @annotation(com.markyang.framework.service.core.validator.core.FormValidate)")
	public void condition() {}

	@Around("condition()")
	public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {
		if (!(joinPoint.getSignature() instanceof MethodSignature)) {
			// 不是方法代理
			return joinPoint.proceed();
		}
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		Class<?>[] groups = method.getDeclaredAnnotation(FormValidate.class).value();
		Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			if (arg instanceof BaseForm) {
				Set<ConstraintViolation<Object>> constraintViolations = VALIDATOR.validate(arg, groups);
				if (constraintViolations.size() > 0) {
					// 有错误
					throw new CrudValidationException(constraintViolations.iterator().next().getMessage());
				}
			}
		}
		// 验证通过
		return joinPoint.proceed(args);
	}

	/**
	 * Invoked by the containing {@code BeanFactory} after it has set all bean properties
	 * and satisfied {@link BeanFactoryAware}, {@code ApplicationContextAware} etc.
	 * <p>This method allows the bean instance to perform validation of its overall
	 * configuration and final initialization when all bean properties have been set.
	 *
	 * @throws Exception in the event of misconfiguration (such as failure to set an
	 *                   essential property) or if initialization fails for any other reason
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// 获取validator
		VALIDATOR = this.validatorFactory.getValidator();
	}
}
