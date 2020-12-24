package com.markyang.framework.pojo.rabbit;

import com.markyang.framework.pojo.rabbit.message.MessageData;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * RabbitMQ消息类型
 *
 * @author yangchangliang
 * @version 1
 */
@AllArgsConstructor
@Getter
public enum RabbitMessageType {
    /**
     * 队列相关的类型
     *
     * QueuingDto
     */
    RSV_QUEUING(MessageData.class);


    /**
     * 相关联类
     */
    private final Class<?> clazz;
}
