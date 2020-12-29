package com.markyang.framework.auth.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * feign client扫描配置
 * @author markyang
 */
@Configuration
@EnableFeignClients(basePackages = {
    "com.markyang.framework.client.system"
})
@ComponentScan(basePackages = {
    "com.markyang.framework.client.system.**.fallback"
})
public class FeignClientsConfig {
}
