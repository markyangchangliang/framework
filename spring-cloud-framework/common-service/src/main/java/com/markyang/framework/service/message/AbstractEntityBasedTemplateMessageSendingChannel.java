package com.markyang.framework.service.message;

import com.google.gson.reflect.TypeToken;
import com.markyang.framework.pojo.entity.message.MessageChannel;
import com.markyang.framework.pojo.entity.message.MessageTemplate;
import com.markyang.framework.pojo.message.TemplateMessageConfiguration;
import com.markyang.framework.pojo.message.TemplateMessageParameterNameMapping;
import com.markyang.framework.util.JsonUtils;

import java.util.List;
import java.util.Map;

/**
 * 模板消息发送，基于JDBC的实现
 *
 * @author yangchangliang
 * @version 1
 */
public abstract class AbstractEntityBasedTemplateMessageSendingChannel extends AbstractTemplateMessageSendingChannel {
    /**
     * 获取模板消息配置
     *
     * @param orgId       机构ID
     * @param businessKey 业务key
     * @return 配置
     */
    @Override
    protected TemplateMessageConfiguration getTemplateMessageConfiguration(String orgId, String businessKey) {
        MessageChannel messageChannel = this.getMessageChannel(orgId);
        MessageTemplate messageTemplate = this.getMessageTemplate(orgId, businessKey);
        return TemplateMessageConfiguration.builder()
            .channelConfiguration(JsonUtils.fromJson(messageChannel.getConfiguration(), TypeToken.getParameterized(Map.class, String.class, String.class).getType()))
            .templateMessageId(messageTemplate.getTemplateId())
            .templateMessageName(messageTemplate.getTemplateName())
            .templateMessageContent(messageTemplate.getTemplateContent())
            .templateMessageParameterNameMappings(JsonUtils.fromJson(messageTemplate.getTemplateParameterMappings(), TypeToken.getParameterized(List.class, TemplateMessageParameterNameMapping.class).getType()))
            .build();
    }

    /**
     * 获取消息通道实体对象
     * @param orgId 机构ID
     * @return 消息通道
     */
    protected abstract MessageChannel getMessageChannel(String orgId);

    /**
     * 获取消息模板对象
     * @param orgId 机构ID
     * @param businessKey 业务标识
     * @return 模板对象
     */
    protected abstract MessageTemplate getMessageTemplate(String orgId, String businessKey);
}
