package com.markyang.framework.service.message.form.update;

import com.markyang.framework.service.core.form.AbstractUpdateBaseForm;
import com.markyang.framework.service.core.validator.group.FrameworkGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


 /**
 * 消息模板(MessageTemplate)更新表单
 *
 * @author yangchangliang
 * @version 1
 */
@ApiModel(value = "消息模板数据表单", description = "消息模板表单类")
@Data
@EqualsAndHashCode(callSuper = true)
public class MessageTemplateUpdateForm extends AbstractUpdateBaseForm {
    
    /**
    * 通道名称
    */    
    @ApiModelProperty(value = "通道名称", allowableValues = "", required = true, position = 2, notes = "")
    @Size(max = 32, message = "通道名称{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "通道名称{required}")
    private String channelName;    
    
    /**
    * 业务标识符
    */    
    @ApiModelProperty(value = "业务标识符", allowableValues = "", required = true, position = 3, notes = "")
    @Size(max = 64, message = "业务标识符{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "业务标识符{required}")
    private String businessKey;    
    
    /**
    * 模板ID
    */    
    @ApiModelProperty(value = "模板ID", allowableValues = "", required = true, position = 4, notes = "")
    @Size(max = 128, message = "模板ID{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "模板ID{required}")
    private String templateId;    
    
    /**
    * 模板名称
    */    
    @ApiModelProperty(value = "模板名称", allowableValues = "", position = 5, notes = "")
    @Size(max = 32, message = "模板名称{size}")
    private String templateName;    
    
    /**
    * 模板内容
    */    
    @ApiModelProperty(value = "模板内容", allowableValues = "", position = 6, notes = "")
    private String templateContent;    
    
    /**
    * 模板参数映射
    */    
    @ApiModelProperty(value = "模板参数映射", allowableValues = "", position = 7, notes = "")
    private String templateParameterMappings;    
    
}