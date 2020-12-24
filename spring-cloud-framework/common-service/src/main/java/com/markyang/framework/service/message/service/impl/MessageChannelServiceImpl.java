package com.markyang.framework.service.message.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.markyang.framework.pojo.entity.message.MessageChannel;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import com.markyang.framework.service.message.form.search.MessageChannelSearchForm;
import com.markyang.framework.service.message.repository.MessageChannelRepository;
import com.markyang.framework.service.message.service.MessageChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 消息通道服务类
 *
 * @author yangchangliang
 * @version 1
 */
@Service
@Slf4j
public class MessageChannelServiceImpl extends AbstractSearchableServiceImpl<MessageChannel, MessageChannelRepository, MessageChannelSearchForm> implements MessageChannelService {

    /**
     * 获取消息通道
     *
     * @param orgId       机构ID
     * @param channelName 通道名称
     * @return 消息通道
     */
    @Cacheable(cacheNames = "messageChannel", unless = "#result == null")
    @Override
    public Optional<MessageChannel> getMessageChannel(String orgId, String channelName) {
        return Optional.of(this.getOne(Wrappers.<MessageChannel>lambdaQuery().eq(MessageChannel::getOrgId, orgId).eq(MessageChannel::getName, channelName)));
    }

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public MessageChannel create() {
        return MessageChannel.builder().build();
    }
}
