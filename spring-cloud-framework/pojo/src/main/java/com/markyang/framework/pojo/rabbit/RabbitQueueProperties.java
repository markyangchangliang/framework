package com.markyang.framework.pojo.rabbit;

import lombok.Data;

/**
 * Rabbit监听队列配置
 *
 * @author yangchangliang
 * @version 1
 */
@Data
public class RabbitQueueProperties {

    /**
     * 交换机名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String queue;
    /**
     * 交换机到队列的路由键
     */
    private String routingKey;
}
