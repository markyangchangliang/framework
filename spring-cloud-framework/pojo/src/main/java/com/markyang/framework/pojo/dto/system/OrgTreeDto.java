package com.markyang.framework.pojo.dto.system;

import com.markyang.framework.pojo.common.support.TreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 机构树传输对象
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/28 13:58 星期六
 */
@Data
public class OrgTreeDto implements TreeNode {

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号,bigint(20)", required = true, position = 0)
    private String id;

    /**
     * 父编号
     */
    @ApiModelProperty(value = "父编号,bigint(20)", required = true, position = 1)
    private String parentId;
    /**
     * 类别
     */
    @ApiModelProperty(value = "类别,varchar(16)", required = true, position = 2)
    private String type;
    /**
     * 代码
     */
    @ApiModelProperty(value = "代码,varchar(128)", required = true, position = 3)
    private String code;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称,varchar(128)", required = true, position = 6)
    private String name;
    /**
     * 简称
     */
    @ApiModelProperty(value = "简称,varchar(128)", position = 7)
    private String abbrName;

    /**
     * 孩子结点
     */
    @ApiModelProperty(value = "子构,list", position = 15)
    private List<OrgTreeDto> children;

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
        this.children = (List<OrgTreeDto>) children;
    }
}
