package com.markyang.framework.app.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@MapperScan({
    "com.markyang.framework.app.system.message.repository",
    "com.markyang.framework.app.system.wx.mp.repository",
    "com.markyang.framework.app.system.repository",
    "com.markyang.framework.service.common.repository",
    "com.markyang.framework.service.message.repository"
})
@ConfigurationPropertiesScan
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
