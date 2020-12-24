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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 项目分成比例(ItemFeeRatio)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:30:07
 */
@ApiModel(value = "项目分成比例数据表单", description = "项目分成比例表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class ItemFeeRatioUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 机构编号
     */
    @ApiModelProperty(value = "机构编号", allowableValues = "", required = true, position = 1)
    @NotNull(message = "机构编号{required}")
    private String orgId;

    /**
     * 部门编号
     */
    @ApiModelProperty(value = "部门编号", allowableValues = "", required = true, position = 2)
    @NotNull(message = "部门编号{required}")
    private String deptId;

    /**
     * 项目编号
     */
    @ApiModelProperty(value = "项目编号", allowableValues = "", required = true, position = 3)
    @Size(max = 128, message = "项目编号{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "项目编号{required}")
    private String itemId;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", allowableValues = "", required = true, position = 4)
    @Size(max = 256, message = "项目名称{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "项目名称{required}")
    private String itemName;

    /**
     * 检查机构比例
     */
    @ApiModelProperty(value = "检查机构比例", allowableValues = "", required = true, position = 5)
    @NotNull(message = "检查机构比例{required}")
    private Double checkRatio;

    /**
     * 预约机构比例
     */
    @ApiModelProperty(value = "预约机构比例", allowableValues = "", required = true, position = 6)
    @NotNull(message = "预约机构比例{required}")
    private Double applyRatio;

    /**
     * 平台比例
     */
    @ApiModelProperty(value = "平台比例", allowableValues = "", required = true, position = 7)
    @NotNull(message = "平台比例{required}")
    private Double platformRatio;

}