package com.markyang.framework.service.message.service;

import com.markyang.framework.service.core.service.SearchableService;
import com.markyang.framework.service.message.form.search.MessageChannelSearchForm;
import com.markyang.framework.pojo.entity.message.MessageChannel;

import java.util.Optional;

/**
 * 消息通道服务接口
 *
 * @author yangchangliang
 * @version 1
 */
public interface MessageChannelService extends SearchableService<MessageChannel, MessageChannelSearchForm> {

    /**
     * 获取消息通道
     * @param orgId 机构ID
     * @param channelName 通道名称
     * @return 消息通道
     */
    Optional<MessageChannel> getMessageChannel(String orgId, String channelName);
}
