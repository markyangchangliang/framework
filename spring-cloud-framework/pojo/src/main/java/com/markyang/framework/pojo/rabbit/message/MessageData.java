package com.markyang.framework.pojo.rabbit.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 消息内容基类
 *
 * @author yangchangliang
 * @version 1
 */
@Data
@ApiModel("消息内容基类")
public abstract class MessageData implements Serializable {

    /**
     * 消息类型
     */
    @ApiModelProperty("消息类型")
    private String messageType;
}
