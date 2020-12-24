package com.markyang.framework.service.rabbit;

import com.google.common.collect.Maps;
import com.rabbitmq.client.Channel;
import com.markyang.framework.pojo.rabbit.RabbitMessage;
import com.markyang.framework.service.rabbit.exception.RabbitMessageRequeueException;
import com.markyang.framework.util.BeanOperationUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Rabbit消息处理器服务类
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@AllArgsConstructor
@Slf4j
public class RabbitMessageHandlerService {

    private static final Map<String, Integer> MESSAGE_REQUEUE_COUNT_MAP = Maps.newConcurrentMap();
    private final List<RabbitMessageHandler> rabbitMessageHandlers;

    /**
     * 消息处理逻辑
     * @param queueName 队列名
     * @param message 消息
     * @param deliveryTag 唯一标识
     * @param channel 通道
     * @throws IOException 异常
     */
    @SuppressWarnings("unchecked")
    public void handleMessage(String queueName, RabbitMessage message, long deliveryTag, Channel channel) throws IOException {
        log.info("收到新的RabbitMQ消息：{}", message);
        log.info("新消息的DeliveryTag：{}", deliveryTag);
        // 调用自定义实现
        try {
            // 尝试转换数据类
            Class<?> clazz = message.getMessageType().getClazz();
            if (ClassUtils.isPrimitiveOrWrapper(clazz) ||
                ClassUtils.isAssignable(CharSequence.class, clazz) ||
                ClassUtils.isAssignable(Collection.class, clazz) ||
                ClassUtils.isAssignable(Map.class, clazz)) {
                // 集合类型和原始类型直接返回
                return;
            }
            Object data = message.getData();
            if (data instanceof Map) {
                message.setData(BeanOperationUtils.fromMap(clazz, (Map<String, Object>) data));
            } else if (data instanceof List) {
                // List，尝试转换子对象
                List<Object> objectList = (List<Object>) data;
                if ((objectList).size() > 0 && (objectList).get(0) instanceof Map) {
                    // 就默认它是List<Map<String, Object>>类型
                    message.setData(objectList
                        .parallelStream()
                        .map(map -> BeanOperationUtils.fromMap(clazz, (Map<String, Object>) map))
                        .collect(Collectors.toList()));
                }
            }
            for (RabbitMessageHandler rabbitMessageHandler : this.rabbitMessageHandlers) {
                if (rabbitMessageHandler.support(queueName, message.getMessageType())) {
                    rabbitMessageHandler.handle(queueName, message);
                }
            }
        } catch (RabbitMessageRequeueException e) {
            // 重新入队
            String messageId = message.getMessageId();
            if (MESSAGE_REQUEUE_COUNT_MAP.containsKey(messageId)) {
                if (MESSAGE_REQUEUE_COUNT_MAP.get(messageId) >= 3) {
                    // 超过三次了，不进行重新入队了，放弃了
                    channel.basicAck(deliveryTag, false);
                    MESSAGE_REQUEUE_COUNT_MAP.remove(messageId);
                } else {
                    // 没超过三次，入队次数累加
                    channel.basicNack(deliveryTag, false, true);
                    MESSAGE_REQUEUE_COUNT_MAP.put(messageId, MESSAGE_REQUEUE_COUNT_MAP.get(messageId) + 1);
                }
            } else {
                // 第一次重新入队
                MESSAGE_REQUEUE_COUNT_MAP.put(messageId, 1);
                channel.basicNack(deliveryTag, false, true);
            }
        } finally {
            // 签收消息
            channel.basicAck(deliveryTag, false);
        }
    }
}
