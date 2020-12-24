package com.markyang.framework.pojo.payment;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.io.Serializable;
import java.util.Map;

/**
 * 支付创建结果
 *
 * @author yangchangliang
 * @version 1
 */
@Data
@Builder
public class PaymentCreationResult implements Serializable {

    private static final long serialVersionUID = -8270610567855766799L;

    /**
     * 预支付ID
     */
    private String prepaymentId;

    /**
     * 支付ID
     */
    private String paymentId;

    /**
     * 额外信息
     */
    @Singular(value = "additionalInformation", ignoreNullCollections = true)
    private Map<String, Object> additionalInformation;
}
