package com.markyang.framework.gateway;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**网关启动类
 * @author yangchangliang
 */
@SpringBootApplication(exclude = {
    RabbitAutoConfiguration.class,
    DataSourceAutoConfiguration.class,
    DruidDataSourceAutoConfigure.class
})
@EnableDiscoveryClient
@EnableZuulProxy
@EnableConfigurationProperties
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
