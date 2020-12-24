package com.markyang.framework.app.base.annotation;

import java.lang.annotation.*;

/**
 * 支付ID注解
 *
 * @author yangchangliang
 * @version 1
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PaymentId {

    /**
     * 对象的某一个字段
     * @return 字段名称
     */
    String field() default "";
}
