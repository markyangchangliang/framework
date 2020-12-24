package com.markyang.framework.service.rabbit;

import com.markyang.framework.pojo.rabbit.RabbitMessage;
import com.markyang.framework.pojo.rabbit.RabbitMessageType;
import com.markyang.framework.service.rabbit.exception.RabbitMessageRequeueException;

/**
 * RabbitMQ消息处理器接口
 *
 * @author yangchangliang
 * @version 1
 */
public interface RabbitMessageHandler {

    /**
     * 处理消息
     * @param queueName 队列名称
     * @param message 消息对象
     * @throws RabbitMessageRequeueException 如果需要消息重新入队，请抛出该异常
     */
    void handle(String queueName, RabbitMessage message) throws RabbitMessageRequeueException;

    /**
     * 能支持消费的消息类型
     * @param queueName 队列名称
     * @param messageType 消息类型
     * @return bool
     */
    boolean support(String queueName, RabbitMessageType messageType);
}
