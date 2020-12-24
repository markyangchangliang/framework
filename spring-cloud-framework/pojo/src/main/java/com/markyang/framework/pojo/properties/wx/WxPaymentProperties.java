package com.markyang.framework.pojo.properties.wx;

import lombok.Data;

/**
 * 微信支付配置
 *
 * @author yangchangliang
 * @version 1
 */
@Data
public class WxPaymentProperties {

    /**
     * 微信支付商户的appId
     */
    private String appId;

    /**
     * 微信支付商户号
     */
    private String mchId;

    /**
     * 微信支付商户密钥
     */
    private String mchKey;

    /**
     * api_client_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
     */
    private String keyPath;
}
