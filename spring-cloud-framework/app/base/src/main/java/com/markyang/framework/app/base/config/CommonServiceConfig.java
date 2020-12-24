package com.markyang.framework.app.base.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 共用配置
 * @author admin
 */
@Configuration
@ComponentScan(basePackages = {
    "com.markyang.framework.service.auth",
    "com.markyang.framework.service.common",
    "com.markyang.framework.service.rabbit",
    "com.markyang.framework.service.core"
})
public class CommonServiceConfig {
}
