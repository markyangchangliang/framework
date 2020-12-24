package com.markyang.framework.service.payment.wx;

import com.github.binarywang.wxpay.bean.request.*;
import com.github.binarywang.wxpay.bean.result.*;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.markyang.framework.config.payment.PaymentConfigProperties;
import com.markyang.framework.pojo.payment.*;
import com.markyang.framework.service.payment.PaymentService;
import com.markyang.framework.service.payment.exception.PaymentException;
import com.markyang.framework.service.payment.exception.WxPaymentException;
import com.markyang.framework.service.payment.service.PaymentRecordService;
import com.markyang.framework.service.wx.CompositeWxPayService;
import com.markyang.framework.pojo.enumeration.payment.ClientTypeEnum;
import com.markyang.framework.pojo.enumeration.payment.PaymentStatusEnum;
import com.markyang.framework.pojo.enumeration.payment.RefundStatusEnum;
import com.markyang.framework.pojo.enumeration.payment.ServiceProviderEnum;
import com.markyang.framework.util.DateTimeUtils;
import com.markyang.framework.util.OrderUtils;
import com.markyang.framework.util.PaymentUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 微信支付实现
 *
 * @author yangchangliang
 * @version 1
 */
@EnableConfigurationProperties(PaymentConfigProperties.class)
@RequiredArgsConstructor
@Slf4j
public abstract class WxPaymentService implements PaymentService {

    /**
     * 触发器名称前缀
     */
    public static final String TRIGGER_NAME_PREFIX = "wx_payment_";

    /**
     * 回调主机地址
     */
    @Value("${framework.payment.callback-host:'http://127.0.0.1'}")
    protected String callbackHost;

    @NonNull
    protected final CompositeWxPayService compositeWxPayService;

    @NonNull
    protected final PaymentRecordService paymentRecordService;

    @NonNull
    protected final PaymentConfigProperties paymentConfigProperties;


    /**
     * 推断交易类型
     *
     * @param clientType 客户端类型
     * @return 交易类型
     */
    public static String deduceTradeType(ClientTypeEnum clientType) {
        switch (clientType) {
            case APP:
                return WxPayConstants.TradeType.APP;
            case WEB:
                return WxPayConstants.TradeType.MWEB;
            case MINI_APP:
                return WxPayConstants.TradeType.JSAPI;
        }
        return "";
    }

    /**
     * 反推ClientType类型
     *
     * @param tradeType 交易类型
     * @return ClientType
     */
    public static ClientTypeEnum deduceClientType(String tradeType) {
        if (StringUtils.isBlank(tradeType)) {
            return null;
        }
        switch (tradeType) {
            case WxPayConstants.TradeType.APP:
                return ClientTypeEnum.APP;
            case WxPayConstants.TradeType.MWEB:
                return ClientTypeEnum.WEB;
            case WxPayConstants.TradeType.JSAPI:
            default:
                return ClientTypeEnum.MINI_APP;
        }
    }

    /**
     * 反推支付状态
     *
     * @param tradeState 交易状态
     * @return 支付状态枚举对象
     */
    public static PaymentStatusEnum deducePaymentStatus(String tradeState) {
        switch (tradeState) {
            case WxPayConstants.WxpayTradeStatus.NOTPAY:
                return PaymentStatusEnum.WAITING_PAY;
            case WxPayConstants.WxpayTradeStatus.USER_PAYING:
                return PaymentStatusEnum.PAYING;
            case WxPayConstants.WxpayTradeStatus.SUCCESS:
                return PaymentStatusEnum.PAID_SUCCESS;
            case WxPayConstants.WxpayTradeStatus.PAY_ERROR:
                return PaymentStatusEnum.PAID_FAILURE;
            case WxPayConstants.WxpayTradeStatus.CLOSED:
                return PaymentStatusEnum.CLOSED;
            case WxPayConstants.WxpayTradeStatus.REFUND:
                return PaymentStatusEnum.REFUNDED;
            case WxPayConstants.WxpayTradeStatus.REVOKED:
                return PaymentStatusEnum.REVERSED;
            default:
                return null;
        }
    }

    /**
     * 推到退款状态
     *
     * @param refundStatus 退款状态
     * @return 退款状态枚举
     */
    public static RefundStatusEnum deduceRefundStatus(String refundStatus) {
        switch (refundStatus) {
            case WxPayConstants.RefundStatus.SUCCESS:
                return RefundStatusEnum.REFUNDED_SUCCESS;
            case WxPayConstants.RefundStatus.PROCESSING:
                return RefundStatusEnum.REFUNDING;
            case WxPayConstants.RefundStatus.REFUND_CLOSE:
                return RefundStatusEnum.CLOSED;
            case WxPayConstants.RefundStatus.CHANGE:
            default:
                return RefundStatusEnum.REFUNDED_FAILURE;
        }
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
        String orgId = PaymentUtils.getOrgId(paymentId)
            .orElseGet(() -> this.paymentRecordService.getOrgId(paymentId));
        if (StringUtils.isBlank(orgId)) {
            throw new PaymentException("根据PaymentId[" + paymentId + "]找不到机构ID");
        }
        return orgId;
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
    public PaymentQueryResult queryPayment(String paymentId, String spPaymentId, ServiceProviderEnum serviceProvider) {
        try {
            WxPayOrderQueryRequest request = WxPayOrderQueryRequest.newBuilder()
                .transactionId(spPaymentId)
                .outTradeNo(paymentId)
                .build();

            WxPayOrderQueryResult wxPayOrderQueryResult = this.compositeWxPayService.queryOrder(this.getOrgId(paymentId, serviceProvider), request);
            return PaymentQueryResult.builder()
                .paymentId(wxPayOrderQueryResult.getOutTradeNo())
                .spPaymentId(wxPayOrderQueryResult.getTransactionId())
                .clientType(deduceClientType(wxPayOrderQueryResult.getTradeType()))
                .paymentStatus(deducePaymentStatus(wxPayOrderQueryResult.getTradeState()))
                .paymentStatusDescription(wxPayOrderQueryResult.getTradeStateDesc())
                .finishedDateTime(StringUtils.isNotBlank(wxPayOrderQueryResult.getTimeEnd()) ? DateTimeUtils.parseLocalDateTime(wxPayOrderQueryResult.getTimeEnd(), "yyyyMMddHHmmss") : LocalDateTime.now())
                .totalFee(new BigDecimal(BaseWxPayResult.fenToYuan(wxPayOrderQueryResult.getTotalFee())))
                .businessKey(PaymentUtils.getPaymentBusinessKey(wxPayOrderQueryResult.getOutTradeNo()).orElse(""))
                .build();
        } catch (WxPayException e) {
            log.error("查询订单支付状态出现错误：{}", e.toString());
            throw new WxPaymentException("查询订单支付状态出现错误：" + e.getMessage());
        }
    }

    /**
     * 关闭支付
     *
     * @param paymentId       支付ID
     * @param spPaymentId     服务提供商支付ID
     * @param serviceProvider 服务提供商
     * @return 结果
     */
    @Override
    public PaymentClosureResult closePayment(String paymentId, String spPaymentId, ServiceProviderEnum serviceProvider) {
        try {
            WxPayOrderCloseResult result = this.compositeWxPayService.closeOrder(this.getOrgId(paymentId, serviceProvider), paymentId);
            return PaymentClosureResult
                .builder()
                .result(result.getResultMsg())
                .build();
        } catch (WxPayException e) {
            log.error("关闭微信支付订单出现异常：{}", e.toString());
            throw new WxPaymentException("关闭微信支付订单出现异常：" + e.getMessage());
        }
    }

    /**
     * 创建退款
     *
     * @param refundDetails 退款信息
     * @return 结果
     */
    @Override
    public RefundCreationResult createRefund(RefundDetails refundDetails) {
        WxPayRefundRequest request = WxPayRefundRequest.newBuilder()
            .outRefundNo(OrderUtils.generateSerialNumber(32, false))
            .outTradeNo(refundDetails.getPaymentId())
            .transactionId(refundDetails.getSpPaymentId())
            .opUserId(refundDetails.getRefundedBy())
            .totalFee(BaseWxPayRequest.yuanToFen(refundDetails.getTotalFee().toPlainString()))
            .refundFee(BaseWxPayRequest.yuanToFen(refundDetails.getRefundFee().toPlainString()))
            .refundFeeType("CNY")
            .refundDesc(refundDetails.getReason())
            .notifyUrl(this.callbackHost + WxPaymentCallbackController.REFUND_NOTIFY_URI)
            .build();
        try {
            WxPayRefundResult wxPayRefundResult = this.compositeWxPayService.refund(Objects.nonNull(refundDetails.getOrgId()) ? refundDetails.getOrgId() : this.getOrgId(refundDetails.getPaymentId(), ServiceProviderEnum.WX), request);
            return RefundCreationResult
                .builder()
                .paymentId(wxPayRefundResult.getOutTradeNo())
                .spPaymentId(wxPayRefundResult.getTransactionId())
                .refundId(wxPayRefundResult.getOutRefundNo())
                .spRefundId(wxPayRefundResult.getRefundId())
                .totalFee(new BigDecimal(BaseWxPayResult.fenToYuan(wxPayRefundResult.getTotalFee())))
                .refundFee(new BigDecimal(BaseWxPayResult.fenToYuan(wxPayRefundResult.getRefundFee())))
                .build();
        } catch (WxPayException e) {
            log.error("微信退款出现异常：{}", e.toString());
            throw new WxPaymentException("微信退款出现异常：" + e.getMessage());
        }
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
    public RefundQueryResult queryRefund(String paymentId, String spPaymentId, String refundId, String spRefundId, ServiceProviderEnum serviceProvider) {
        try {
            WxPayRefundQueryRequest request = WxPayRefundQueryRequest.newBuilder()
                .outTradeNo(paymentId)
                .transactionId(spPaymentId)
                .outRefundNo(refundId)
                .refundId(spRefundId)
                .build();
            WxPayRefundQueryResult wxPayRefundQueryResult = this.compositeWxPayService.refundQuery(this.getOrgId(refundId, serviceProvider), request);
            Optional<RefundDetails> refundDetailsOptional = PaymentUtils.getRefundDetails(refundId);
            List<WxPayRefundQueryResult.RefundRecord> refundRecords = wxPayRefundQueryResult.getRefundRecords();
            for (WxPayRefundQueryResult.RefundRecord refundRecord : refundRecords) {
                if (StringUtils.equals(refundId, refundRecord.getOutRefundNo())) {
                    return RefundQueryResult
                        .builder()
                        .businessId(refundDetailsOptional.map(RefundDetails::getBusinessId).orElse(""))
                        .businessKey(refundDetailsOptional.map(RefundDetails::getBusinessKey).orElse(""))
                        .paymentId(wxPayRefundQueryResult.getOutTradeNo())
                        .refundId(refundRecord.getOutRefundNo())
                        .spPaymentId(wxPayRefundQueryResult.getTransactionId())
                        .spRefundId(refundRecord.getRefundId())
                        .totalFee(new BigDecimal(BaseWxPayResult.fenToYuan(wxPayRefundQueryResult.getTotalFee())))
                        .refundFee(new BigDecimal(BaseWxPayResult.fenToYuan(refundRecord.getRefundFee())))
                        .refundStatus(deduceRefundStatus(refundRecord.getRefundStatus()))
                        .refundStatusDescription(refundRecord.getRefundStatus())
                        .finishedDateTime(StringUtils.isNotBlank(refundRecord.getRefundSuccessTime()) ? DateTimeUtils.parseLocalDateTime(refundRecord.getRefundSuccessTime()) : LocalDateTime.now())
                        .build();
                }
            }
            throw new WxPaymentException("找不到微信退款订单：" + refundId);
        } catch (WxPayException e) {
            log.error("微信退款查询出现异常：{}", e.toString());
            throw new WxPaymentException("微信退款查询出现异常：" + e.getMessage());
        }
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
    public PaymentReversionResult reversePayment(String paymentId, String spPaymentId, ServiceProviderEnum serviceProvider) {
        WxPayOrderReverseRequest request = WxPayOrderReverseRequest
            .newBuilder()
            .outTradeNo(paymentId)
            .transactionId(spPaymentId)
            .build();
        String orgId = this.getOrgId(paymentId, serviceProvider);
        try {
            WxPayOrderReverseResult wxPayOrderReverseResult = this.compositeWxPayService.reverseOrder(orgId, request);
            // 第一次调用
            if (StringUtils.equals(wxPayOrderReverseResult.getIsRecall(), "Y")) {
                wxPayOrderReverseResult = this.compositeWxPayService.reverseOrder(orgId, request);
            }
            // 第二次调用
            if (StringUtils.equals(wxPayOrderReverseResult.getIsRecall(), "Y")) {
                wxPayOrderReverseResult = this.compositeWxPayService.reverseOrder(orgId, request);
            }
            return PaymentReversionResult
                .builder()
                .result(wxPayOrderReverseResult.getResultCode())
                .build();
        } catch (WxPayException e) {
            log.error("微信订单撤销出现异常：{}", e.toString());
            throw new WxPaymentException("微信订单撤销出现异常：" + e.getMessage());
        }
    }
}
