package com.markyang.framework.auth.config;

import com.markyang.framework.config.app.RedisConfig;
import com.markyang.framework.util.RedisUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 通用配置
 * @author markyang
 */
@Configuration
@ComponentScan(basePackages = {
    "com.markyang.framework.config.feign",
    "com.markyang.framework.config.auth",
    "com.markyang.framework.config.exception",
    "com.markyang.framework.config.serializer",
    "com.markyang.framework.config.banner"
})
@Import({
    RedisConfig.class,
    RedisUtils.class
})
public class CommonConfig {
}
