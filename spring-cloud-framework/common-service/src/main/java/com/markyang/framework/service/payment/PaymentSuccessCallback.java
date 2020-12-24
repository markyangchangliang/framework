package com.markyang.framework.service.payment;

import com.markyang.framework.pojo.payment.PaymentResult;

import java.io.Serializable;

/**
 * 支付成功回调接口
 *
 * @author yangchangliang
 * @version 1
 */
public interface PaymentSuccessCallback extends PaymentCallbackIdentifier, Serializable {

    /**
     * 处理逻辑
     * @param paymentResult 支付结果对象
     */
    void invoke(PaymentResult paymentResult);
}
