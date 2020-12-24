package com.markyang.framework.app.system.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.markyang.framework.app.base",
    "com.markyang.framework.service.message",
    "com.markyang.framework.config.message",
    "com.markyang.framework.config.tencent",
    "com.markyang.framework.service.tencent",
    "com.markyang.framework.config.rule",
    "com.markyang.framework.service.rule"
})
public class BaseAppConfig {
}
