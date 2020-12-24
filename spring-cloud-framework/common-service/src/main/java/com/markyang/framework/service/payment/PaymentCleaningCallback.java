package com.markyang.framework.service.payment;

import java.io.Serializable;

/**
 * 支付清理回调
 *
 * @author yangchangliang
 * @version 1
 */
public interface PaymentCleaningCallback extends PaymentCallbackIdentifier, Serializable {

    /**
     * 清理未支付的废品支付业务订单的记录
     * @param paymentId 支付ID
     * @param businessKey 业务Key
     * @param businessId 业务ID
     */
    void clean(String paymentId, String businessKey, String businessId);
}
