package com.markyang.framework.app.system.service;

import com.markyang.framework.app.system.form.search.RoleSearchForm;
import com.markyang.framework.pojo.dto.system.RoleDto;
import com.markyang.framework.pojo.dto.system.RoleUserDto;
import com.markyang.framework.pojo.entity.system.Role;
import com.markyang.framework.service.core.service.SearchableService;

import java.util.List;

/**
 * 角色管理(Role)服务类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
public interface RoleService extends SearchableService<Role, RoleSearchForm> {

    /**
     * 获取拥有该角色职员列表
     *
     * @param roleId 角色id
     * @return 用户id，用户姓名和部门
     */
    List<RoleUserDto> getUsersByRole(String roleId);

    /**
     * 删除角色授权用户
     *  @param roleId 角色id
     * @param userId 用户id
     */
    void removeUserRoleInfo(String roleId, String userId);

    /**
     * 获取用户所有的角色
     * @param userId 用户ID
     * @return 角色列表
     */
    List<RoleDto> getUserRoles(String userId);
}