package com.markyang.framework.service.message;

import com.markyang.framework.pojo.message.MessageTemplateDetails;
import com.markyang.framework.pojo.message.MessageTemplateParameterDetails;

import java.util.List;

/**
 * 消息通道模板服务接口
 *
 * @author yangchangliang
 * @version 1
 */
public interface MessageChannelTemplateService {

    /**
     * 是否支持服务指定通道
     * @param channelName 通道名称
     * @return 是否支持
     */
    boolean supports(String channelName);

    /**
     * 获取模板列表
     * @param orgId 机构ID
     * @return 模板列表
     */
    List<MessageTemplateDetails> getTemplates(String orgId);

    /**
     * 获取模板参数信息
     * @param orgId 机构ID
     * @param templateId 模板ID
     * @return 模板参数
     */
    List<MessageTemplateParameterDetails> getTemplateParameters(String orgId, String templateId);

    /**
     * 获取模板参数信息
     * @param orgId 机构ID
     * @param messageTemplateDetails 模板对象
     * @return 模板参数
     */
    List<MessageTemplateParameterDetails> getTemplateParameters(String orgId, MessageTemplateDetails messageTemplateDetails);
}
