package com.markyang.framework.config.rabbit;

import com.markyang.framework.pojo.rabbit.RabbitQueueProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Rabbit消息发送配置
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@ConfigurationProperties(prefix = "framework.rabbit.sender")
@Data
public class RabbitSenderConfigProperties {

    /**
     * 发送Rabbit消息的队列配置
     */
    private Map<String, RabbitQueueProperties> queues;
}
