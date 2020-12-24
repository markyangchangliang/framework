package com.markyang.framework.pojo.dto.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 表列表传输对象
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/27
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto implements Serializable {

    private static final long serialVersionUID = 4536762286755410817L;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色Id", position = 1)
    private String roleId;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称", position = 2)
    private String roleName;


    private static RoleDto of(String roleId, String roleName) {
        return new RoleDto(roleId, roleName);
    }
}
