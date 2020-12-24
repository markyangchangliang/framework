package com.markyang.framework.service.message;

import com.google.common.collect.Lists;
import com.markyang.framework.config.message.MessageConfigProperties;
import com.markyang.framework.service.message.exception.BusinessKeyNoChannelNameMappingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * yml配置通道解析实现
 *
 * @author yangchangliang
 * @version 1
 */
@ConditionalOnProperty(prefix = "framework.message", name = "mapping-store-type", havingValue = "yml")
@Component
@Slf4j
@AllArgsConstructor
public class YmlConfigurationMessageChannelResolver implements MessageSendingChannelResolver {

    private final MessageConfigProperties messageConfigProperties;
    private final RulesMessageSendingChannelDispatcher rulesMessageSendingChannelDispatcher;
    private final List<MessageSendingChannel> messageSendingChannels;
    /**
     * 解析消息发送渠道
     *
     * @param orgId 机构ID
     * @param businessKey 业务标识
     * @return 消息发送渠道实现集合
     */
    @Override
    public List<MessageSendingChannel> resolve(String orgId, String businessKey) {
        MessageConfigProperties.MessageChannelConfig messageChannelConfig = this.messageConfigProperties.getBusinessKeyChannelNamesMapping().get(businessKey);
        if (MessageConfigProperties.ChannelExecutionType.DIRECT.equals(messageChannelConfig.getExecutionType())) {
            if (CollectionUtils.isEmpty(messageChannelConfig.getMessageChannels())) {
                throw new BusinessKeyNoChannelNameMappingException("业务key[" + businessKey + "]没有对应的消息通道名称映射");
            }
            return this.messageSendingChannels.parallelStream()
                .filter(messageSendingChannel -> messageChannelConfig.getMessageChannels()
                    .parallelStream()
                    .anyMatch(messageSendingChannel::is))
                .collect(Collectors.toList());
        }
        // 规则执行
        return Lists.newArrayList(
            new RulesMessageSendingChannel(messageChannelConfig.getRulesName(), this.rulesMessageSendingChannelDispatcher)
        );
    }
}
