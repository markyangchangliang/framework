package com.markyang.framework.app.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.markyang.framework.app.base.exception.UpdateDeniedException;
import com.markyang.framework.app.system.form.search.RoleSearchForm;
import com.markyang.framework.app.system.service.RoleService;
import com.markyang.framework.app.system.repository.RoleMenuRepository;
import com.markyang.framework.app.system.repository.RoleRepository;
import com.markyang.framework.app.system.repository.UserRoleRepository;
import com.markyang.framework.pojo.dto.system.RoleDto;
import com.markyang.framework.pojo.dto.system.RoleUserDto;
import com.markyang.framework.pojo.entity.system.Role;
import com.markyang.framework.pojo.entity.system.RoleMenu;
import com.markyang.framework.pojo.entity.system.UserRole;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import com.markyang.framework.util.AuthUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 角色管理(Role)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl extends AbstractSearchableServiceImpl<Role, RoleRepository, RoleSearchForm> implements RoleService {

    private final RoleRepository repository;
    private final UserRoleRepository userRoleRepository;
    private final RoleMenuRepository roleMenuRepository;
    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public Role create() {
        return Role.builder().build();
    }

    /**
     * 获取拥有该角色职员列表
     *
     * @param roleId 角色id
     * @return 用户id，用户姓名和部门
     */
    @Override
    public List<RoleUserDto> getUsersByRole(String roleId) {
        return this.repository.getUsersByRole(roleId);
    }

    @Override
    public void beforeAdd(Role entity) {
        //已有授权的用户，不能删除该角色
        Boolean exists = this.repository.exists(Wrappers.<Role>lambdaQuery().eq(Role::getName, entity.getName()));
        if (BooleanUtils.isTrue(exists)) {
            throw new UpdateDeniedException("角色名重复!");
        }
    }

    @Override
    public void beforeDelete(Role entity) {
        assert !Objects.isNull(entity.getId());
        //限定无法删除超级管理员
        if (Objects.equals(String.valueOf(entity.getId()), "1")) {
            throw new UpdateDeniedException("无法删除超级管理员！");
        }
        //已有授权的用户，不能删除该角色
        Boolean exists = this.userRoleRepository.exists(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getRoleId, entity.getId()));
        if (BooleanUtils.isTrue(exists)) {
            throw new UpdateDeniedException("已有授权的用户，不能删除该角色！");
        }
    }

    @Override
    public void afterDelete(Role entity) {
        // 删除角色之后删除，删除角色的菜单
        this.roleMenuRepository.delete(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, entity.getId()));
    }

    @Override
    public void removeUserRoleInfo(String roleId, String userId) {
        if (AuthUtils.isSuper(userId)) {
            throw new UpdateDeniedException("无法取消超级管理员权限！");
        }
        if (StringUtils.isAnyBlank(roleId, userId)) {
            throw new UpdateDeniedException("用户名id和角色id不能为空！");
        }
        this.userRoleRepository.delete(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getRoleId, roleId).eq(UserRole::getUserId, userId));
    }

    /**
     * 获取用户所有的角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<RoleDto> getUserRoles(String userId) {
        return this.repository.getUserRoles(userId);
    }
}