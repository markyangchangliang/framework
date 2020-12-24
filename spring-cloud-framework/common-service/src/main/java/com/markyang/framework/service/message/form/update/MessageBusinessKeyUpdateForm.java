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
 * 业务key信息表(MessageBusinessKey)更新表单
 *
 * @author yangchangliang
 * @version 1
 */
@ApiModel(value = "业务key信息表数据表单", description = "业务key信息表表单类")
@Data
@EqualsAndHashCode(callSuper = true)
public class MessageBusinessKeyUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 业务key名称
     */
    @ApiModelProperty(value = "业务key名称", allowableValues = "", required = true, position = 1, notes = "")
    @Size(max = 64, message = "业务key名称{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "业务key名称{required}")
    private String keyName;

    /**
     * 执行类型，a->直接执行，b->按规则执行，c->禁止执行
     */
    @ApiModelProperty(value = "执行类型，a->直接执行，b->按规则执行，c->禁止执行", allowableValues = "", position = 2, notes = "")
    private String executionType;

    /**
     * 规则名称
     */
    @ApiModelProperty(value = "规则名称", allowableValues = "", position = 3, notes = "")
    @Size(max = 64, message = "规则名称{size}")
    private String ruleName;

    /**
     * 执行通道名称
     */
    @ApiModelProperty(value = "执行通道名称", allowableValues = "", position = 4, notes = "")
    @Size(max = 128, message = "执行通道名称{size}")
    private String channelNames;

    /**
     * 参数DTO
     */
    @ApiModelProperty(value = "参数DTO", allowableValues = "", position = 5, notes = "")
    @Size(max = 128, message = "参数DTO{size}")
    @NotBlank(message = "参数DTO{required}")
    private String parameterDto;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", allowableValues = "", position = 6, notes = "")
    @Size(max = 256, message = "备注{size}")
    private String remark;

}