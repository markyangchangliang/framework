package com.markyang.framework.pojo.payment;

import com.markyang.framework.pojo.enumeration.payment.ClientTypeEnum;
import com.markyang.framework.pojo.enumeration.payment.ServiceProviderEnum;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 支付详情信息
 *
 * @author yangchangliang
 * @version 1
 */
@Data
@Builder
public class PaymentDetails implements Serializable {

    private static final long serialVersionUID = -2928816887663299676L;

    /**
     * 支付人ID
     */
    private String payerId;

    /**
     * 支付标题
     */
    private String title;

    /**
     * 支付金额
     */
    private BigDecimal totalFee;

    /**
     * 终端IP
     */
    private String clientIp;

    /**
     * 终端类型
     */
    private ClientTypeEnum clientType;

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
