package com.markyang.framework.app.base.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 公共配置
 *
 * @author yangchangliang
 */
@ComponentScan(basePackages = {
    "com.markyang.framework.config.app",
    "com.markyang.framework.config.auth",
    "com.markyang.framework.config.feign",
    "com.markyang.framework.config.exception",
    "com.markyang.framework.config.serializer",
    "com.markyang.framework.config.deserializer",
    "com.markyang.framework.config.banner",
    "com.markyang.framework.config.rabbit",
    "com.markyang.framework.config.mybatis",
    "com.markyang.framework.util"
})
@Configuration
public class CommonConfig {
}
