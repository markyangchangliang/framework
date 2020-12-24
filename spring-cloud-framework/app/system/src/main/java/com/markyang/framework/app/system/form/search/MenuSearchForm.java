package com.markyang.framework.app.system.form.search;

import com.markyang.framework.service.core.search.ConditionEnum;
import com.markyang.framework.service.core.search.SearchCondition;
import com.markyang.framework.service.core.form.SearchBaseForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 菜单管理(Menu)搜索表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:23:13
 */
@ApiModel(value = "菜单管理搜索表单", description = "菜单管理搜索表单类")
@NoArgsConstructor
@SuperBuilder
@Data
public class MenuSearchForm implements SearchBaseForm {
    /**
     * 应用编号
     */
    @ApiModelProperty(value = "应用编号", allowableValues = "", position = 1)
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String appId;
    /**
     * 父菜单ID，一级菜单为0
     */
    @ApiModelProperty(value = "父菜单ID，一级菜单为0", allowableValues = "", position = 2)
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String parentId;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", allowableValues = "", position = 3)
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String title;
    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称", allowableValues = "", position = 4)
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String name;
    /**
     * 菜单URI
     */
    @ApiModelProperty(value = "菜单URI", allowableValues = "", position = 6)
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String uri;
    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    @ApiModelProperty(value = "类型   0：目录   1：菜单   2：按钮", allowableValues = "", position = 8)
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String type;
    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标", allowableValues = "", position = 9)
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String icon;
    /**
     * 菜单URL
     */
    @ApiModelProperty(value = "权限允许的Ant风格的Uri", allowableValues = "", position = 10)
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String permittedAntUris;
    /**
     * 打开目标
     */
    @ApiModelProperty(value = "打开目标：_self:本页，_blank:新页", allowableValues = "", position = 11)
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String target;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", allowableValues = "", position = 12)
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String seq;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态：a.启用，b.停用", allowableValues = "", position = 13)
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String status;

}