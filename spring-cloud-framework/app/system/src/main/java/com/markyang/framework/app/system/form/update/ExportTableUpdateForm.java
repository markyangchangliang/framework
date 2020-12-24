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
 * 导出数据表名(ExportTable)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:30:07
 */
@ApiModel(value = "导出数据表名数据表单", description = "导出数据表名表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class ExportTableUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 类别
     */
    @ApiModelProperty(value = "类别", allowableValues = "", required = true, position = 1)
    @Size(max = 16, message = "类别{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "类别{required}")
    private String type;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", allowableValues = "", required = true, position = 2)
    @Size(max = 256, message = "名称{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "名称{required}")
    private String name;

    /**
     * sql语句
     */
    @ApiModelProperty(value = "sql语句", allowableValues = "", position = 3)
    private String querySql;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", allowableValues = "", position = 4)
    @Size(max = 512, message = "备注{size}")
    private String remark;

}