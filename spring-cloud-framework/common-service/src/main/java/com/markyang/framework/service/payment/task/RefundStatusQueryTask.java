package com.markyang.framework.service.payment.task;

import com.markyang.framework.pojo.enumeration.payment.RefundStatusEnum;
import com.markyang.framework.pojo.enumeration.payment.ServiceProviderEnum;
import com.markyang.framework.pojo.payment.RefundQueryResult;
import com.markyang.framework.pojo.schedule.ScheduledTask;
import com.markyang.framework.service.payment.CompositePaymentService;
import com.markyang.framework.service.payment.RefundQueryCallback;
import com.markyang.framework.service.payment.service.PaymentRecordService;
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
 * 退款订单状态获取任务
 *
 * @author yangchangliang
 * @version 1
 */
@AllArgsConstructor(staticName = "of")
@Slf4j
public class RefundStatusQueryTask implements ScheduledTask {

    private static final long serialVersionUID = 1536569178725244647L;

    /**
     * 支付ID
     */
    private final String paymentId;

    /**
     * 商户支付ID
     */
    private final String spPaymentId;

    /**
     * 退款ID
     */
    private final String refundId;

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
        RefundQueryResult refundQueryResult = paymentService.queryRefund(this.paymentId, this.spPaymentId, this.refundId, null, serviceProvider);
        // 退款成功 处理退款成功
        if (Objects.equals(refundQueryResult.getRefundStatus(), RefundStatusEnum.REFUNDED_SUCCESS)) {
            // 退款成功处理
            log.info("[{}]退款订单[{}]查询结果：状态[{}]，描述[{}]", this.serviceProvider, this.refundId, refundQueryResult.getRefundStatus(), refundQueryResult.getRefundStatusDescription());
            Collection<RefundQueryCallback> refundQueryCallbacks = applicationContext.getBeansOfType(RefundQueryCallback.class).values();
            try {
                refundQueryCallbacks.parallelStream().filter(refundQueryCallback -> refundQueryCallback.support(serviceProvider, refundQueryResult.getBusinessKey())).forEach(refundQueryCallback -> refundQueryCallback.invoke(refundQueryResult));
            } catch (Exception e) {
                log.error("退款处理回调发生异常：{}", e.getMessage());
            }
        } else {
            log.warn("[{}]退款订单[{}]查询结果：状态[{}]，描述[{}]", this.serviceProvider, this.refundId, refundQueryResult.getRefundStatus(), refundQueryResult.getRefundStatusDescription());
        }
        // 更新支付记录
        if (!Objects.equals(RefundStatusEnum.REFUNDING, refundQueryResult.getRefundStatus())) {
            PaymentRecordService paymentRecordService = applicationContext.getBean(PaymentRecordService.class);
            paymentRecordService.get(refundQueryResult.getPaymentId()).filter(paymentRecord -> Objects.equals(paymentRecord.getRefundStatus(), RefundStatusEnum.REFUNDING)).ifPresent(paymentRecord -> {
                paymentRecord.setRefundEndedTime(LocalDateTime.now());
                paymentRecord.setRefundStatus(refundQueryResult.getRefundStatus());
                paymentRecordService.update(paymentRecord);
            });
        }
        // 取消定时任务状态
        if (!Objects.equals(RefundStatusEnum.REFUNDING, refundQueryResult.getRefundStatus())) {
            // 非处理中的订单，取消定时任务
            ScheduleUtils.cancelSchedule(paymentService.getScheduleTaskTriggerNamePrefix(this.serviceProvider) + refundQueryResult.getRefundId(), paymentService.getScheduleTaskTriggerGroupName(this.serviceProvider));
            // 清除Redis退款信息
            // RedisUtils.remove(CacheConstants.REFUND_DETAILS_CACHE_KEY_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR + refundQueryResult.getRefundId());
        }
    }
}
