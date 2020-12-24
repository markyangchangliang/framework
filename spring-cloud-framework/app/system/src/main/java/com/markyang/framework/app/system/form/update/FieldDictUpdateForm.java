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
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;


/**
 * 数据字典(FieldDict)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:23:13
 */
@ApiModel(value = "数据字典数据表单", description = "数据字典表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class FieldDictUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 数据库名称
     */
    @ApiModelProperty(value = "数据库名称", allowableValues = "", required = true, position = 1)
    @Size(max = 128, message = "数据库名称{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "数据库名称{required}")
    private String appId;

    /**
     * 表名
     */
    @ApiModelProperty(value = "表名", allowableValues = "", required = true, position = 2)
    @Size(max = 64, message = "表名{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "表名{required}")
    private String tableName;

    /**
     * 字典名
     */
    @ApiModelProperty(value = "字典名", allowableValues = "", required = true, position = 3)
    @Size(max = 64, message = "字典名{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "字典名{required}")
    private String fieldName;

    /**
     * 字段值
     */
    @ApiModelProperty(value = "字段值", allowableValues = "", required = true, position = 4)
    @Size(max = 64, message = "字段值{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "字段值{required}")
    private String fieldValue;

    /**
     * 字段含义
     */
    @ApiModelProperty(value = "字段含义", allowableValues = "", required = true, position = 5)
    @Size(max = 64, message = "字段含义{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "字段含义{required}")
    private String fieldMean;

    /**
     * 类别
     */
    @ApiModelProperty(value = "类别", allowableValues = "", position = 6)
    private String type;

    /**
     * 显示状态
     */
    @ApiModelProperty(value = "显示状态", allowableValues = "", position = 7)
    private String displayStatus;

    /**
     * 启用状态
     */
    @ApiModelProperty(value = "启用状态", allowableValues = "", position = 8)
    private String status;

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号", allowableValues = "", position = 9)
    @PositiveOrZero(message = "序号{positive}")
    private Integer seq;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", allowableValues = "", position = 10)
    @Size(max = 2048, message = "备注{size}")
    private String remark;

}