package com.markyang.framework.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yangchangliang
 */
@ConfigurationProperties(prefix = "framework.auth")
@Data
public class AuthProperties {

    /**
     * 普通用户名密码登录地址
     */
    private String loginProcessingUrl;

    /**
     * 短信验证码登录地址
     */
    private String smsLoginProcessingUrl;

    /**
     * 微信小程序Code登录地址
     */
    private String wxMiniAppCodeLoginProcessingUrl;

    /**
     * 允许通过的URL
     */
    private String[] permittedUrls;
}
