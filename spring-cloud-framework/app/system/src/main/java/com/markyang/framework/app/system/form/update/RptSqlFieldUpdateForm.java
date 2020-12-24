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
 * 报表转义字段(RptSqlField)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:30:07
 */
@ApiModel(value = "报表转义字段数据表单", description = "报表转义字段表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class RptSqlFieldUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 表编号
     */
    @ApiModelProperty(value = "表编号", allowableValues = "", required = true, position = 1)
    @NotNull(message = "表编号{required}")
    private String rptSqlId;

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
     * 转义类型
     */
    @ApiModelProperty(value = "转义类型", allowableValues = "", required = true, position = 4)
    @Size(max = 32, message = "转义类型{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "转义类型{required}")
    private String transType;

    /**
     * 表名
     */
    @ApiModelProperty(value = "表名", allowableValues = "", position = 5)
    @Size(max = 64, message = "表名{size}")
    private String tableName;

    /**
     * 字典名
     */
    @ApiModelProperty(value = "字典名", allowableValues = "", position = 6)
    @Size(max = 64, message = "字典名{size}")
    private String fieldName;

}