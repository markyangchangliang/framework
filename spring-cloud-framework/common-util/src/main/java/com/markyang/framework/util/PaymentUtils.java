package com.markyang.framework.util;

import com.markyang.framework.pojo.constant.CacheConstants;
import com.markyang.framework.pojo.enumeration.payment.ServiceProviderEnum;
import com.markyang.framework.pojo.payment.PaymentDetails;
import com.markyang.framework.pojo.payment.RefundDetails;

import java.util.Objects;
import java.util.Optional;

/**
 * 支付工具类
 *
 * @author yangchangliang
 * @version 1
 */
public final class PaymentUtils {

    /**
     * 获取支付详情
     * @param paymentId 支付ID
     * @return 支付详情Optional对象
     */
    public static Optional<PaymentDetails> getPaymentDetails(String paymentId) {
        PaymentDetails paymentDetails = RedisUtils.get(CacheConstants.PAYMENT_DETAILS_CACHE_KEY_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR + paymentId);
        if (Objects.isNull(paymentDetails)) {
            return Optional.empty();
        }
        return Optional.of(paymentDetails);
    }

    /**
     * 获取退款详情
     * @param refundId 退款ID
     * @return 退款详情Optional对象
     */
    public static Optional<RefundDetails> getRefundDetails(String refundId) {
        RefundDetails refundDetails = RedisUtils.get(CacheConstants.REFUND_DETAILS_CACHE_KEY_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR + refundId);
        if (Objects.isNull(refundDetails)) {
            return Optional.empty();
        }
        return Optional.of(refundDetails);
    }

    /**
     * 获取支付业务标识符
     * @param paymentId 支付ID
     * @return 业务标识符Optional对象
     */
    public static Optional<String> getPaymentBusinessKey(String paymentId) {
        return getPaymentDetails(paymentId).flatMap(paymentDetails -> Optional.ofNullable(paymentDetails.getBusinessKey()));
    }

    /**
     * 获取商户标识符
     * @param paymentOrRefundId 支付或退款ID
     * @return 商户标识符
     */
    public static Optional<String> getOrgId(String paymentOrRefundId) {
        return getPaymentDetails(paymentOrRefundId)
            .map(paymentDetails -> Optional.ofNullable(paymentDetails.getOrgId()))
            .orElseGet(() -> getRefundDetails(paymentOrRefundId)
                .flatMap(refundDetails -> Optional.ofNullable(refundDetails.getOrgId())));
    }

    /**
     * 获取服务提供商
     * @param paymentId 支付ID
     * @return 服务提供商
     */
    public static Optional<ServiceProviderEnum> getServiceProvider(String paymentId) {
        return getPaymentDetails(paymentId).flatMap(paymentDetails -> Optional.ofNullable(paymentDetails.getServiceProvider()));
    }
}
