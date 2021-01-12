package com.markyang.framework.app.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markyang.framework.app.system.form.search.UserSearchForm;
import com.markyang.framework.pojo.auth.AuthenticatedUser;
import com.markyang.framework.pojo.dto.system.DeptUserDto;
import com.markyang.framework.pojo.dto.system.OrgUserDto;
import com.markyang.framework.pojo.dto.system.RoleDto;
import com.markyang.framework.pojo.dto.system.UserProfileDto;
import com.markyang.framework.pojo.entity.system.User;
import com.markyang.framework.pojo.web.PageVo;
import com.markyang.framework.service.core.service.SearchableService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 用户服务类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/23 9:38 下午 星期一
 */
public interface UserService extends SearchableService<User, UserSearchForm> {

    /**
     * 根据用户名加载用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    Optional<AuthenticatedUser> getByUsername(String username);

    /**
     * 根据手机号加载用户
     *
     * @param mobilePhone 手机号
     * @return 用户对象
     */
    Optional<AuthenticatedUser> getByMobilePhone(String mobilePhone);

    /**
     * 根据用户ID加载用户
     *
     * @param userId 用户ID
     * @return 用户对象
     */
    Optional<AuthenticatedUser> getByUserId(String userId);


    /**
     * 获取用户信息
     *
     * @param workerId 职员编号
     * @return 用户信息
     */
    Optional<User> getByWorkerId(String workerId);

    /**
     * 启用、停用用户
     *
     * @param id        用户编号
     * @param operation 操作
     */
    void updateUserStatus(String id, String operation);


    /**
     * 设置用户角色
     * @param id      用户id
     * @param roleIds 角色id列表
     */
    void setUserRoles(String id, String[] roleIds);


    /**
     * 获取用户权限信息
     *
     * @param userId 用户id
     * @return List<UserRole>
     */
    List<RoleDto> getUserRoles(String userId);

    /**
     * 重置密码
     *
     * @param userId 职员编号
     */
    void resetPassword(String userId);

    /**
     * 修改密码
     *
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     */
    void changePassword(String oldPassword, String newPassword);

    /**
     * 获取当前部门下的用户信息
     *
     * @param deptId   部门编码
     * @param page 分页
     * @return 返回List<DeptUserDto>
     */
    PageVo<DeptUserDto> getDeptUsers(String deptId, Page<DeptUserDto> page);

    /**
     * 获取机构所有的用户
     * @param orgId 机构ID
     * @return 用户列表
     */
    List<OrgUserDto> getOrgUsers(String orgId);

    /**
     * 获取用户基本信息
     * @return 基本信息
     */
    UserProfileDto getUserProfile();
}
