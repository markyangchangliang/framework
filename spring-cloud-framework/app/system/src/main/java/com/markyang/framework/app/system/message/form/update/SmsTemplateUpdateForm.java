package com.markyang.framework.app.system.message.form.update;

import com.markyang.framework.pojo.auditor.CreatedOrgId;
import com.markyang.framework.service.core.form.AbstractUpdateBaseForm;
import com.markyang.framework.service.core.validator.group.FrameworkGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 短信模板(SmsTemplate)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-05-28 17:40:10
 */
@ApiModel(value = "短信模板数据表单", description = "短信模板表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SmsTemplateUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 短信通道
     */
    @ApiModelProperty(value = "短信通道", allowableValues = "", required = true, position = 2, notes = "")
    @Size(max = 128, message = "短信通道{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "短信通道{required}")
    private String channel;
    /**
     * 机构编号
     */
    @ApiModelProperty(value = "机构编号", allowableValues = "", required = true, position = 1, notes = "")
    @CreatedOrgId
    private String orgId;
    /**
     * 模板审核结果
     */
    @ApiModelProperty(value = "模板审核结果", allowableValues = "", position = 8, notes = "")
    @Size(max = 256, message = "模板审核结果{size}")
    private String templateAuditResult;
    /**
     * 模板内容
     */
    @ApiModelProperty(value = "模板内容", allowableValues = "", required = true, position = 6, notes = "")
    @Size(max = 1024, message = "模板内容{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "模板内容{required}")
    private String templateContent;
    /**
     * 模板编号
     */
    @ApiModelProperty(value = "模板编号", allowableValues = "", required = true, position = 4, notes = "")
    @Size(max = 64, message = "模板编号{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "模板编号{required}")
    private String templateId;
    /**
     * 模板名称
     */
    @ApiModelProperty(value = "模板名称", allowableValues = "", required = true, position = 5, notes = "")
    @Size(max = 256, message = "模板名称{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "模板名称{required}")
    private String templateName;
    /**
     * 模板备注 用于申请腾讯云短信模板
     */
    @ApiModelProperty(value = "模板备注用于申请腾讯云短信模板,varchar(255)", allowableValues = "", required = true, position = 6)
    @Size(max = 255, message = "模板备注{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "模板备注{required}")
    private String templateRemark;

    /**
     * 模板状态
     */
    @ApiModelProperty(value = "模板状态", allowableValues = "", required = true, position = 7, notes = "")
    @NotNull(message = "模板状态{required}")
    private String templateStatus;
    /**
     * 类别
     */
    @ApiModelProperty(value = "类别", allowableValues = "", required = true, position = 3, notes = "")
    @NotNull(message = "类别{required}")
    private String type;

}