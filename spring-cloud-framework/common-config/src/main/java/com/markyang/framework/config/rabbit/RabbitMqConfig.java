package com.markyang.framework.config.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置
 *
 * @author yangchangliang
 * @version 1
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 设置RabbitMQ消息转换器
     * @param objectMapper Jackson对象Mapper
     * @return JSON消息转换器
     */
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
