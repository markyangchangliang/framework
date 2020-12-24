package com.markyang.framework.service.payment;

import com.markyang.framework.pojo.constant.CacheConstants;
import com.markyang.framework.pojo.entity.payment.PaymentRecord;
import com.markyang.framework.pojo.enumeration.payment.PaymentStatusEnum;
import com.markyang.framework.pojo.enumeration.payment.RefundStatusEnum;
import com.markyang.framework.pojo.enumeration.payment.ServiceProviderEnum;
import com.markyang.framework.pojo.payment.PaymentResult;
import com.markyang.framework.pojo.payment.RefundDetails;
import com.markyang.framework.pojo.payment.RefundResult;
import com.markyang.framework.service.payment.service.PaymentRecordService;
import com.markyang.framework.util.RedisUtils;
import com.markyang.framework.util.ScheduleUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 支付回调控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/4/18 4:52 下午 星期六
 */
@Slf4j
@AllArgsConstructor
public abstract class PaymentCallbackController {

    private final PaymentRecordService paymentRecordService;
    private final CompositePaymentService compositePaymentService;
    private final List<PaymentSuccessCallback> paymentSuccessCallbacks;
    private final List<PaymentFailureCallback> paymentFailureCallbacks;
    private final List<RefundSuccessCallback> refundSuccessCallbacks;
    private final List<RefundFailureCallback> refundFailureCallbacks;

    /**
     * 调用支付成功处理回调
     *
     * @param paymentResult   支付结果对象
     * @param serviceProvider 服务提供商
     */
    protected void invokePaymentSuccessCallback(PaymentResult paymentResult, ServiceProviderEnum serviceProvider) {
        Optional<PaymentRecord> paymentRecordOptional = this.paymentRecordService.get(paymentResult.getPaymentId()).filter(paymentRecord -> Objects.equals(paymentRecord.getPaymentStatus(), PaymentStatusEnum.PAYING) || Objects.equals(paymentRecord.getPaymentStatus(), PaymentStatusEnum.WAITING_PAY));
        try {
            // 处理自定义的回调处理
            this.paymentSuccessCallbacks.parallelStream().filter(paymentSuccessCallback -> paymentSuccessCallback.support(serviceProvider, paymentResult.getBusinessKey())).forEach(paymentSuccessCallback -> paymentSuccessCallback.invoke(paymentResult));
        } catch (Exception e) {
            log.error("支付成功处理业务[businessKey={}, businessId={}]发生异常：{}", paymentResult.getBusinessKey(), paymentResult.getBusinessId(), e.getMessage());
            // 业务处理失败
            paymentRecordOptional.ifPresent(paymentRecord -> {
                // 说明业务表中的状态修改未成功 回撤订单
                this.compositePaymentService.createRefund(
                        RefundDetails.builder()
                                .businessId(paymentResult.getBusinessId())
                                .businessKey(paymentResult.getBusinessKey())
                                .paymentId(paymentResult.getPaymentId())
                                .spPaymentId(paymentResult.getSpPaymentId())
                                .serviceProvider(serviceProvider)
                                .orgId(paymentRecord.getOrgId())
                                .totalFee(paymentResult.getTotalFee())
                                .refundFee(paymentResult.getTotalFee())
                                .reason("处理支付成功业务逻辑异常")
                                .refundedBy("系统")
                                .build()
                );
            });
        } finally {
            // 更新支付记录表
            paymentRecordOptional.ifPresent(paymentRecord -> {
                paymentRecord.setSpPaymentId(paymentResult.getSpPaymentId());
                paymentRecord.setPaymentEndedTime(paymentResult.getFinishedDateTime());
                paymentRecord.setPaymentStatus(paymentResult.getPaymentStatus());
                this.paymentRecordService.update(paymentRecord);
            });
            // 执行公用逻辑
            this.handleCommonPaymentLogic(paymentResult, serviceProvider);
        }
    }

    /**
     * 调用支付失败处理回调
     *
     * @param paymentResult   支付结果对象
     * @param serviceProvider 服务提供商
     */
    protected void invokePaymentFailureCallback(PaymentResult paymentResult, ServiceProviderEnum serviceProvider) {
        try {
            // 处理自定义的回调处理
            this.paymentFailureCallbacks.parallelStream().filter(paymentFailureCallback -> paymentFailureCallback.support(serviceProvider, paymentResult.getBusinessKey())).forEach(paymentFailureCallback -> paymentFailureCallback.invoke(paymentResult));
        } catch (Exception e) {
            // 业务处理失败
            log.error("支付失败处理业务发生异常：{}", e.getMessage());
        } finally {
            // 更新支付记录表
            this.paymentRecordService.get(paymentResult.getPaymentId()).filter(paymentRecord -> Objects.equals(paymentRecord.getPaymentStatus(), PaymentStatusEnum.PAYING) || Objects.equals(paymentRecord.getPaymentStatus(), PaymentStatusEnum.WAITING_PAY)).ifPresent(paymentRecord -> {
                paymentRecord.setSpPaymentId(paymentResult.getSpPaymentId());
                paymentRecord.setPaymentEndedTime(paymentResult.getFinishedDateTime());
                paymentRecord.setPaymentStatus(paymentResult.getPaymentStatus());
                this.paymentRecordService.update(paymentRecord);
            });
            // 撤销订单
            this.compositePaymentService.reversePayment(paymentResult.getPaymentId(), paymentResult.getSpPaymentId(), serviceProvider);
            // 执行公用逻辑
            this.handleCommonPaymentLogic(paymentResult, serviceProvider);
        }
    }

    /**
     * 处理支付公用逻辑
     *
     * @param paymentResult   支付结果对象
     * @param serviceProvider 服务提供商
     */
    private void handleCommonPaymentLogic(PaymentResult paymentResult, ServiceProviderEnum serviceProvider) {
        ScheduleUtils.cancelSchedule(this.compositePaymentService.getScheduleTaskTriggerNamePrefix(serviceProvider) + paymentResult.getPaymentId(), this.compositePaymentService.getScheduleTaskTriggerGroupName(serviceProvider));
        ScheduleUtils.cancelSchedule(this.compositePaymentService.getScheduleTaskTriggerNamePrefix(serviceProvider) + "cleaning_" + paymentResult.getPaymentId(), this.compositePaymentService.getScheduleTaskTriggerGroupName(serviceProvider));
        // 清除Redis支付信息
        String paymentInfoCacheKey = CacheConstants.PAYMENT_INFO_CACHE_KEY_CACHE_KEY_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR + paymentResult.getPaymentId();
        RedisUtils.remove(RedisUtils.getString(paymentInfoCacheKey, ""));
        RedisUtils.remove(paymentInfoCacheKey);
        // 清除支付详情信息
        // RedisUtils.remove(CacheConstants.PAYMENT_DETAILS_CACHE_KEY_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR + paymentResult.getPaymentId());
    }

    /**
     * 调用退款成功处理回调
     *
     * @param refundResult    退款结果对象
     * @param serviceProvider 服务提供商
     */
    protected void invokeRefundSuccessCallback(RefundResult refundResult, ServiceProviderEnum serviceProvider) {
        log.info("退款成功：{}", refundResult);
        try {
            // 处理自定义的回调处理
            // 自定义处理
            this.refundSuccessCallbacks.parallelStream().filter(refundSuccessCallback -> refundSuccessCallback.support(serviceProvider, refundResult.getBusinessKey())).forEach(refundSuccessCallback -> refundSuccessCallback.invoke(refundResult));
        } catch (Exception e) {
            // 业务处理失败
            log.error("退款成功处理业务发生异常：{}", e.getMessage());
        } finally {
            // 执行公用逻辑
            this.handleCommonRefundLogic(refundResult, serviceProvider);
        }

    }

    /**
     * 调用退款失败处理回调
     *
     * @param refundResult    退款结果对象
     * @param serviceProvider 服务提供商
     */
    protected void invokeRefundFailureCallback(RefundResult refundResult, ServiceProviderEnum serviceProvider) {
        log.error("退款失败：{}", refundResult);
        try {
            // 处理自定义的回调处理
            // 自定义处理
            this.refundFailureCallbacks.parallelStream().filter(refundFailureCallback -> refundFailureCallback.support(serviceProvider, refundResult.getBusinessKey())).forEach(refundFailureCallback -> refundFailureCallback.invoke(refundResult));
        } catch (Exception e) {
            // 业务处理失败
            log.error("退款失败处理业务发生异常：{}", e.getMessage());
        } finally {
            // 执行公用逻辑
            this.handleCommonRefundLogic(refundResult, serviceProvider);
        }

    }

    /**
     * 退款公用逻辑
     *
     * @param refundResult    退款结果对象
     * @param serviceProvider 服务提供商
     */
    private void handleCommonRefundLogic(RefundResult refundResult, ServiceProviderEnum serviceProvider) {
        // 更新支付记录表
        this.paymentRecordService.get(refundResult.getPaymentId()).filter(paymentRecord -> Objects.equals(paymentRecord.getRefundStatus(), RefundStatusEnum.REFUNDING)).ifPresent(paymentRecord -> {
            if (!Objects.equals(refundResult.getRefundStatus(), RefundStatusEnum.REFUNDING)) {
                paymentRecord.setRefundEndedTime(LocalDateTime.now());
            }
            paymentRecord.setRefundStatus(refundResult.getRefundStatus());
            this.paymentRecordService.update(paymentRecord);
        });
        // 取消定时任务
        ScheduleUtils.cancelSchedule(this.compositePaymentService.getScheduleTaskTriggerNamePrefix(serviceProvider) + refundResult.getRefundId(), this.compositePaymentService.getScheduleTaskTriggerGroupName(serviceProvider));
        // 清除退款详情信息
        // RedisUtils.remove(CacheConstants.REFUND_DETAILS_CACHE_KEY_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR + refundResult.getRefundId());
    }
}
