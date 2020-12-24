package com.markyang.framework.auth.properties;

import com.markyang.framework.auth.social.core.SocialAppProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yangchangliang
 */
@Data
@ConfigurationProperties(prefix = "framework.auth.social")
public class SocialProperties {

    /**
     * 处理URL
     */
    private String processingUrl;

    /**
     * 注册URL
     */
    private String signUpUrl;

    /**
     * QQ社区互联
     */
    private SocialAppProperties qq;

    /**
     * 微信社区互联
     */
    private SocialAppProperties wx;
}
