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
 * 菜单管理(Menu)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:30:07
 */
@ApiModel(value = "菜单管理数据表单", description = "菜单管理表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MenuUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 应用编号
     */
    @ApiModelProperty(value = "应用编号", allowableValues = "", position = 1)
    @Size(max = 64, message = "应用编号{size}")
    @NotBlank(groups = {FrameworkGroups.Add.class, FrameworkGroups.Update.class}, message = "应用编号{required}")
    private String appId;

    /**
     * 父菜单ID，一级菜单为0
     */
    @ApiModelProperty(value = "父菜单ID，一级菜单为0", allowableValues = "", position = 2)
    @NotNull(groups = {FrameworkGroups.Add.class}, message = "父菜单ID{required}")
    private String parentId;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", allowableValues = "", position = 3)
    @Size(max = 128, message = "标题{size}")
    @NotBlank(groups = {FrameworkGroups.Add.class, FrameworkGroups.Update.class}, message = "标题{required}")
    private String title;

    /**
     * 菜单URI
     */
    @ApiModelProperty(value = "菜单URI", allowableValues = "", position = 6)
    @Size(max = 128, message = "菜单URI{size}")
    private String uri;

    /**
     * 类型   0：目录   1：菜单   2：权限
     */
    @ApiModelProperty(value = "类型   0：目录   1：菜单   2：权限", allowableValues = "", position = 8)
    @Size(max = 1, message = "类型{size}")
    @NotBlank(groups = {FrameworkGroups.Add.class, FrameworkGroups.Update.class}, message = "类型{required}")
    private String type;

    /**
     * 权限允许的Ant风格的Uri
     */
    @ApiModelProperty(value = "权限允许的Ant风格的Uri,varchar(256)", position = 7)
    @Size(max = 256, message = "权限允许的Ant风格的Uri{size}")
    private String permittedAntUris;

    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标", allowableValues = "", position = 9)
    @Size(max = 64, message = "菜单图标{size}")
    private String icon;

    /**
     * 打开目标
     */
    @ApiModelProperty(value = "打开目标", allowableValues = "", position = 11)
    @Size(max = 32, message = "打开目标{size}")
    @NotBlank(groups = {FrameworkGroups.Add.class, FrameworkGroups.Update.class}, message = "打开目标{required}")
    private String target;

    /**
     * 排序
     */
    @ApiModelProperty(value = "序号", allowableValues = "", position = 12)
    @Size(max = 32, message = "序号{size}")
    @NotBlank(groups = {FrameworkGroups.Add.class, FrameworkGroups.Update.class}, message = "序号{required}")
    private String seq;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", allowableValues = "", position = 13)
    @NotBlank(groups = {FrameworkGroups.Add.class, FrameworkGroups.Update.class}, message = "状态{required}")
    private String status;

}