package com.markyang.framework.pojo.message;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 模板消息配置对象
 *
 * @author yangchangliang
 * @version 1
 */
@Data
@Builder
public class TemplateMessageConfiguration {

    /**
     * 通道配置
     */
    private Map<String, String> channelConfiguration;

    /**
     * 模板ID
     */
    private String templateMessageId;

    /**
     * 模板名称
     */
    private String templateMessageName;

    /**
     * 消息内容
     */
    private String templateMessageContent;

    /**
     * 模板消息参数名称映射
     */
    private List<TemplateMessageParameterNameMapping> templateMessageParameterNameMappings;
}
