package com.markyang.framework.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 认证配置
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@ConfigurationProperties(prefix = "framework.auth")
@Data
public class AuthConfigProperties {

    /**
     * SpringBootAdmin监控服务的IP
     */
    private String springBootAdminIp;

    /**
     * 允许放行的URI列表
     */
    private String[] permittedUriPatterns;
}
