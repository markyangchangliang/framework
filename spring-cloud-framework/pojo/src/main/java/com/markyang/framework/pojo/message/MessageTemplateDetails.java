package com.markyang.framework.pojo.message;

import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * 消息模板
 *
 * @author yangchangliang
 * @version 1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageTemplateDetails {

    /**
     * 模板ID
     */
    private String templateId;
    /**
     * 模板名称
     */
    private String templateName;
    /**
     * 模板描述
     */
    private String templateDesc;
    /**
     * 模板内容
     */
    private String templateContent;
    /**
     * 模板参数
     */
    private List<MessageTemplateParameterDetails> templateParameters;
    /**
     * 额外信息
     */
    @Singular("additionalInformation")
    private Map<String, Object> additionalInformation;
}
