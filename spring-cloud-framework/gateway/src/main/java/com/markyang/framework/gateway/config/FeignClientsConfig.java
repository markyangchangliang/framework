package com.markyang.framework.gateway.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * feign调用配置
 * @author yangchangliang
 */
@Configuration
@EnableFeignClients(basePackages = "com.markyang.framework.client.system")
@ComponentScan(basePackages = "com.markyang.framework.client.system.**.fallback")
public class FeignClientsConfig {
}
