package com.markyang.framework.service.message;

import java.util.List;

/**
 * 消息发送渠道解析接口
 *
 * @author yangchangliang
 * @version 1
 */
public interface MessageSendingChannelResolver {

    /**
     * 解析消息发送渠道
     * @param orgId 机构ID
     * @param businessKey 业务标识
     * @return 消息发送渠道实现集合
     */
    List<MessageSendingChannel> resolve(String orgId, String businessKey);
}
