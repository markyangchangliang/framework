package com.markyang.framework.service.payment;

import com.markyang.framework.pojo.payment.RefundQueryResult;

import java.io.Serializable;

/**
 * 退款状态查询回调接口
 *
 * @author yangchangliang
 * @version 1
 */
public interface RefundQueryCallback extends PaymentCallbackIdentifier, Serializable {

    /**
     * 处理逻辑
     * @param refundQueryResult 查询结果对象
     */
    void invoke(RefundQueryResult refundQueryResult);
}
