package com.markyang.framework.service.message.wx;

import cn.binarywang.wx.miniapp.api.WxMaSubscribeService;
import com.google.common.collect.Lists;
import com.markyang.framework.service.message.MessageChannelTemplateService;
import com.markyang.framework.pojo.message.MessageTemplateDetails;
import com.markyang.framework.pojo.message.MessageTemplateParameterDetails;
import com.markyang.framework.service.message.exception.MessageException;
import com.markyang.framework.service.wx.CompositeWxMaService;
import com.markyang.framework.util.OrgInfoUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 微信小程序模板消息服务类实现
 *
 * @author yangchangliang
 * @version 1
 */
@ConditionalOnBean(CompositeWxMaService.class)
@Component
@Slf4j
@AllArgsConstructor
public class WxMiniAppMessageChannelTemplateService implements MessageChannelTemplateService {

    private final CompositeWxMaService compositeWxMaService;

    /**
     * 是否支持服务指定通道
     *
     * @param channelName 通道名称
     * @return 是否支持
     */
    @Override
    public boolean supports(String channelName) {
        return StringUtils.equals(WxMiniAppTemplateMessageSendingChannel.MESSAGE_CHANNEL_NAME, channelName);
    }

    /**
     * 获取模板列表
     *
     * @param orgId 机构ID
     * @return 模板列表
     */
    @Override
    public List<MessageTemplateDetails> getTemplates(String orgId) {
        WxMaSubscribeService subscribeService = this.compositeWxMaService.getSubscribeService(OrgInfoUtils.getOrgWxMiniAppId(orgId).orElse(""));
        try {
            List<WxMaSubscribeService.TemplateInfo> templateList = subscribeService.getTemplateList();
            return templateList.parallelStream().map(templateInfo -> {
                MessageTemplateDetails messageTemplateDetails = MessageTemplateDetails.builder()
                    .templateId(templateInfo.getPriTmplId())
                    .templateName(templateInfo.getTitle())
                    .templateContent(templateInfo.getContent())
                    .templateDesc(templateInfo.getExample())
                    .additionalInformation("type", templateInfo.getType() == 2 ? "一次性订阅" : "长期订阅")
                    .build();
                messageTemplateDetails.setTemplateParameters(this.getTemplateParameters(orgId, messageTemplateDetails));
                return messageTemplateDetails;
            }).collect(Collectors.toList());
        } catch (WxErrorException e) {
            log.error("获取小程序订阅模板消息列表失败：{}", e.getError().getErrorMsg());
            throw new MessageException("获取小程序模板消息列表失败");
        }
    }

    /**
     * 获取模板参数信息
     *
     * @param orgId 机构ID
     * @param templateId 模板ID
     * @return 模板参数
     */
    @Override
    public List<MessageTemplateParameterDetails> getTemplateParameters(String orgId, String templateId) {
        return Collections.emptyList();
    }

    /**
     * 获取模板参数信息
     *
     * @param orgId 机构ID
     * @param messageTemplateDetails 模板对象
     * @return 模板参数
     */
    @Override
    public List<MessageTemplateParameterDetails> getTemplateParameters(String orgId, MessageTemplateDetails messageTemplateDetails) {
        String content = messageTemplateDetails.getTemplateContent();
        String[] tokens = StringUtils.split(content, "\n");
        if (ArrayUtils.isEmpty(tokens)) {
            return Collections.emptyList();
        }
        List<MessageTemplateParameterDetails> messageTemplateParameterDetails = Lists.newArrayList();
        for (int i = 0; i < tokens.length; i++) {
            String[] split = StringUtils.split(tokens[i], ":");
            if (ArrayUtils.isEmpty(split) || split.length != 2) {
                continue;
            }
            String name = StringUtils.substringBetween(split[1], "{{", ".DATA}}");
            if (StringUtils.isBlank(name)) {
                continue;
            }
            messageTemplateParameterDetails.add(
                MessageTemplateParameterDetails.builder()
                    .parameterId(String.valueOf(i + 1))
                    .parameterName(name)
                    .parameterDesc(split[0])
                    .build()
            );
        }
        return messageTemplateParameterDetails;
    }
}
