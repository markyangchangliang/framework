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
 * 导出数据字段(ExportField)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:30:06
 */
@ApiModel(value = "导出数据字段数据表单", description = "导出数据字段表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class ExportFieldUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 表编号
     */
    @ApiModelProperty(value = "表编号", allowableValues = "", required = true, position = 1)
    @NotNull(message = "表编号{required}")
    private String exportTableId;

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号", allowableValues = "", required = true, position = 2)
    @NotNull(message = "序号{required}")
    private Integer seq;

    /**
     * 字段名称
     */
    @ApiModelProperty(value = "字段名称", allowableValues = "", required = true, position = 3)
    @Size(max = 32, message = "字段名称{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "字段名称{required}")
    private String name;

    /**
     * 字段类型
     */
    @ApiModelProperty(value = "字段类型", allowableValues = "", required = true, position = 4)
    @Size(max = 32, message = "字段类型{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "字段类型{required}")
    private String type;

    /**
     * 字段长度
     */
    @ApiModelProperty(value = "字段长度", allowableValues = "", position = 5)
    private Integer length;

    /**
     * 小数位
     */
    @ApiModelProperty(value = "小数位", allowableValues = "", position = 6)
    private Integer decimalLength;

    /**
     * 默认值
     */
    @ApiModelProperty(value = "默认值", allowableValues = "", position = 7)
    @Size(max = 128, message = "默认值{size}")
    private String defaultValue;

    /**
     * 转义类型
     */
    @ApiModelProperty(value = "转义类型", allowableValues = "", required = true, position = 8)
    @Size(max = 32, message = "转义类型{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "转义类型{required}")
    private String transType;

    /**
     * 表名
     */
    @ApiModelProperty(value = "表名", allowableValues = "", required = true, position = 9)
    @Size(max = 64, message = "表名{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "表名{required}")
    private String tableName;

    /**
     * 字典名
     */
    @ApiModelProperty(value = "字典名", allowableValues = "", required = true, position = 10)
    @Size(max = 64, message = "字典名{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "字典名{required}")
    private String fieldName;

}