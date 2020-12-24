package com.markyang.framework.service.payment;

import com.markyang.framework.pojo.enumeration.payment.ServiceProviderEnum;
import com.markyang.framework.pojo.payment.*;

/**
 * 统一支付接口
 *
 * @author yangchangliang
 * @version 1
 */
public interface PaymentService {

    /**
     * 支付ID键
     */
    String PAYMENT_ID_KEY = "paymentId";

    /**
     * 商户ID键
     */
    String MERCHANT_ID_KEY = "merchantId";

    /**
     * 创建支付
     * @param paymentDetails 支付信息
     * @return 结果
     */
    PaymentCreationResult createPayment(PaymentDetails paymentDetails);

    /**
     * 关闭支付
     * @param paymentId 支付ID
     * @param spPaymentId 服务提供商支付ID
     * @param serviceProvider 服务提供商
     * @return 结果
     */
    PaymentClosureResult closePayment(String paymentId, String spPaymentId, ServiceProviderEnum serviceProvider);

    /**
     * 查询支付信息
     * @param paymentId 支付ID
     * @param spPaymentId 服务提供商支付ID
     * @param serviceProvider 服务提供商
     * @return 结果
     */
    PaymentQueryResult queryPayment(String paymentId, String spPaymentId, ServiceProviderEnum serviceProvider);

    /**
     * 创建退款
     * @param refundDetails 退款信息
     * @return 结果
     */
    RefundCreationResult createRefund(RefundDetails refundDetails);

    /**
     * 查询退款
     * @param paymentId 支付ID
     * @param spPaymentId 服务提供商支付ID
     * @param refundId 退款ID
     * @param spRefundId 服务提供商退款ID
     * @param serviceProvider 服务提供商
     * @return 结果
     */
    RefundQueryResult queryRefund(String paymentId, String spPaymentId, String refundId, String spRefundId, ServiceProviderEnum serviceProvider);

    /**
     * 撤销支付
     * @param paymentId 支付ID
     * @param spPaymentId 服务提供商支付ID
     * @param serviceProvider 服务提供商
     * @return 结果
     */
    PaymentReversionResult reversePayment(String paymentId, String spPaymentId, ServiceProviderEnum serviceProvider);

    /**
     * 获取定时任务触发器名称前缀
     * @param serviceProvider 服务提供商
     * @return 名称前缀
     */
    String getScheduleTaskTriggerNamePrefix(ServiceProviderEnum serviceProvider);

    /**
     * 获取定时任务触发器组名称
     * @param serviceProvider 服务提供商
     * @return 组名称
     */
    String getScheduleTaskTriggerGroupName(ServiceProviderEnum serviceProvider);

    /**
     * 是否支持支付方式
     * @param serviceProvider 支付方式
     * @return bool
     */
    boolean support(ServiceProviderEnum serviceProvider);

    /**
     * 根据支付ID获取机构ID
     * @param paymentId 支付ID
     * @param serviceProvider 服务提供商
     * @return 机构ID
     */
    String getOrgId(String paymentId, ServiceProviderEnum serviceProvider);

}
