package com.markyang.framework.pojo.payment;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.io.Serializable;
import java.util.Map;

/**
 * 支付撤销结果
 *
 * @author yangchangliang
 * @version 1
 */
@Data
@Builder
public class PaymentReversionResult implements Serializable {

    private static final long serialVersionUID = -9207045021076454453L;

    /**
     * 结果
     */
    private String result;

    /**
     * 额外信息
     */
    @Singular(value = "additionalInformation", ignoreNullCollections = true)
    private Map<String, Object> additionalInformation;
}
