package com.markyang.framework.pojo.dto.system;

import com.markyang.framework.pojo.common.support.TreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 菜单数据传输对象
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/27 3:41 下午 星期五
 */
@Data
public class MenuTreeDto implements TreeNode {

    private static final long serialVersionUID = 4535982425296265587L;
    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单ID，bigint(20)", position = 1)
    private String id;

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
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称,varchar(64)", position = 4)
    private String name;
    /**
     * 菜单URI
     */
    @ApiModelProperty(value = "菜单URI,varchar(128)", position = 6)
    private String uri;
    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    @ApiModelProperty(value = "类型   0：目录   1：菜单   2：按钮,char(1)", position = 8)
    private String type;
    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标,varchar(64)", position = 9)
    private String icon;
    /**
     * 打开目标
     */
    @ApiModelProperty(value = "打开目标,varchar(32)", position = 11)
    private String target;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序,varchar(32)", position = 12)
    private String seq;

    /**
     * 子菜单
     */
    @ApiModelProperty(value = "子菜单,char(1)", position = 14)
    private List<MenuTreeDto> children;

    /**
     * 获取ID
     *
     * @return ID
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * 获取父级ID
     *
     * @return ID
     */
    @Override
    public String getParentId() {
        return this.parentId;
    }

    /**
     * 设置子节点
     *
     * @param children 孩子节点
     */
    @Override
    @SuppressWarnings("unchecked")
    public void setChildren(List<? extends TreeNode> children) {
        this.children = (List<MenuTreeDto>) children;
    }
}
