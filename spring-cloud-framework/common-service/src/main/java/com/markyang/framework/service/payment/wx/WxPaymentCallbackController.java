package com.markyang.framework.service.payment.wx;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.markyang.framework.service.payment.*;
import com.markyang.framework.service.payment.service.PaymentRecordService;
import com.markyang.framework.service.wx.CompositeWxPayService;
import com.markyang.framework.pojo.enumeration.payment.PaymentStatusEnum;
import com.markyang.framework.pojo.enumeration.payment.RefundStatusEnum;
import com.markyang.framework.pojo.enumeration.payment.ServiceProviderEnum;
import com.markyang.framework.pojo.payment.PaymentDetails;
import com.markyang.framework.pojo.payment.PaymentResult;
import com.markyang.framework.pojo.payment.RefundDetails;
import com.markyang.framework.pojo.payment.RefundResult;
import com.markyang.framework.util.DateTimeUtils;
import com.markyang.framework.util.PaymentUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 微信支付回调控制器
 *
 * @author yangchangliang
 * @version 1
 */
@RestController
@Slf4j
@RequestMapping("/notification/wx")
public class WxPaymentCallbackController extends PaymentCallbackController {

    /**
     * 支付通知URI
     */
    public static final String PAYMENT_NOTIFY_URI = "/notification/wx/payment";
    /**
     * 退款通知URI
     */
    public static final String REFUND_NOTIFY_URI = "/notification/wx/refund";

    private final CompositeWxPayService compositeWxPayService;

    public WxPaymentCallbackController(CompositeWxPayService compositeWxPayService, CompositePaymentService compositePaymentService, PaymentRecordService paymentRecordService, List<PaymentSuccessCallback> paymentSuccessCallbacks, List<PaymentFailureCallback> paymentFailureCallbacks, List<RefundSuccessCallback> refundSuccessCallbacks, List<RefundFailureCallback> refundFailureCallbacks) {
        super(paymentRecordService, compositePaymentService, paymentSuccessCallbacks, paymentFailureCallbacks, refundSuccessCallbacks, refundFailureCallbacks);
        this.compositeWxPayService = compositeWxPayService;
    }

    /**
     * 微信小程序支付成功通知
     * @param xmlData XML数据
     * @return 结果字符串
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/payment")
    public String paymentNotify(@RequestBody String xmlData) {
        try {
            WxPayOrderNotifyResult wxPayOrderNotifyResult = this.compositeWxPayService.parseOrderNotifyResult(xmlData);
            Optional<PaymentDetails> paymentDetailsOptional = PaymentUtils.getPaymentDetails(wxPayOrderNotifyResult.getOutTradeNo());
            if (StringUtils.equals(wxPayOrderNotifyResult.getResultCode(), WxPayConstants.ResultCode.SUCCESS)) {
                // 处理返回结果
                PaymentResult paymentResult = PaymentResult.builder()
                    .paymentId(wxPayOrderNotifyResult.getOutTradeNo())
                    .spPaymentId(wxPayOrderNotifyResult.getTransactionId())
                    .clientType(WxPaymentService.deduceClientType(wxPayOrderNotifyResult.getTradeType()))
                    .totalFee(new BigDecimal(BaseWxPayResult.fenToYuan(wxPayOrderNotifyResult.getTotalFee())))
                    .paymentStatus(PaymentStatusEnum.PAID_SUCCESS)
                    .paymentStatusDescription("支付成功")
                    .finishedDateTime(StringUtils.isNotBlank(wxPayOrderNotifyResult.getTimeEnd()) ? DateTimeUtils.parseLocalDateTime(wxPayOrderNotifyResult.getTimeEnd(), "yyyyMMddHHmmss") : LocalDateTime.now())
                    .businessKey(paymentDetailsOptional.map(PaymentDetails::getBusinessKey).orElse(""))
                    .businessId(paymentDetailsOptional.map(PaymentDetails::getBusinessId).orElse(""))
                    .build();
                this.invokePaymentSuccessCallback(paymentResult, ServiceProviderEnum.WX);
                // 返回成功
                return WxPayNotifyResponse.success("处理成功");
            } else {
                // 处理失败结果
                // 处理返回结果
                PaymentResult paymentResult = PaymentResult.builder()
                    .paymentId(wxPayOrderNotifyResult.getOutTradeNo())
                    .spPaymentId(wxPayOrderNotifyResult.getTransactionId())
                    .clientType(WxPaymentService.deduceClientType(wxPayOrderNotifyResult.getTradeType()))
                    .totalFee(new BigDecimal(BaseWxPayResult.fenToYuan(wxPayOrderNotifyResult.getTotalFee())))
                    .paymentStatus(PaymentStatusEnum.PAID_FAILURE)
                    .paymentStatusDescription("支付失败[" + wxPayOrderNotifyResult.getErrCodeDes() + "]")
                    .finishedDateTime(DateTimeUtils.parseLocalDateTime(wxPayOrderNotifyResult.getTimeEnd(), "yyyyMMddHHmmss"))
                    .businessKey(paymentDetailsOptional.map(PaymentDetails::getBusinessKey).orElse(""))
                    .businessId(paymentDetailsOptional.map(PaymentDetails::getBusinessId).orElse(""))
                    .build();
                this.invokePaymentFailureCallback(paymentResult, ServiceProviderEnum.WX);
                // 返回成功
                return WxPayNotifyResponse.success("处理成功");
            }

        } catch (WxPayException e) {
            log.error("微信支付回调出现异常：{}", e.toString());
            return WxPayNotifyResponse.fail(e.getCustomErrorMsg());
        }
    }

    /**
     * 退款通知
     * @param xmlData XML数据
     * @return 结果字符串
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/refund")
    public String refundNotify(@RequestBody String xmlData) {
        try {
            WxPayRefundNotifyResult wxPayRefundNotifyResult = this.compositeWxPayService.parseRefundNotifyResult(xmlData);
            WxPayRefundNotifyResult.ReqInfo reqInfo = wxPayRefundNotifyResult.getReqInfo();
            Optional<RefundDetails> refundDetailsOptional = PaymentUtils.getRefundDetails(reqInfo.getOutRefundNo());
            // 处理结果
            RefundResult refundResult = this.createRefundResult(reqInfo, refundDetailsOptional.orElse(null));
            // 调用回调处理
            if (Objects.equals(refundResult.getRefundStatus(), RefundStatusEnum.REFUNDED_SUCCESS)) {
                this.invokeRefundSuccessCallback(refundResult, ServiceProviderEnum.WX);
            } else {
                this.invokeRefundFailureCallback(refundResult, ServiceProviderEnum.WX);
            }
            return WxPayNotifyResponse.success("处理成功");
        } catch (WxPayException e) {
            log.error("微信退款回调出现异常：{}", e.toString());
            return WxPayNotifyResponse.fail(e.getMessage());
        }
    }

    /**
     * 创建退款结果对象
     * @param reqInfo 微信返回结果
     * @param refundDetails 退款详情
     * @return 退款结果对象
     */
    private RefundResult createRefundResult(WxPayRefundNotifyResult.ReqInfo reqInfo, RefundDetails refundDetails) {
        return RefundResult.builder()
            .paymentId(reqInfo.getOutTradeNo())
            .spPaymentId(reqInfo.getTransactionId())
            .refundId(reqInfo.getOutRefundNo())
            .spRefundId(reqInfo.getRefundId())
            .totalFee(new BigDecimal(BaseWxPayResult.fenToYuan(reqInfo.getTotalFee())))
            .refundFee(new BigDecimal(BaseWxPayResult.fenToYuan(reqInfo.getRefundFee())))
            .refundStatus(WxPaymentService.deduceRefundStatus(reqInfo.getRefundStatus()))
            .refundStatusDescription(reqInfo.getRefundStatus())
            .finishedDateTime(StringUtils.isNotBlank(reqInfo.getSuccessTime()) ? DateTimeUtils.parseLocalDateTime(reqInfo.getSuccessTime()) : LocalDateTime.now())
            .businessKey(Objects.nonNull(refundDetails) ? refundDetails.getBusinessKey() : "")
            .businessId(Objects.nonNull(refundDetails) ? refundDetails.getBusinessId() : "")
            .build();
    }
}
