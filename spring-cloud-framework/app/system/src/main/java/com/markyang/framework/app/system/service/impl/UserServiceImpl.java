package com.markyang.framework.app.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.markyang.framework.app.base.annotation.ApplyPostGetHook;
import com.markyang.framework.app.base.exception.UpdateDeniedException;
import com.markyang.framework.app.system.form.search.UserSearchForm;
import com.markyang.framework.app.system.service.UserService;
import com.markyang.framework.app.system.enumeration.UserEnum;
import com.markyang.framework.app.system.repository.RoleRepository;
import com.markyang.framework.app.system.repository.UserRepository;
import com.markyang.framework.app.system.repository.UserRoleRepository;
import com.markyang.framework.pojo.auth.AuthenticatedUser;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.dto.system.DeptUserDto;
import com.markyang.framework.pojo.dto.system.OrgUserDto;
import com.markyang.framework.pojo.dto.system.RoleDto;
import com.markyang.framework.pojo.dto.system.UserProfileDto;
import com.markyang.framework.pojo.entity.system.User;
import com.markyang.framework.pojo.entity.system.UserRole;
import com.markyang.framework.pojo.web.PageVo;
import com.markyang.framework.service.common.CommonInfoCacheService;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import com.markyang.framework.util.AuthUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 用户服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/23 9:50 下午 星期一
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends AbstractSearchableServiceImpl<User, UserRepository, UserSearchForm> implements UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CommonInfoCacheService commonInfoCacheService;

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public User create() {
        return User.builder().build();
    }

    /**
     * 根据用户名加载用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    @Override
    public Optional<AuthenticatedUser> getByUsername(String username) {
        return getAuthenticatedUser(this.repository.getUserByUsername(username));
    }

    /**
     * 根据手机号加载用户
     *
     * @param mobilePhone 手机号
     * @return 用户对象
     */
    @Override
    public Optional<AuthenticatedUser> getByMobilePhone(String mobilePhone) {
        return getAuthenticatedUser(this.repository.getUserByMobilePhone(mobilePhone));
    }

    /**
     * 根据用户ID加载用户
     *
     * @param userId 用户ID
     * @return 用户对象
     */
    @Override
    public Optional<AuthenticatedUser> getByUserId(String userId) {
        return getAuthenticatedUser(this.repository.getUserByUserId(userId));
    }

    /**
     * 获取并缓存对象
     *
     * @param authenticatedUser 认证用户
     * @return 认证用户
     */
    private Optional<AuthenticatedUser> getAuthenticatedUser(AuthenticatedUser authenticatedUser) {
        if (Objects.isNull(authenticatedUser)) {
            return Optional.empty();
        }
        if (Objects.isNull(authenticatedUser.getPermittedAntUris())) {
            authenticatedUser.setAntUris(Collections.emptySet());
        } else {
            authenticatedUser.setAntUris(Sets.newHashSet(StringUtils.split(authenticatedUser.getPermittedAntUris(), ",")));
        }
        authenticatedUser.setPermittedAntUris(null);
        return Optional.of(authenticatedUser);
    }

    /**
     * 获取用户信息
     *
     * @param workerId 职员编号
     * @return 用户信息
     */
    @Override
    public Optional<User> getByWorkerId(String workerId) {
        return Optional.ofNullable(this.getOne(Wrappers.<User>lambdaQuery().eq(User::getWorkerId, workerId), false));
    }

    /**
     * 启用、停用用户
     *
     * @param id        用户编号
     * @param operation 操作
     */
    @Override
    public void updateUserStatus(String id, String operation) {
        this.get(id).map(user -> {
            String status = StringUtils.equals("enabled", operation) ? UserEnum.STATUS_ENABLED.getValue()
                    : UserEnum.STATUS_DISABLED.getValue();
            user.setStatus(status);

            // TODO 启用或者停用企业微信用户信息

            this.update(user);
            return true;
        }).orElseThrow(() -> new UpdateDeniedException("用户信息不存在!"));
    }

    /**
     * @param id      用户id
     * @param roleIds 角色id
     */
    @Override
    public void setUserRoles(String id, String[] roleIds) {
        this.get(id).map(user -> {
            // 删除用户角色信息
            this.userRoleRepository.delete(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, id));
            // 添加用户角色信息
            if (Objects.nonNull(roleIds)) {
                List<UserRole> userRoleList = Lists.newArrayListWithExpectedSize(roleIds.length);
                for (String roleId : roleIds) {
                    userRoleList.add(UserRole.builder().userId(id).roleId(roleId).build());
                }
                if (CollectionUtils.isNotEmpty(userRoleList)) {
                    this.userRoleRepository.insertBatchSomeColumn(userRoleList);
                }
            }
            return true;
        }).orElseThrow(() -> new UpdateDeniedException("用户信息不存在!"));
    }

    /**
     * 获取用户权限信息
     *
     * @param userId 用户id
     * @return List<UserRole>
     */
    @Override
    public List<RoleDto> getUserRoles(String userId) {
        return this.roleRepository.getUserRoles(userId);
    }


    /**
     * 重置密码
     *
     * @param userId 职员编号
     */
    @Override
    public void resetPassword(String userId) {
        this.get(userId).map(user -> {
            user.setPassword(this.passwordEncoder.encode(FrameworkConstants.USER_DEFAULT_PASSWORD));
            this.update(user);
            return true;
        }).orElseThrow(() -> new UpdateDeniedException("该用户不存在用户表，请联系管理员补全信息!"));
    }

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    @Override
    public void changePassword(String oldPassword, String newPassword) {
        // 先验证旧密码是否正确
        Optional<User> userOptional = this.get(AuthUtils.getLoggedUserId());
        if (!userOptional.isPresent()) {
            throw new UpdateDeniedException("未找到该用户信息！");
        }
        User user = userOptional.get();
        if (this.passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(this.passwordEncoder.encode(newPassword));
            this.update(user);
        } else {
            throw new UpdateDeniedException("旧密码错误！");
        }
    }

    /**
     * 获取当前部门下的用户信息
     *
     * @param deptId   部门编码
     * @param page 分页
     * @return 返回List<DeptUserDto>
     */
    @ApplyPostGetHook
    @Override
    public PageVo<DeptUserDto> getDeptUsers(String deptId, Page<DeptUserDto> page) {
        return PageVo.of(this.repository.getDeptUsers(page, deptId));
    }

    /**
     * 获取机构所有的用户
     *
     * @param orgId 机构ID
     * @return 用户列表
     */
    @Override
    public List<OrgUserDto> getOrgUsers(String orgId) {
        return this.repository.getOrgUsers(orgId);
    }

    /**
     * 添加之后的处理逻辑
     *
     * @param entity 实体对象
     */
    @Override
    public void afterAdd(User entity) {
        this.commonInfoCacheService.updateCachedUserWorkerInfo(entity.getId());
    }

    /**
     * 修改之后的处理逻辑
     *
     * @param entity 实体对象
     */
    @Override
    public void afterUpdate(User entity) {
        this.afterAdd(entity);
    }

    /**
     * 获取用户基本信息
     *
     * @return 基本信息
     */
    @ApplyPostGetHook
    @Override
    public UserProfileDto getUserProfile() {
        return this.repository.getUserProfile(AuthUtils.getLoggedUserId());
    }

    /**
     * 删除之后的处理逻辑
     *
     * @param entity 实体对象
     */
    @Override
    public void afterDelete(User entity) {
        this.commonInfoCacheService.deleteCachedUserWorkerInfo(entity.getId(), null);
    }
}

