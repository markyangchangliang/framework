package com.markyang.framework.service.payment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.markyang.framework.pojo.payment.*;
import com.markyang.framework.service.payment.exception.NoSuchPaymentRecordException;
import com.markyang.framework.service.payment.exception.NotSupportedPaymentServiceProvider;
import com.markyang.framework.service.payment.service.PaymentRecordService;
import com.markyang.framework.service.payment.task.PaymentStatusQueryTask;
import com.markyang.framework.service.payment.task.RefundStatusQueryTask;
import com.markyang.framework.service.payment.task.UnhandledPaymentCleaningTask;
import com.markyang.framework.pojo.constant.CacheConstants;
import com.markyang.framework.pojo.entity.payment.PaymentRecord;
import com.markyang.framework.pojo.enumeration.payment.PaymentStatusEnum;
import com.markyang.framework.pojo.enumeration.payment.RefundStatusEnum;
import com.markyang.framework.pojo.enumeration.payment.ServiceProviderEnum;
import com.markyang.framework.util.RedisUtils;
import com.markyang.framework.util.ScheduleUtils;
import com.markyang.framework.util.TriggerUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SimpleScheduleBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 组合支付，支付顶层接口
 *
 * @author yangchangliang
 * @version 1
 */
@Service
@Slf4j
public class CompositePaymentService implements PaymentService {

    /**
     * 支付记录服务类
     */
    public final PaymentRecordService paymentRecordService;

    /**
     * 支付实现集合
     */
    private final List<PaymentService> paymentServices;

    public CompositePaymentService(
        PaymentRecordService paymentRecordService,
        @Qualifier("wxMiniAppPaymentService") PaymentService wxMiniAppPaymentService
    ) {
        this.paymentRecordService = paymentRecordService;
        this.paymentServices = Lists.newArrayList(
            wxMiniAppPaymentService
        );
    }

    /**
     * 创建支付
     *
     * @param paymentDetails 支付信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentCreationResult createPayment(PaymentDetails paymentDetails) {
        for (PaymentService paymentService : this.paymentServices) {
            if (paymentService.support(paymentDetails.getServiceProvider())) {
                PaymentCreationResult result = paymentService.createPayment(paymentDetails);
                // 后置处理
                this.postPaymentCreation(paymentService, result.getPaymentId(), paymentDetails.getOrgId(), paymentDetails);
                // 添加paymentId给additionalInformation
                Map<String, Object> additionalInformation = Maps.newHashMap(result.getAdditionalInformation());
                additionalInformation.put(PAYMENT_ID_KEY, result.getPaymentId());
                result.setAdditionalInformation(additionalInformation);
                return result;
            }
        }
        throw new NotSupportedPaymentServiceProvider("不支持的支付服务提供商：" + paymentDetails.getServiceProvider());
    }

    /**
     * 支付后处理
     * @param paymentService 支付服务类
     * @param paymentId 支付ID
     * @param orgId 机构ID
     * @param paymentDetails 支付详情
     */
    private void postPaymentCreation(PaymentService paymentService, String paymentId, String orgId, PaymentDetails paymentDetails) {
        // 保存统一支付记录
        this.paymentRecordService.save(
            PaymentRecord.builder()
                .paymentId(paymentId)
                .orgId(orgId)
                .businessId(paymentDetails.getBusinessId())
                .businessKey(paymentDetails.getBusinessKey())
                .clientIp(paymentDetails.getClientIp())
                .clientType(paymentDetails.getClientType())
                .paymentCreatedTime(LocalDateTime.now())
                .serviceProvider(ServiceProviderEnum.WX)
                .title(paymentDetails.getTitle())
                .payerId(paymentDetails.getPayerId())
                .paymentStatus(PaymentStatusEnum.WAITING_PAY)
                .totalFee(paymentDetails.getTotalFee())
                .build()
        );
        // 缓存PaymentDetails
        RedisUtils.set(CacheConstants.PAYMENT_DETAILS_CACHE_KEY_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR + paymentId, paymentDetails, CacheConstants.PAYMENT_DURATION_TIME);
        // 启动定时任务
        ScheduleUtils.schedule(TriggerUtils.createTriggerAtDateTimeWithIntervalRepeat(
            paymentService.getScheduleTaskTriggerNamePrefix(paymentDetails.getServiceProvider()) + paymentId,
            paymentService.getScheduleTaskTriggerGroupName(paymentDetails.getServiceProvider()),
            PaymentStatusQueryTask.of(paymentId, paymentDetails.getServiceProvider()),
            SimpleScheduleBuilder.repeatMinutelyForTotalCount(6),
            LocalDateTime.now().plusMinutes(1)));
        // 启动待支付订单清理定时任务
        ScheduleUtils.schedule(TriggerUtils.createTriggerAtDateTime(paymentService.getScheduleTaskTriggerNamePrefix(paymentDetails.getServiceProvider()) + "cleaning_" + paymentId,
            paymentService.getScheduleTaskTriggerGroupName(paymentDetails.getServiceProvider()),
            UnhandledPaymentCleaningTask.of(paymentId, paymentDetails.getServiceProvider(), paymentDetails.getBusinessKey(), paymentDetails.getBusinessId()),
            LocalDateTime.now().plusDays(2)
            ));
    }

    /**
     * 关闭支付
     *
     * @param paymentId   支付ID
     * @param spPaymentId 服务提供商支付ID
     * @param serviceProvider 服务提供商
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentClosureResult closePayment(String paymentId, String spPaymentId, ServiceProviderEnum serviceProvider) {
        for (PaymentService paymentService : this.paymentServices) {
            if (paymentService.support(serviceProvider)) {
                return paymentService.closePayment(paymentId, spPaymentId, serviceProvider);
            }
        }
        throw new NotSupportedPaymentServiceProvider("不支持的支付服务提供商：" + serviceProvider);
    }

    /**
     * 查询支付信息
     *
     * @param paymentId       支付ID
     * @param spPaymentId     服务提供商支付ID
     * @param serviceProvider 服务提供商
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentQueryResult queryPayment(String paymentId, String spPaymentId, ServiceProviderEnum serviceProvider) {
        for (PaymentService paymentService : this.paymentServices) {
            if (paymentService.support(serviceProvider)) {
                return paymentService.queryPayment(paymentId, spPaymentId, serviceProvider);
            }
        }
        throw new NotSupportedPaymentServiceProvider("不支持的支付服务提供商：" + serviceProvider);
    }

    /**
     * 创建退款
     *
     * @param refundDetails 退款信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RefundCreationResult createRefund(RefundDetails refundDetails) {
        // 保存统一支付记录
        Optional<PaymentRecord> paymentRecordOptional = this.paymentRecordService.get(refundDetails.getPaymentId());
        if (!paymentRecordOptional.isPresent()) {
            throw new NoSuchPaymentRecordException("支付记录[" + refundDetails.getPaymentId() + "]不存在");
        }
        // 填充数据
        PaymentRecord paymentRecord = paymentRecordOptional.get();
        refundDetails.setOrgId(paymentRecord.getOrgId());
        refundDetails.setTotalFee(paymentRecord.getTotalFee());
        refundDetails.setServiceProvider(paymentRecord.getServiceProvider());
        refundDetails.setSpPaymentId(paymentRecord.getSpPaymentId());
        for (PaymentService paymentService : this.paymentServices) {
            if (paymentService.support(refundDetails.getServiceProvider())) {
                RefundCreationResult result = paymentService.createRefund(refundDetails);
                this.postRefundCreation(paymentService, paymentRecord, refundDetails, result);
                return result;
            }
        }
        throw new NotSupportedPaymentServiceProvider("不支持的支付服务提供商：" + refundDetails.getServiceProvider());
    }

    /**
     * 退款之后，存储信息
     * @param paymentService 支付服务类
     * @param refundDetails 退款详情
     * @param result 退款创建结果
     */
    private void postRefundCreation(PaymentService paymentService, PaymentRecord paymentRecord, RefundDetails refundDetails, RefundCreationResult result) {
        // 保存统一支付记录
        // 更新数据库
        paymentRecord.setRefunded(true);
        paymentRecord.setRefundStatus(RefundStatusEnum.REFUNDING);
        paymentRecord.setRefundedBy(refundDetails.getRefundedBy());
        paymentRecord.setRefundCreatedTime(LocalDateTime.now());
        paymentRecord.setRefundId(result.getRefundId());
        paymentRecord.setSpRefundId(result.getSpRefundId());
        paymentRecord.setRefundReason(refundDetails.getReason());
        this.paymentRecordService.update(paymentRecord);
        // 缓存RefundDetails
        RedisUtils.set(CacheConstants.REFUND_DETAILS_CACHE_KEY_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR + result.getRefundId(), refundDetails, CacheConstants.PAYMENT_DURATION_TIME);
        // 启动定时任务
        ScheduleUtils.schedule(TriggerUtils.createTriggerAtDateTimeWithIntervalRepeat(paymentService.getScheduleTaskTriggerNamePrefix(paymentRecord.getServiceProvider()) + refundDetails.getPaymentId(), paymentService.getScheduleTaskTriggerGroupName(paymentRecord.getServiceProvider()), RefundStatusQueryTask.of(refundDetails.getPaymentId(), paymentRecord.getSpPaymentId(), result.getRefundId(), paymentRecord.getServiceProvider()), SimpleScheduleBuilder.repeatMinutelyForTotalCount(6), LocalDateTime.now().plusMinutes(1)));
    }

    /**
     * 查询退款
     *
     * @param paymentId       支付ID
     * @param spPaymentId     服务提供商支付ID
     * @param refundId        退款ID
     * @param spRefundId      服务提供商退款ID
     * @param serviceProvider 服务提供商
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RefundQueryResult queryRefund(String paymentId, String spPaymentId, String refundId, String spRefundId, ServiceProviderEnum serviceProvider) {
        for (PaymentService paymentService : this.paymentServices) {
            if (paymentService.support(serviceProvider)) {
                return paymentService.queryRefund(paymentId, spPaymentId, refundId, spRefundId, serviceProvider);
            }
        }
        throw new NotSupportedPaymentServiceProvider("不支持的支付服务提供商：" + serviceProvider);
    }

    /**
     * 撤销支付
     *
     * @param paymentId       支付ID
     * @param spPaymentId     服务提供商支付ID
     * @param serviceProvider 服务提供商
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentReversionResult reversePayment(String paymentId, String spPaymentId, ServiceProviderEnum serviceProvider) {
        for (PaymentService paymentService : this.paymentServices) {
            if (paymentService.support(serviceProvider)) {
                return paymentService.reversePayment(paymentId, spPaymentId, serviceProvider);
            }
        }
        throw new NotSupportedPaymentServiceProvider("不支持的支付服务提供商：" + serviceProvider);
    }

    /**
     * 获取定时任务触发器名称前缀
     *
     * @param serviceProvider 服务提供商
     * @return 名称前缀
     */
    @Override
    public String getScheduleTaskTriggerNamePrefix(ServiceProviderEnum serviceProvider) {
        for (PaymentService paymentService : this.paymentServices) {
            if (paymentService.support(serviceProvider)) {
                return paymentService.getScheduleTaskTriggerNamePrefix(serviceProvider);
            }
        }
        throw new NotSupportedPaymentServiceProvider("不支持的支付服务提供商：" + serviceProvider);
    }

    /**
     * 获取定时任务触发器组名称
     *
     * @param serviceProvider 服务提供商
     * @return 组名称
     */
    @Override
    public String getScheduleTaskTriggerGroupName(ServiceProviderEnum serviceProvider) {
        for (PaymentService paymentService : this.paymentServices) {
            if (paymentService.support(serviceProvider)) {
                return paymentService.getScheduleTaskTriggerGroupName(serviceProvider);
            }
        }
        throw new NotSupportedPaymentServiceProvider("不支持的支付服务提供商：" + serviceProvider);
    }

    /**
     * 是否支持支付方式
     *
     * @param serviceProvider 支付方式
     * @return bool
     */
    @Override
    public boolean support(ServiceProviderEnum serviceProvider) {
        // 永远只是返回true
        return true;
    }

    /**
     * 根据支付ID获取机构ID
     *
     * @param paymentId       支付ID
     * @param serviceProvider 服务提供商
     * @return 机构ID
     */
    @Override
    public String getOrgId(String paymentId, ServiceProviderEnum serviceProvider) {
        for (PaymentService paymentService : this.paymentServices) {
            if (paymentService.support(serviceProvider)) {
                return paymentService.getOrgId(paymentId, serviceProvider);
            }
        }
        throw new NotSupportedPaymentServiceProvider("不支持的支付服务提供商：" + serviceProvider);
    }
}
