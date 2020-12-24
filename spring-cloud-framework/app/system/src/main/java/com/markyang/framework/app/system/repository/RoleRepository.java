package com.markyang.framework.app.system.repository;

import com.markyang.framework.pojo.dto.system.RoleDto;
import com.markyang.framework.pojo.dto.system.RoleUserDto;
import com.markyang.framework.pojo.entity.system.Role;
import com.markyang.framework.service.core.repository.FrameworkRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色管理(Role)仓库类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:16:18
 */
public interface RoleRepository extends FrameworkRepository<Role> {

    /**
     * 获取拥有该角色职员列表
     *
     * @param roleId 角色id
     * @return 用户id，用户姓名和部门
     */
    List<RoleUserDto> getUsersByRole(@Param("roleId") String roleId);

    /**
     * 获取用户所有的角色
     * @param userId 用户ID
     * @return 角色列表
     */
    List<RoleDto> getUserRoles(@Param("userId") String userId);

    /**
     * 获取app用户角色
     * @param appId 应用ID
     * @param userId 用户ID
     * @return 角色
     */
    List<RoleDto> getAppUserRoles(@Param("appId") String appId, @Param("userId") String userId);

    /**
     * 获取应用角色
     * @param appId 应用ID
     * @return 角色
     */
    List<RoleDto> getAppRoles(@Param("appId") String appId);
}