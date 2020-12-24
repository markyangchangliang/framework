package com.markyang.framework.pojo.payment;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 退款结果
 *
 * @author yangchangliang
 * @version 1
 */
@Data
@Builder
public class RefundCreationResult implements Serializable {

    private static final long serialVersionUID = -4016983958996722018L;

    /**
     * 支付ID
     */
    private String paymentId;

    /**
     * 服务提供商支付ID
     */
    private String spPaymentId;

    /**
     * 退款ID
     */
    private String refundId;

    /**
     * 服务提供商退款ID
     */
    private String spRefundId;

    /**
     * 支付金额
     */
    private BigDecimal totalFee;

    /**
     * 退款金额
     */
    private BigDecimal refundFee;

    /**
     * 额外信息
     */
    @Singular(value = "additionalInformation", ignoreNullCollections = true)
    private Map<String, Object> additionalInformation;
}
