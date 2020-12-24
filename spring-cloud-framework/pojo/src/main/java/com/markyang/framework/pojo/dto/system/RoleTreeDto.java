package com.markyang.framework.pojo.dto.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

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
public class RoleTreeDto implements Serializable {

    private static final long serialVersionUID = 4536762286755410817L;

    /**
     * 应用Id
     */
    @ApiModelProperty(value = "应用Id", position = 1)
    private String appId;

    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称", position = 2)
    private String appName;


    /**
     * 角色菜单
     */
    @ApiModelProperty(value = "应用名称", position = 2)
    private List<RoleDto> roles;

    private static RoleTreeDto of(String appId, String appName, List<RoleDto> roles) {
        return new RoleTreeDto(appId, appName, roles);
    }
}
