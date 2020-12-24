package com.markyang.framework.service.message.service;

import com.markyang.framework.service.core.service.SearchableService;
import com.markyang.framework.service.message.form.search.MessageTemplateSearchForm;
import com.markyang.framework.pojo.entity.message.MessageTemplate;

import java.util.Optional;

/**
 * 消息模板服务接口
 *
 * @author yangchangliang
 * @version 1
 */
public interface MessageTemplateService extends SearchableService<MessageTemplate, MessageTemplateSearchForm> {

    /**
     * 获取消息模板
     * @param orgId 机构ID
     * @param channelName 通道名称
     * @param businessKey 业务key
     * @return 消息模板
     */
    Optional<MessageTemplate> getMessageTemplate(String orgId, String channelName, String businessKey);
}
