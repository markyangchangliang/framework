package com.markyang.framework.pojo.dto.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 表列表传输对象
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/27
 */
@Data
public class RoleUserDto implements Serializable {

    private static final long serialVersionUID = 4536762286755410817L;
    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号", position = 1)
    private String id;

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称", position = 2)
    private String name;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称", position = 3)
    private String deptName;
    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID", position = 4)
    private String roleId;
}
