package com.markyang.framework.service.message;

import com.markyang.framework.service.rule.RulesExecutorService;
import com.markyang.framework.pojo.message.MessageDetails;
import org.jeasy.rules.api.Facts;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 规则消息发送通道实现
 *
 * @author yangchangliang
 * @version 1
 */
@Component
public class RulesMessageSendingChannelDispatcher {

    private final RulesExecutorService rulesExecutorService;
    private final List<String> channelNames;

    public RulesMessageSendingChannelDispatcher(ApplicationContext applicationContext, RulesExecutorService rulesExecutorService) {
        this.rulesExecutorService = rulesExecutorService;
        Map<String, MessageSendingChannel> sendingChannelMap = applicationContext.getBeansOfType(MessageSendingChannel.class);
        // 拿到所有的消息发送通道的Bean名称
        this.channelNames = sendingChannelMap.keySet().parallelStream().collect(Collectors.toList());
    }

    /**
     * 消息发送逻辑
     *
     * @param rulesName 规则名称
     * @param messageDetails 模板消息详情
     * @return boolean
     */
    public boolean send(String rulesName, MessageDetails messageDetails) {
        Facts facts = new Facts();
        facts.put("messageDetails", messageDetails);
        // 初始化状态
        facts.put("messageChannelSendingStatus", this.channelNames.parallelStream().collect(Collectors.toMap(channelName -> channelName, __ -> Boolean.FALSE)));
        // 执行规则
        this.rulesExecutorService.execute(rulesName, facts);
        return true;
    }
}
