package com.markyang.framework.pojo.payment;

import com.markyang.framework.pojo.enumeration.payment.RefundStatusEnum;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 退款查询结果
 *
 * @author yangchangliang
 * @version 1
 */
@Data
@Builder
public class RefundQueryResult implements Serializable {

    private static final long serialVersionUID = 6548911747644864184L;

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
     * 退款状态
     */
    private RefundStatusEnum refundStatus;

    /**
     * 退款状态描述
     */
    private String refundStatusDescription;

    /**
     * 退款完成时间
     */
    private LocalDateTime finishedDateTime;

    /**
     * 业务标识符
     */
    private String businessKey;

    /**
     * 业务主键（可选）
     */
    private String businessId;

    /**
     * 额外信息
     */
    @Singular(value = "additionalInformation", ignoreNullCollections = true)
    private Map<String, Object> additionalInformation;
}
