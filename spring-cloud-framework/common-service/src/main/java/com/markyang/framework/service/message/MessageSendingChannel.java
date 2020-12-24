package com.markyang.framework.service.message;

import com.markyang.framework.pojo.message.MessageDetails;

/**
 * 模板消息发送通道接口
 *
 * @author yangchangliang
 * @version 1
 */
public interface MessageSendingChannel {

    /**
     * 判断是否是某一个channel
     * @param channelName channel名称
     * @return bool
     */
    boolean is(String channelName);

    /**
     * 消息发送逻辑
     * @param messageDetails 模板消息详情
     * @return boolean
     */
    boolean send(MessageDetails messageDetails);
}
