package com.markyang.framework.service.payment.wx;

import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.markyang.framework.config.payment.PaymentConfigProperties;
import com.markyang.framework.service.payment.exception.WxMiniAppPaymentException;
import com.markyang.framework.service.payment.service.PaymentRecordService;
import com.markyang.framework.service.wx.CompositeWxPayService;
import com.markyang.framework.pojo.enumeration.payment.ServiceProviderEnum;
import com.markyang.framework.pojo.payment.PaymentCreationResult;
import com.markyang.framework.pojo.payment.PaymentDetails;
import com.markyang.framework.util.DateTimeUtils;
import com.markyang.framework.util.OrderUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 微信小程序支付
 *
 * @author yangchangliang
 * @version 1
 */
@Service
@Slf4j
public class WxMiniAppPaymentService extends WxPaymentService {

    public WxMiniAppPaymentService(CompositeWxPayService compositeWxPayService, PaymentRecordService paymentRecordService, PaymentConfigProperties paymentConfigProperties) {
        super(compositeWxPayService, paymentRecordService, paymentConfigProperties);
    }

    /**
     * 创建支付
     *
     * @param paymentDetails 支付信息
     * @return 结果
     */
    @Override
    public PaymentCreationResult createPayment(PaymentDetails paymentDetails) {
        String paymentId = OrderUtils.generateSerialNumber(32, false);
        // 获取子应用信息
        WxPayUnifiedOrderRequest request = WxPayUnifiedOrderRequest.newBuilder()
            .body(paymentDetails.getTitle())
            .subOpenid(paymentDetails.getPayerId())
            .feeType("CNY")
            .totalFee(BaseWxPayRequest.yuanToFen(paymentDetails.getTotalFee().stripTrailingZeros().toPlainString()))
            .tradeType(deduceTradeType(paymentDetails.getClientType()))
            .outTradeNo(paymentId)
            .attach(paymentDetails.getBusinessKey())
            .timeStart(DateTimeUtils.toDateTimeStringFormat("yyyyMMddHHmmss"))
            .timeExpire(DateTimeUtils.toDateTimeStringFormat(LocalDateTime.now().plusMinutes(10), "yyyyMMddHHmmss"))
            .spbillCreateIp(paymentDetails.getClientIp())
            .notifyUrl(this.callbackHost + WxPaymentCallbackController.PAYMENT_NOTIFY_URI)
            .build();
        try {
            // 小程序支付结果
            WxPayMpOrderResult result = this.compositeWxPayService.createOrder(paymentDetails.getOrgId(), request);
            // 返回结果
            return PaymentCreationResult.builder()
                .paymentId(paymentId)
                .additionalInformation("appId", result.getAppId())
                .additionalInformation("nonceStr", result.getNonceStr())
                .additionalInformation("timeStamp", result.getTimeStamp())
                .additionalInformation("package", result.getPackageValue())
                .additionalInformation("signType", result.getSignType())
                .additionalInformation("paySign", result.getPaySign())
                .build();
        } catch (WxPayException e) {
            log.error("创建微信支付订单出现错误：{}", e.getReturnMsg());
            throw new WxMiniAppPaymentException("创建支付订单失败：" + e.getReturnMsg());
        }
    }

    /**
     * 是否支持支付方式
     *
     * @param serviceProvider 支付方式
     * @return bool
     */
    @Override
    public boolean support(ServiceProviderEnum serviceProvider) {
        return Objects.equals(ServiceProviderEnum.WX, serviceProvider);
    }
}
