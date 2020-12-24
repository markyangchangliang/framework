package com.markyang.framework.service.payment.task;

import com.markyang.framework.service.payment.PaymentQueryCallback;
import com.markyang.framework.pojo.constant.CacheConstants;
import com.markyang.framework.pojo.enumeration.payment.PaymentStatusEnum;
import com.markyang.framework.pojo.enumeration.payment.ServiceProviderEnum;
import com.markyang.framework.pojo.payment.PaymentQueryResult;
import com.markyang.framework.pojo.payment.RefundDetails;
import com.markyang.framework.pojo.schedule.ScheduledTask;
import com.markyang.framework.service.payment.CompositePaymentService;
import com.markyang.framework.service.payment.service.PaymentRecordService;
import com.markyang.framework.util.RedisUtils;
import com.markyang.framework.util.ScheduleUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

/**
 * 支付订单状态获取任务
 *
 * @author yangchangliang
 * @version 1
 */
@AllArgsConstructor(staticName = "of")
@Slf4j
public class PaymentStatusQueryTask implements ScheduledTask {

    private static final long serialVersionUID = -196717858180584871L;

    /**
     * 支付ID
     */
    private final String paymentId;

    /**
     * 服务提供商
     */
    private final ServiceProviderEnum serviceProvider;

    /**
     * 任务执行方法
     *
     * @param context            任务执行上下文
     * @param applicationContext Spring容器
     * @throws JobExecutionException 任务执行异常
     */
    @Override
    public void execute(JobExecutionContext context, ApplicationContext applicationContext) throws JobExecutionException {
        // 获取聚合PaymentService
        CompositePaymentService paymentService = applicationContext.getBean("compositePaymentService", CompositePaymentService.class);
        PaymentQueryResult paymentQueryResult = paymentService.queryPayment(paymentId, null, serviceProvider);
        // 支付成功 处理支付成功
        if (Objects.equals(paymentQueryResult.getPaymentStatus(), PaymentStatusEnum.PAID_SUCCESS)) {
            // 支付成功处理
            log.info("[{}]支付订单[{}]查询结果：状态[{}]，描述[{}]", this.serviceProvider, this.paymentId, paymentQueryResult.getPaymentStatus(), paymentQueryResult.getPaymentStatusDescription());
            Collection<PaymentQueryCallback> paymentQueryCallbacks = applicationContext.getBeansOfType(PaymentQueryCallback.class).values();
            try {
                paymentQueryCallbacks.parallelStream().filter(paymentQueryCallback -> paymentQueryCallback.support(serviceProvider, paymentQueryResult.getBusinessKey())).forEach(paymentQueryCallback -> paymentQueryCallback.invoke(paymentQueryResult));
            } catch (Exception e) {
                // 处理发生异常
                log.error("支付处理业务[businessKey={}, businessId={}]回调发生异常：{}", paymentQueryResult.getBusinessKey(), paymentQueryResult.getBusinessId(), e.getMessage());
                if (Objects.equals(PaymentStatusEnum.PAID_SUCCESS, paymentQueryResult.getPaymentStatus())) {
                    // 开始退款
                    paymentService.createRefund(
                            RefundDetails
                                    .builder()
                                    .businessId(paymentQueryResult.getBusinessId())
                                    .businessKey(paymentQueryResult.getBusinessKey())
                                    .paymentId(paymentQueryResult.getPaymentId())
                                    .spPaymentId(paymentQueryResult.getSpPaymentId())
                                    .serviceProvider(this.serviceProvider)
                                    .orgId(paymentService.getOrgId(paymentQueryResult.getPaymentId(), this.serviceProvider))
                                    .totalFee(paymentQueryResult.getTotalFee())
                                    .refundFee(paymentQueryResult.getTotalFee())
                                    .reason("处理主动查询订单支付成功业务逻辑异常")
                                    .refundedBy("系统")
                                    .build()
                    );
                }
            }
        } else {
            log.warn("[{}]支付订单[{}]查询结果：状态[{}]，描述[{}]", this.serviceProvider, this.paymentId, paymentQueryResult.getPaymentStatus(), paymentQueryResult.getPaymentStatusDescription());
        }
        // 更新支付记录
        if (!Objects.equals(PaymentStatusEnum.WAITING_PAY, paymentQueryResult.getPaymentStatus())) {
            PaymentRecordService paymentRecordService = applicationContext.getBean(PaymentRecordService.class);
            paymentRecordService.get(paymentQueryResult.getPaymentId()).filter(paymentRecord -> Objects.equals(paymentRecord.getPaymentStatus(), PaymentStatusEnum.WAITING_PAY) || Objects.equals(paymentRecord.getPaymentStatus(), PaymentStatusEnum.PAYING)).ifPresent(paymentRecord -> {
                paymentRecord.setSpPaymentId(paymentQueryResult.getSpPaymentId());
                paymentRecord.setPaymentEndedTime(LocalDateTime.now());
                paymentRecord.setPaymentStatus(paymentQueryResult.getPaymentStatus());
                paymentRecordService.update(paymentRecord);
            });
        }
        // 取消定时任务状态
        if (!Objects.equals(PaymentStatusEnum.WAITING_PAY, paymentQueryResult.getPaymentStatus()) && !Objects.equals(PaymentStatusEnum.PAYING, paymentQueryResult.getPaymentStatus())) {
            // 非处理中的订单，取消定时任务
            ScheduleUtils.cancelSchedule(paymentService.getScheduleTaskTriggerNamePrefix(this.serviceProvider) + paymentQueryResult.getPaymentId(), paymentService.getScheduleTaskTriggerGroupName(this.serviceProvider));
            ScheduleUtils.cancelSchedule(paymentService.getScheduleTaskTriggerNamePrefix(this.serviceProvider) + "cleaning_" + paymentQueryResult.getPaymentId(), paymentService.getScheduleTaskTriggerGroupName(this.serviceProvider));
            // 清除Redis支付信息
            String paymentInfoCacheKey = CacheConstants.PAYMENT_INFO_CACHE_KEY_CACHE_KEY_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR + paymentQueryResult.getPaymentId();
            RedisUtils.remove(RedisUtils.getString(paymentInfoCacheKey, ""));
            RedisUtils.remove(paymentInfoCacheKey);
            // RedisUtils.remove(CacheConstants.PAYMENT_DETAILS_CACHE_KEY_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR + paymentQueryResult.getPaymentId());
        }
    }
}
