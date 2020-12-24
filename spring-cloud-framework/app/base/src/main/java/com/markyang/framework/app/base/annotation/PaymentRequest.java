package com.markyang.framework.app.base.annotation;

import java.lang.annotation.*;

/**
 * 支付请求
 *
 * @author yangchangliang
 * @version 1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PaymentRequest {

    /**
     * 业务标识符
     * @return 业务标识符
     */
    String businessKey();
}
