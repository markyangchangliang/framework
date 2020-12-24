package com.markyang.framework.config.payment;

import com.markyang.framework.pojo.properties.wx.AliPaymentProperties;
import com.markyang.framework.pojo.properties.wx.WxPaymentProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 支付配置类
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@ConfigurationProperties(prefix = "framework.payment")
@Data
public class PaymentConfigProperties {

    /**
     * 回调主机地址配置
     */
    private String callbackHost;

    /**
     * 微信支付配置信息
     */
    private WxPaymentProperties wx = new WxPaymentProperties();

    /**
     * 支付宝支付配置信息
     */
    private AliPaymentProperties ali = new AliPaymentProperties();
}
