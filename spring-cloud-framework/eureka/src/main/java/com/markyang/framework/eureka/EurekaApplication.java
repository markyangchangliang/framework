package com.markyang.framework.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 注册中心启动类
 * @author yangchangliang
 */
@EnableEurekaServer
@SpringBootApplication(exclude = {
    RabbitAutoConfiguration.class,
    DataSourceAutoConfiguration.class
})
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}
