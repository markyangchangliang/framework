package com.markyang.framework.service.payment.service;

import com.markyang.framework.service.core.form.SearchBaseForm;
import com.markyang.framework.pojo.entity.payment.PaymentRecord;
import com.markyang.framework.service.core.service.SearchableService;

import java.util.Optional;

/**
 * 统一支付记录表(PaymentRecord)服务类
 *
 * @author yangchangliang
 * @version 1
 */
public interface PaymentRecordService extends SearchableService<PaymentRecord, SearchBaseForm> {

    /**
     * 获取支付记录对象
     * @param paymentId 支付ID
     * @return 支付对象Optional
     */
    Optional<PaymentRecord> get(String paymentId);

    /**
     * 根据paymentId获取机构ID
     * @param paymentId 支付ID
     * @return 机构ID
     */
    String getOrgId(String paymentId);

    /**
     * 根据支付ID删除支付记录
     * @param paymentId 支付ID
     */
    void delete(String paymentId);
}