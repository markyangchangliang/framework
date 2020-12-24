package com.markyang.framework.pojo.dto.system;

import com.markyang.framework.pojo.common.support.TreeNode;
import com.markyang.framework.pojo.entity.support.DtoDictField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 部门职员树形接口
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/30 11:36 星期一
 */
@Data
@NoArgsConstructor
public class DeptWorkerTreeDto implements TreeNode, Serializable {
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
     * 名称
     */
    @ApiModelProperty(value = "序号", required = false, position = 7)
    private Integer seq;

    public DeptWorkerTreeDto(String id, String parentId, String name, String type, Integer seq) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.type = type;
        this.seq = seq;
    }

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
     * 孩子结点
     */
    @ApiModelProperty(value = "子构,list", position = 8)
    private List<DeptWorkerTreeDto> children;

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
        this.children =  (List<DeptWorkerTreeDto>) children;
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
