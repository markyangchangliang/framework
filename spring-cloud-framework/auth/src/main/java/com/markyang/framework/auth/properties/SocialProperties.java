package com.markyang.framework.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 社区属性
 * @author markyang
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

}
