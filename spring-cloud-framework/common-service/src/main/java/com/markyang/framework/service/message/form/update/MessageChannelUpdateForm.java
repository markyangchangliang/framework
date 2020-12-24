package com.markyang.framework.service.message.form.update;

import com.markyang.framework.service.core.form.AbstractUpdateBaseForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


 /**
 * 消息配置(MessageChannel)更新表单
 *
 * @author yangchangliang
 * @version 1
 */
@ApiModel(value = "消息配置数据表单", description = "消息配置表单类")
@Data
@EqualsAndHashCode(callSuper = true)
public class MessageChannelUpdateForm extends AbstractUpdateBaseForm {
    
    /**
    * 消息通道名称
    */    
    @ApiModelProperty(value = "消息通道名称", allowableValues = "", required = true, position = 2, notes = "")
    @Size(max = 32, message = "消息通道名称{size}")
    @NotBlank(message = "消息通道名称{required}")
    private String name;    
    
    /**
    * 通道配置
    */    
    @ApiModelProperty(value = "通道配置", allowableValues = "", required = true, position = 3, notes = "")
    @NotNull(message = "通道配置{required}")
    private String configuration;    
    
    /**
    * 备注
    */    
    @ApiModelProperty(value = "备注", allowableValues = "", position = 4, notes = "")
    @Size(max = 256, message = "备注{size}")
    private String remark;    
    
}