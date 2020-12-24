package com.markyang.framework.service.message;

import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import com.markyang.framework.config.message.MessageConfigProperties;
import com.markyang.framework.pojo.entity.message.MessageBusinessKey;
import com.markyang.framework.service.message.exception.BusinessKeyNoChannelNameMappingException;
import com.markyang.framework.service.message.exception.MessageException;
import com.markyang.framework.service.message.service.MessageBusinessKeyService;
import com.markyang.framework.util.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * JDBC配置通道解析实现
 *
 * @author yangchangliang
 * @version 1
 */
@ConditionalOnProperty(prefix = "framework.message", name = "mapping-store-type", havingValue = "jdbc")
@Component
@Slf4j
@AllArgsConstructor
public class JdbcConfigurationMessageChannelResolver implements MessageSendingChannelResolver {

    private final MessageBusinessKeyService messageBusinessKeyService;
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
        MessageBusinessKey messageBusinessKey = this.messageBusinessKeyService.getBusinessKey(orgId, businessKey).orElse(null);
        if (Objects.isNull(messageBusinessKey)) {
            throw new MessageException("没有找到业务Key[" + businessKey + "]相关的数据库配置");
        }
        if (StringUtils.equals(messageBusinessKey.getExecutionType(), MessageConfigProperties.ChannelExecutionType.DIRECT.getValue())) {
            String[] tokens = JsonUtils.fromJson(messageBusinessKey.getChannelNames(), TypeToken.getArray(String.class).getType());
            if (ArrayUtils.isEmpty(tokens)) {
                throw new BusinessKeyNoChannelNameMappingException("业务key[" + businessKey + "]没有对应的消息通道名称映射");
            }
            List<String> messageChannels = Arrays.asList(tokens);
            return this.messageSendingChannels.parallelStream()
                .filter(messageSendingChannel -> messageChannels
                    .parallelStream()
                    .anyMatch(messageSendingChannel::is))
                .collect(Collectors.toList());
        } else if (StringUtils.equals(messageBusinessKey.getExecutionType(), MessageConfigProperties.ChannelExecutionType.RULE.getValue())) {
            // 规则执行
            return Lists.newArrayList(
                new RulesMessageSendingChannel(messageBusinessKey.getRuleName(), this.rulesMessageSendingChannelDispatcher)
            );
        }
        return Collections.emptyList();
    }
}
