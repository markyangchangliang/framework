package com.markyang.framework.service.payment;

import com.markyang.framework.pojo.payment.PaymentQueryResult;

import java.io.Serializable;

/**
 * 支付状态查询回调接口
 *
 * @author yangchangliang
 * @version 1
 */
public interface PaymentQueryCallback extends PaymentCallbackIdentifier, Serializable {

    /**
     * 处理逻辑
     * @param paymentQueryResult 查询结果对象
     */
    void invoke(PaymentQueryResult paymentQueryResult);
}
