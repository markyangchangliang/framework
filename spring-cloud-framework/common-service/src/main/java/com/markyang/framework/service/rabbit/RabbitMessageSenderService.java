package com.markyang.framework.service.rabbit;

import com.google.common.collect.Maps;
import com.markyang.framework.config.rabbit.RabbitSenderConfigProperties;
import com.markyang.framework.pojo.rabbit.RabbitMessage;
import com.markyang.framework.pojo.rabbit.RabbitMessageType;
import com.markyang.framework.pojo.rabbit.RabbitQueueProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * RabbitMQ消息发送服务类
 *
 * @author yangchangliang
 * @version 1
 */
@Service
@Slf4j
public class RabbitMessageSenderService {

    /**
     * 存储已经发送的消息，方便重发
     */
    private static final Map<String, RabbitMessage> RABBIT_MESSAGE_MAP = Maps.newConcurrentMap();
    /**
     * 存储已经发送消息的队列名称
     */
    private static final Map<String, String> RABBIT_MESSAGE_QUEUE_NAME_MAP = Maps.newConcurrentMap();

    /**
     * RabbitMQ消息操作对象
     */
    private final RabbitTemplate rabbitTemplate;
    
    /**
     * RabbitMQ配置对象
     */
    private final RabbitSenderConfigProperties rabbitSenderConfigProperties;

    @Autowired
    public RabbitMessageSenderService(@Autowired(required = false) RabbitTemplate rabbitTemplate, RabbitSenderConfigProperties rabbitSenderConfigProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitSenderConfigProperties = rabbitSenderConfigProperties;
        if (Objects.nonNull(this.rabbitTemplate)) {
            this.init();
        }
    }

    /**
     * 工具类初始化方法
     */
    private void init() {
        // 注册消息确认回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            assert correlationData != null;
            if (ack) {
                log.info("消息：[{}]发送成功", correlationData.getId());
                return;
            }
            log.error("消息：[{}]发送失败，失败原因：{}", correlationData.getId(), cause);
            // 重新发送一次
            if (RABBIT_MESSAGE_MAP.containsKey(correlationData.getId())) {
                String queueName = RABBIT_MESSAGE_QUEUE_NAME_MAP.get(correlationData.getId());
                RabbitQueueProperties rabbitQueueProperties = rabbitSenderConfigProperties.getQueues().get(queueName);
                rabbitTemplate.convertAndSend(rabbitQueueProperties.getExchange(), rabbitQueueProperties.getRoutingKey(), RABBIT_MESSAGE_MAP.get(correlationData.getId()), correlationData);
                // 重发后移除
                RABBIT_MESSAGE_MAP.remove(correlationData.getId());
            }
        });
        // 注册消息发送失败回调
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> log.error("发送到交换机[{}]且路由键为[{}]的消息[{}]发送失败，失败码[{}]，失败原因[{}]", exchange, routingKey, new String(message.getBody(), StandardCharsets.UTF_8), replyCode, replyText));
    }

    /**
     * 发送消息
     *
     * @param data        消息内容对象 最后都会转为JSON
     * @param messageType 消息类型
     */
    public void sendMessage(String queueName, RabbitMessageType messageType, Object data) {
        if (!rabbitSenderConfigProperties.getQueues().containsKey(queueName)) {
            log.error("队列[{}]配置不存在，消息发送失败", queueName);
            return;
        }
        RabbitQueueProperties rabbitQueueProperties = rabbitSenderConfigProperties.getQueues().get(queueName);
        String uuid = UUID.randomUUID().toString();
        RabbitMessage rabbitMessage = RabbitMessage.of(uuid, messageType, data);
        RABBIT_MESSAGE_MAP.put(uuid, rabbitMessage);
        RABBIT_MESSAGE_QUEUE_NAME_MAP.put(uuid, queueName);
        rabbitTemplate.convertAndSend(rabbitQueueProperties.getExchange(), rabbitQueueProperties.getRoutingKey(), rabbitMessage, new CorrelationData(uuid));
    }

}
