package com.markyang.framework.app.base.annotation;

import java.lang.annotation.*;

/**
 * 支付成功处理注解
 *
 * @author yangchangliang
 * @version 1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PaymentSuccessRequest {
}
