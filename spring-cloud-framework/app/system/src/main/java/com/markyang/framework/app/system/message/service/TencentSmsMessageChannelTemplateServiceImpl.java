package com.markyang.framework.app.system.message.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.markyang.framework.pojo.entity.system.message.SmsTemplate;
import com.markyang.framework.pojo.message.MessageTemplateDetails;
import com.markyang.framework.pojo.message.MessageTemplateParameterDetails;
import com.markyang.framework.service.message.MessageChannelTemplateService;
import com.markyang.framework.service.message.sms.TencentSmsTemplateMessageSendingChannel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 腾讯短信模板
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/28 2:20 下午 星期四
 */
@Component
@Slf4j
@AllArgsConstructor
public class TencentSmsMessageChannelTemplateServiceImpl implements MessageChannelTemplateService {

    private final SmsTemplateService smsTemplateService;

    /**
     * 是否支持服务指定通道
     *
     * @param channelName 通道名称
     * @return 是否支持
     */
    @Override
    public boolean supports(String channelName) {
        return StringUtils.equals(TencentSmsTemplateMessageSendingChannel.MESSAGE_CHANNEL_NAME, channelName);
    }

    /**
     * 获取模板列表
     *
     * @param orgId 机构ID
     * @return 模板列表
     */
    @Override
    public List<MessageTemplateDetails> getTemplates(String orgId) {
        List<SmsTemplate> smsTemplateList = this.smsTemplateService.list(Wrappers.<SmsTemplate>lambdaQuery().eq(SmsTemplate::getOrgId, orgId));
        List<MessageTemplateDetails> messageTemplateDetailsList = new ArrayList<>();
        for (SmsTemplate templateInfo : smsTemplateList) {
            MessageTemplateDetails messageTemplateDetails = MessageTemplateDetails.builder()
                    .templateId(templateInfo.getTemplateId())
                    .templateName(templateInfo.getTemplateName())
                    .templateContent(templateInfo.getTemplateContent())
                    .templateDesc(templateInfo.getTemplateContent())
                    .build();
            messageTemplateDetails.setTemplateParameters(this.getTemplateParameters(orgId, messageTemplateDetails));
            messageTemplateDetailsList.add(messageTemplateDetails);
        }
        return messageTemplateDetailsList;
    }

    /**
     * 获取模板参数信息
     *
     * @param orgId      机构ID
     * @param templateId 模板ID
     * @return 模板参数
     */
    @Override
    public List<MessageTemplateParameterDetails> getTemplateParameters(String orgId, String templateId) {
        SmsTemplate templateInfo = this.smsTemplateService.getOne(Wrappers.<SmsTemplate>lambdaQuery().eq(SmsTemplate::getOrgId, orgId).eq(SmsTemplate::getTemplateId, templateId));
        if (ObjectUtils.isNotEmpty(templateInfo)) {
            MessageTemplateDetails messageTemplateDetails = MessageTemplateDetails.builder()
                    .templateId(templateInfo.getTemplateId())
                    .templateName(templateInfo.getTemplateName())
                    .templateContent(templateInfo.getTemplateContent())
                    .templateDesc(templateInfo.getTemplateContent())
                    .build();
            return this.getTemplateParameters(orgId, messageTemplateDetails);
        }
        return null;
    }

    /**
     * 获取模板参数信息
     *
     * @param orgId                  机构ID
     * @param messageTemplateDetails 模板对象
     * @return 模板参数
     */
    @Override
    public List<MessageTemplateParameterDetails> getTemplateParameters(String orgId, MessageTemplateDetails messageTemplateDetails) {
        String content = messageTemplateDetails.getTemplateContent();
        if (StringUtils.isEmpty(content)) {
            return Collections.emptyList();
        }
        List<MessageTemplateParameterDetails> messageTemplateParameterDetails = Lists.newArrayList();
        PropertyPlaceholderHelper propertyPlaceholderHelper = new PropertyPlaceholderHelper("{", "}", ":", true);
        propertyPlaceholderHelper.replacePlaceholders(content, new PropertyPlaceholderHelper.PlaceholderResolver() {
            private int index = 1;

            @Override
            public String resolvePlaceholder(@NotNull String s) {
                messageTemplateParameterDetails.add(MessageTemplateParameterDetails.builder()
                        .parameterId(String.valueOf(index++)).parameterName(s).parameterDesc(s).build());
                return null;
            }
        });
        return messageTemplateParameterDetails;
    }

}
