package com.markyang.framework.pojo.payment;

import com.markyang.framework.pojo.enumeration.payment.ServiceProviderEnum;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 退款详情
 *
 * @author yangchangliang
 * @version 1
 */
@Data
@Builder
public class RefundDetails implements Serializable {

    private static final long serialVersionUID = -5180152043939234068L;

    /**
     * 支付ID
     */
    private String paymentId;

    /**
     * 服务提供商支付ID
     */
    private String spPaymentId;

    /**
     * 退款人
     */
    private String refundedBy;

    /**
     * 订单金额
     */
    private BigDecimal totalFee;

    /**
     * 退款金额
     */
    private BigDecimal refundFee;

    /**
     * 退款原因
     */
    private String reason;

    /**
     * 业务标识符（可选）
     */
    private String businessKey;

    /**
     * 业务主键（可选）
     */
    private String businessId;

    /**
     * 机构ID
     */
    private String orgId;

    /**
     * 服务提供商
     */
    private ServiceProviderEnum serviceProvider;

    /**
     * 额外信息
     */
    @Singular(value = "additionalInformation", ignoreNullCollections = true)
    private Map<String, Object> additionalInformation;

}
