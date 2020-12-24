package com.markyang.framework.pojo.payment;

import com.markyang.framework.pojo.enumeration.payment.ClientTypeEnum;
import com.markyang.framework.pojo.enumeration.payment.PaymentStatusEnum;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 支付查询结果
 *
 * @author yangchangliang
 * @version 1
 */
@Data
@Builder
public class PaymentQueryResult implements Serializable {

    private static final long serialVersionUID = 7753318882771113263L;

    /**
     * 支付ID
     */
    private String paymentId;

    /**
     * 服务提供商支付ID
     */
    private String spPaymentId;

    /**
     * 支付终端类型
     */
    private ClientTypeEnum clientType;

    /**
     * 支付状态
     */
    private PaymentStatusEnum paymentStatus;

    /**
     * 支付状态描述
     */
    private String paymentStatusDescription;

    /**
     * 支付完成时间
     */
    private LocalDateTime finishedDateTime;

    /**
     * 支付金额
     */
    private BigDecimal totalFee;

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
