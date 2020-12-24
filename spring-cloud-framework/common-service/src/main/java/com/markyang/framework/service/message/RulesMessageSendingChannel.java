package com.markyang.framework.service.message;

import com.markyang.framework.pojo.message.MessageDetails;
import lombok.AllArgsConstructor;

/**
 * 规则消息通道实现
 *
 * @author yangchangliang
 * @version 1
 */
@AllArgsConstructor
public class RulesMessageSendingChannel implements MessageSendingChannel {

    /**
     * 规则名称
     */
    private final String rulesName;
    /**
     * 规则消息发送调度器
     */
    private final RulesMessageSendingChannelDispatcher rulesMessageSendingChannelDispatcher;
    /**
     * 判断是否是某一个channel
     *
     * @param channelName channel名称
     * @return bool
     */
    @Override
    public boolean is(String channelName) {
        return true;
    }

    /**
     * 消息发送逻辑
     *
     * @param messageDetails 模板消息详情
     */
    @Override
    public boolean send(MessageDetails messageDetails) {
        return this.rulesMessageSendingChannelDispatcher.send(this.rulesName, messageDetails);
    }
}
