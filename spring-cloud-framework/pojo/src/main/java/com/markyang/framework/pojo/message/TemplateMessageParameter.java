package com.markyang.framework.pojo.message;

import lombok.Builder;
import lombok.Data;

/**
 * 模板消息参数
 *
 * @author yangchangliang
 * @version 1
 */
@Data
@Builder
public class TemplateMessageParameter {

    /**
     * 参数名称
     */
    private String parameterName;

    /**
     * 参数值
     */
    private String parameterValue;

    /**
     * 参数位置
     */
    private int parameterIndex;
}
