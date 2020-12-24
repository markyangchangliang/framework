package com.markyang.framework.gateway.config;

import com.markyang.framework.config.app.RedisConfig;
import com.markyang.framework.util.RedisUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 通用配置
 * @author yangchangliang
 */
@Configuration
@ComponentScan(basePackages = {
    "com.markyang.framework.config.auth",
    "com.markyang.framework.config.exception",
    "com.markyang.framework.config.banner",
    "com.markyang.framework.config.feign"
})
@Import({
    RedisConfig.class,
    RedisUtils.class
})
public class CommonConfig {
}
