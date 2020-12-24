package com.markyang.framework.app.system.form.update;

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
import javax.validation.constraints.Size;


/**
 * 系统选项(SystemOption)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:30:07
 */
@ApiModel(value = "系统选项数据表单", description = "系统选项表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SystemOptionUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称", allowableValues = "", required = true, position = 1)
    @Size(max = 128, message = "应用名称{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "应用名称{required}")
    private String appId;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号", allowableValues = "", required = true, position = 1)
    @Size(max = 128, message = "编号{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "编号{required}")
    private String key;

    /**
     * 值
     */
    @ApiModelProperty(value = "值", allowableValues = "", required = true, position = 2)
    @Size(max = 128, message = "值{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "值{required}")
    private String value;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", allowableValues = "", position = 3)
    @Size(max = 1024, message = "备注{size}")
    private String remark;

}