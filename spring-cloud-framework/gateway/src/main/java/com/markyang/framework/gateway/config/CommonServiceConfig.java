package com.markyang.framework.gateway.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 通用service配置
 * @author yangchangliang
 */
@Configuration
@ComponentScan(basePackages = {
    "com.markyang.framework.service.auth",
    "com.markyang.framework.service.common"
})
public class CommonServiceConfig {
}
