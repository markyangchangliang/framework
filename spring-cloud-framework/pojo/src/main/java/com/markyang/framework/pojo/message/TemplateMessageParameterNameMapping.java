package com.markyang.framework.pojo.message;

import lombok.Data;

/**
 * 模板参数名称映射
 *
 * @author yangchangliang
 * @version 1
 */
@Data
public class TemplateMessageParameterNameMapping {

    /**
     * 消息详情参数名称
     */
    private String parameterName;

    /**
     * 模板消息参数名称
     */
    private String templateParameterName;

    /**
     * 模板参数位置
     */
    private int templateParameterIndex;
}
