package com.markyang.framework.pojo.rabbit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RabbitMQ消息
 *
 * @author yangchangliang
 * @version 1
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class RabbitMessage {

    /**
     * 消息ID
     */
    private String messageId;
    /**
     * 消息类型
     */
    private RabbitMessageType messageType;
    /**
     * 消息数据
     */
    private Object data;
}
