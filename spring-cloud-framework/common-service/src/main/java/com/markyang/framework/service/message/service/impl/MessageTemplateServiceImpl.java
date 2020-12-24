package com.markyang.framework.service.message.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.markyang.framework.pojo.entity.message.MessageTemplate;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import com.markyang.framework.service.message.form.search.MessageTemplateSearchForm;
import com.markyang.framework.service.message.repository.MessageTemplateRepository;
import com.markyang.framework.service.message.service.MessageTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 消息模板实现类
 *
 * @author yangchangliang
 * @version 1
 */
@Service
@Slf4j
public class MessageTemplateServiceImpl extends AbstractSearchableServiceImpl<MessageTemplate, MessageTemplateRepository, MessageTemplateSearchForm> implements MessageTemplateService {

    /**
     * 获取消息模板
     *
     * @param orgId       机构ID
     * @param channelName 通道名称
     * @param businessKey 业务key
     * @return 消息模板
     */
    @Cacheable(cacheNames = "messageTemplate", unless = "#result == null")
    @Override
    public Optional<MessageTemplate> getMessageTemplate(String orgId, String channelName, String businessKey) {
        return Optional.ofNullable(this.getOne(Wrappers.<MessageTemplate>lambdaQuery().eq(MessageTemplate::getOrgId, orgId).eq(MessageTemplate::getChannelName, channelName).eq(MessageTemplate::getBusinessKey, businessKey)));
    }

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public MessageTemplate create() {
        return MessageTemplate.builder().build();
    }
}
