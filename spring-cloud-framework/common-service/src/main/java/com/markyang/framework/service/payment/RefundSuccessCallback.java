package com.markyang.framework.service.payment;

import com.markyang.framework.pojo.payment.RefundResult;

import java.io.Serializable;

/**
 * 退款成功回调接口
 *
 * @author yangchangliang
 * @version 1
 */
public interface RefundSuccessCallback extends PaymentCallbackIdentifier, Serializable {

    /**
     * 处理逻辑
     * @param refundResult 退款结果对象
     */
    void invoke(RefundResult refundResult);
}
