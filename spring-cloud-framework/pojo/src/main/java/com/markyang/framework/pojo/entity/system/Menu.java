package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.common.support.TreeNode;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import com.markyang.framework.pojo.entity.support.DictField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 菜单管理(Menu)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:26:45
 */
@ApiModel(value = "菜单管理实体类", description = "菜单管理实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_menu`")
public class Menu extends AbstractBaseEntity implements TreeNode {

    private static final long serialVersionUID = 454501203934138921L;

    /**
     * 应用编号
     */
    @ApiModelProperty(value = "应用编号,varchar(64)", position = 1)
    private String appId;
    /**
     * 父菜单ID，一级菜单为0
     */
    @ApiModelProperty(value = "父菜单ID，一级菜单为0,bigint(20)", position = 2)
    private String parentId;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题,varchar(128)", position = 3)
    private String title;
    /**
     * 菜单URI
     */
    @ApiModelProperty(value = "菜单URI,varchar(128)", position = 6)
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String uri;
    /**
     * 类型   0：目录   1：菜单   2：权限
     */
    @ApiModelProperty(value = "类型   0：目录   1：菜单   2：权限,char(1)", position = 8)
    @DictField
    private String type;
    /**
     * 类型名称
     */
    @ApiModelProperty(value = "类型名称,varchar", position = 16)
    @TableField(exist = false)
    private String typeName;
    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标,varchar(64)", position = 9)
    private String icon;
    /**
     * 菜单URL
     */
    @ApiModelProperty(value = "权限允许的Ant风格的Uri,varchar(256)", position = 10)
    private String permittedAntUris;
    /**
     * 打开目标
     */
    @ApiModelProperty(value = "打开目标,varchar(32)", position = 11)
    @DictField
    private String target;
    /**
     * 打开目标名称
     */
    @ApiModelProperty(value = "打开目标名称,varchar", position = 14)
    @TableField(exist = false)
    private String targetName;
    /**
     * 排序
     */
    @ApiModelProperty(value = "序号,varchar(32)", position = 12)
    private String seq;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态,char(1)", position = 13)
    @DictField
    private String status;
    /**
     * 状态名称
     */
    @ApiModelProperty(value = "状态名称,varchar", position = 15)
    @TableField(exist = false)
    private String statusName;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<Menu> children;

    /**
     * 设置子节点
     *
     * @param children 孩子节点
     */
    @Override
    @SuppressWarnings("unchecked")
    public void setChildren(List<? extends TreeNode> children) {
        this.children = (List<Menu>) children;
    }

    /**
     * 获取子节点
     *
     * @return 子节点
     */
    @Override
    public List<? extends TreeNode> getChildren() {
        return this.children;
    }
}