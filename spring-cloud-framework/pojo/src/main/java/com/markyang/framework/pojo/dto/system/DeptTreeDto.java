package com.markyang.framework.pojo.dto.system;

import com.markyang.framework.pojo.common.support.TreeNode;
import com.markyang.framework.pojo.entity.support.DtoDictField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 部门属性接口
 *
 * @author yangchangliang
 * @version 1.0.0
 * @date 2020/3/28 14:37 星期六
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class DeptTreeDto implements TreeNode, Serializable {

    private static final long serialVersionUID = 5943666353775324861L;

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
     * 名称
     */
    @ApiModelProperty(value = "名称,varchar(128)", required = true, position = 6)
    private String name;

    /**
     * 名称
     */
    @ApiModelProperty(value = "类型,varchar(128)", required = true, position = 6)
    @DtoDictField(tableName = "sys_dept")
    private String type;


    /**
     * 孩子结点
     */
    @ApiModelProperty(value = "子机构,list", position = 15)
    private List<DeptTreeDto> children;

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
        this.children = (List<DeptTreeDto>) children;
    }
}
