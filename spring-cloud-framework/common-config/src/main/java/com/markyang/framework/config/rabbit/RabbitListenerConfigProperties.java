package com.markyang.framework.config.rabbit;

import com.google.common.collect.Maps;
import com.markyang.framework.pojo.rabbit.RabbitQueueProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Rabbit消息队列监听配置
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@ConfigurationProperties(prefix = "framework.rabbit.listener")
@Data
public class RabbitListenerConfigProperties {

    /**
     * 是否开启Rabbit消息监听
     */
    private boolean enabled = false;

    /**
     * 监听的队列
     */
    private Map<String, RabbitQueueProperties> queues = Maps.newHashMap();
}
