package com.markyang.framework.service.payment.task;

import com.markyang.framework.pojo.enumeration.payment.ServiceProviderEnum;
import com.markyang.framework.pojo.schedule.ScheduledTask;
import com.markyang.framework.service.payment.PaymentCleaningCallback;
import com.markyang.framework.service.payment.service.PaymentRecordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * 未处理的支付订单清理任务
 *
 * @author yangchangliang
 * @version 1
 */
@AllArgsConstructor(staticName = "of")
@Slf4j
public class UnhandledPaymentCleaningTask implements ScheduledTask {

    private static final long serialVersionUID = 1353365469230124141L;

    /**
     * 支付ID
     */
    private final String paymentId;

    /**
     * 服务提供商
     */
    private final ServiceProviderEnum serviceProvider;

    /**
     * 业务Key
     */
    private final String businessKey;

    /**
     * 业务ID
     */
    private final String businessId;

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
        PaymentRecordService paymentRecordService = applicationContext.getBean(PaymentRecordService.class);
        // 能执行到这里，说明该支付订单一定是用户创建完成后自己不想支付的订单
        Map<String, PaymentCleaningCallback> beansOfType = applicationContext.getBeansOfType(PaymentCleaningCallback.class);
        if (MapUtils.isNotEmpty(beansOfType)) {
            // 执行回调
            beansOfType.values().parallelStream().filter(paymentCleaningCallback -> paymentCleaningCallback.support(this.serviceProvider, this.businessKey)).forEach(paymentCleaningCallback -> paymentCleaningCallback.clean(this.paymentId, this.businessKey, this.businessId));
        }
        // 删除支付记录
        paymentRecordService.delete(paymentId);
    }
}
