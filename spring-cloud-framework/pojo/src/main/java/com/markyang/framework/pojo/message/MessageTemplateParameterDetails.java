package com.markyang.framework.pojo.message;

import lombok.*;

import java.util.Map;

/**
 * 消息模板变量
 *
 * @author yangchangliang
 * @version 1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageTemplateParameterDetails {

    /**
     * 参数ID
     */
    private String parameterId;
    /**
     * 参数名称
     */
    private String parameterName;
    /**
     * 参数描述
     */
    private String parameterDesc;
    /**
     * 额外信息
     */
    @Singular("additionalInformation")
    private Map<String, Object> additionalInformation;
}
