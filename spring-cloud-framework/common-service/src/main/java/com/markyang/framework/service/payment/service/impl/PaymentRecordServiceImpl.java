package com.markyang.framework.service.payment.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.markyang.framework.service.core.form.SearchBaseForm;
import com.markyang.framework.service.payment.service.PaymentRecordService;
import com.markyang.framework.pojo.entity.payment.PaymentRecord;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import com.markyang.framework.service.payment.repository.PaymentRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * 统一支付记录表(PaymentRecord)服务类实现
 *
 * @author yangchangliang
 * @version 1
 */
@Service
@Slf4j
public class PaymentRecordServiceImpl extends AbstractSearchableServiceImpl<PaymentRecord, PaymentRecordRepository, SearchBaseForm> implements PaymentRecordService {

    /**
     * 获取支付记录对象
     *
     * @param paymentId 支付ID
     * @return 支付对象Optional
     */
    @Override
    public Optional<PaymentRecord> get(String paymentId) {
        return Optional.ofNullable(this.getOne(Wrappers.<PaymentRecord>lambdaQuery().eq(PaymentRecord::getPaymentId, paymentId)));
    }

    /**
     * 根据paymentId获取机构ID
     *
     * @param paymentId 支付ID
     * @return 机构ID
     */
    @Override
    public String getOrgId(String paymentId) {
        PaymentRecord paymentRecord = this.getOne(Wrappers.<PaymentRecord>lambdaQuery().select(PaymentRecord::getOrgId).eq(PaymentRecord::getPaymentId, paymentId));
        if (Objects.nonNull(paymentRecord)) {
            return paymentRecord.getOrgId();
        }
        return null;
    }

    /**
     * 根据支付ID删除支付记录
     *
     * @param paymentId 支付ID
     */
    @Override
    public void delete(String paymentId) {
        this.remove(Wrappers.<PaymentRecord>lambdaQuery().eq(PaymentRecord::getPaymentId, paymentId));
    }

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public PaymentRecord create() {
        return PaymentRecord.builder().build();
    }
}