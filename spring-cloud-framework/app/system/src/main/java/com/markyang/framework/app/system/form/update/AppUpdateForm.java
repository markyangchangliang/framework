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
 * 应用管理(App)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:23:13
 */
@ApiModel(value = "应用管理数据表单", description = "应用管理表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class AppUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", allowableValues = "", required = true, position = 1)
    @Size(max = 128, message = "名称{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "名称{required}")
    private String name;

    /**
     * 数据库名称
     */
    @ApiModelProperty(value = "数据库名称", allowableValues = "", position = 2)
    @Size(max = 128, message = "数据库名称{size}")
    private String dbName;

    /**
     * 类别
     */
    @ApiModelProperty(value = "类别", allowableValues = "", required = true, position = 3)
    @NotNull(message = "类别{required}")
    private String type;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址", allowableValues = "", position = 4)
    @Size(max = 128, message = "地址{size}")
    private String url;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标", allowableValues = "", position = 5)
    @Size(max = 128, message = "图标{size}")
    private String icon;

}