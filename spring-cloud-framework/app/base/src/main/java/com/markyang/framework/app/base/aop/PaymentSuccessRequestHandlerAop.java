package com.markyang.framework.app.base.aop;

import com.markyang.framework.app.base.annotation.PaymentId;
import com.markyang.framework.app.base.exception.PaymentRequestException;
import com.markyang.framework.pojo.enumeration.payment.PaymentStatusEnum;
import com.markyang.framework.pojo.payment.PaymentQueryResult;
import com.markyang.framework.service.payment.CompositePaymentService;
import com.markyang.framework.util.PaymentUtils;
import com.markyang.framework.util.ReflectionOperationUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 支付成功处理请求增强
 * @author yangchangliang
 */
@Component
@ConditionalOnBean(CompositePaymentService.class)
@AllArgsConstructor
@Aspect
@Slf4j
@Order(3)
public class PaymentSuccessRequestHandlerAop {

	private final CompositePaymentService compositePaymentService;

	@Pointcut("execution(* com.markyang.framework.app..*.*(..)) " +
		"&& @annotation(com.markyang.framework.app.base.annotation.PaymentSuccessRequest)")
	public void pointcut() {}

	@Around("pointcut()")
	public Object paying(ProceedingJoinPoint joinPoint) throws Throwable {
		if (!(joinPoint.getSignature() instanceof MethodSignature)) {
			// 不是方法代理，抛出错误
			throw new PaymentRequestException("只能并发控制代理的方法");
		}
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();

		String signature = joinPoint.getTarget().getClass().getName() + "." + method.getName();
		String paymentId = this.extractPaymentId(method, joinPoint.getArgs(), signature);
		// 处理执行方法前的检测
		PaymentQueryResult paymentQueryResult = this.compositePaymentService.queryPayment(paymentId, null, PaymentUtils.getServiceProvider(paymentId).orElse(null));
		if (Objects.equals(paymentQueryResult.getPaymentStatus(), PaymentStatusEnum.PAID_SUCCESS)) {
			// 支付成功了，开始处理业务逻辑
			return joinPoint.proceed();
		}
		// 支付失败返回错误信息
		log.error("有人支付未成功[{}]，但企图调用支付成功处理接口", paymentQueryResult.getPaymentStatus());
		throw new PaymentRequestException("支付未成功，请稍后再试");
	}

	/**
	 * 抽取支付ID
	 * @param method 连接方法
	 * @param args 方法参数
	 * @param signature 方法签名信息
	 * @return 注解信息
	 */
	private String extractPaymentId(Method method, Object[] args, String signature) {
		ReflectionOperationUtils.ParameterAnnotation<PaymentId> paymentIdParameterAnnotation =
			ReflectionOperationUtils.getMethodParameterAnnotation(method, PaymentId.class)
			.orElseThrow(() -> new PaymentRequestException("支付成功处理请求方法[" + signature + "]必须要有带有注解@PaymentId的参数"));

		Object arg = args[paymentIdParameterAnnotation.getParameterIndex()];
		String field = paymentIdParameterAnnotation.getAnnotation().field();
		if (StringUtils.isBlank(field)) {
			// 直接获取arg
			if (arg instanceof String) {
				return (String) arg;
			}
			throw new PaymentRequestException("支付成功处理请求方法[" + signature + "]参数[" + arg + "]" +
				"不是一个字符串或为NULL，如果@PaymentId注解的是一个对象参数，那么请指定该注解的field参数来指定是哪一个对象属性充当paymentId值");
		}
		// 对象获取值
		return ReflectionOperationUtils.getFieldValue(field, arg)
			.map(String.class::cast)
			.orElseThrow(() -> new PaymentRequestException("支付成功处理请求方法[" + signature + "]参数[" + arg + "]对象中获取不到指定属性[" + field + "]的paymentId值"));
	}

}
