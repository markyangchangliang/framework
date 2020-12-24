package com.markyang.framework.pojo.properties.wx;

import lombok.Data;

/**
 * 微信小程序配置
 *
 * @author yangchangliang
 * @version 1
 */
@Data
public class WxMiniAppProperties {

    /**
     * 小程序的appId
     */
    private String appId;
    /**
     * 小程序的appSecret
     */
    private String appSecret;
    /**
     * 设置微信小程序消息服务器配置的token
     */
    private String token;
    /**
     * 设置微信小程序消息服务器配置的EncodingAESKey
     */
    private String aesKey;
    /**
     * 消息格式，XML或者JSON
     */
    private String messageDataFormat;
}
