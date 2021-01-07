package com.markyang.framework.app.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.form.search.UserSearchForm;
import com.markyang.framework.app.system.form.update.UserPasswordChangeUpdateForm;
import com.markyang.framework.app.system.form.update.UserUpdateForm;
import com.markyang.framework.app.system.service.AppService;
import com.markyang.framework.app.system.service.UserService;
import com.markyang.framework.pojo.auth.AuthenticatedUser;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.constant.TableConstants;
import com.markyang.framework.pojo.dto.system.*;
import com.markyang.framework.pojo.entity.system.User;
import com.markyang.framework.pojo.web.PageVo;
import com.markyang.framework.pojo.web.ResultVo;
import com.markyang.framework.service.core.validator.core.FormValidate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统用户(User)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:20:27
 */
@Api(tags = "系统用户控制器")
@ApiSort(1)
@CacheName("user")
@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController extends AbstractSystemController<User, UserService, UserSearchForm, UserUpdateForm> {

    private final UserService userService;
    private final AppService appService;


    /**
     * 根据用户名加载用户信息
     *
     * @param username 用户名
     * @return 结果对象
     */
    @ApiOperationSupport(order = 10, author = "yangchangliang")
    @ApiOperation(value = "根据用户名加载用户信息", notes = "根据用户名加载用户信息，用于登录")
    @GetMapping("/username/{username}")
    public ResultVo<AuthenticatedUser> getByUsername(@PathVariable String username) {
        return this.userService.getByUsername(username)
                .map(authenticatedUser -> ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, authenticatedUser))
                .orElse(ResultVo.error("用户名不存在"));
    }

    /**
     * 根据手机号加载用户信息
     *
     * @param phone 手机号
     * @return 结果对象
     */
    @ApiOperationSupport(order = 11, author = "yangchangliang")
    @ApiOperation(value = "根据手机号加载用户信息", notes = "根据手机号加载用户信息，用于登录")
    @GetMapping("/phone/{phone}")
    public ResultVo<AuthenticatedUser> getByPhone(@PathVariable String phone) {
        return this.userService.getByMobilePhone(phone)
                .map(authenticatedUser -> ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, authenticatedUser))
                .orElse(ResultVo.error("手机号不存在"));
    }

    /**
     * 根据用户ID加载用户信息
     *
     * @param userId 用户ID
     * @return 结果对象
     */
    @ApiOperationSupport(order = 12, author = "yangchangliang")
    @ApiOperation(value = "根据用户ID加载用户信息", notes = "根据用户ID加载用户信息，用于登录")
    @GetMapping("/userId/{userId}")
    public ResultVo<AuthenticatedUser> getByUserId(@PathVariable String userId) {
        return this.userService.getByUserId(userId)
                .map(authenticatedUser -> ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, authenticatedUser))
                .orElse(ResultVo.error("用户不存在"));
    }

    /**
     * 获取机构用户
     *
     * @param orgId 机构ID
     * @return 结果对象
     */
    @ApiOperationSupport(order = 13, author = "yangchangliang")
    @ApiOperation(value = "根据机构ID获取机构所有的用户", notes = "获取机构所有的用户")
    @GetMapping("/orgUsers/{orgId}")
    public ResultVo<List<OrgUserDto>> getOrgUsers(@PathVariable String orgId) {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.userService.getOrgUsers(orgId));
    }

    /**
     * 启用、停用用户
     *
     * @param id        用户id
     * @param operation 操作
     * @return 结果
     */
    @ApiOperationSupport(order = 14)
    @ApiOperation(value = "启用、停用用户", notes = "根据用户ID启用、停用用户")
    @PutMapping("/userStatus/{id}/{operation}")
    public ResultVo<Boolean> getByUserId(@PathVariable String id, @PathVariable String operation) {
        this.userService.updateUserStatus(id, operation);
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP);
    }

    /**
     * 设置用户角色信息
     *
     * @param id      用户id
     * @param roleIds 角色编号
     * @return 结果
     */
    @ApiOperationSupport(order = 15)
    @ApiOperation(value = "设置用户角色信息", notes = "设置用户角色信息")
    @PutMapping("/userRole/{id}")
    public ResultVo<Boolean> getByUserId(@PathVariable String id,
                                         @RequestBody(required = false) String[] roleIds) {
        this.userService.setUserRoles(id, roleIds);
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP);
    }

    /**
     * 获取用户权限信息
     */
    @ApiOperationSupport(order = 16)
    @ApiOperation(value = "获取用户权限信息")
    @GetMapping(value = "/userRoles/{userId}")
    public ResultVo<List<RoleDto>> getUserRoles(@PathVariable String userId) {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.userService.getUserRoles(userId));
    }

    /**
     * 重置密码
     *
     * @param userId 职员编号
     * @return 结果
     */
    @ApiOperationSupport(order = 17)
    @ApiOperation(value = "重置密码")
    @PostMapping(value = "/resetPassword/{userId}", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> resetPassword(@PathVariable String userId) {
        this.userService.resetPassword(userId);
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP);
    }

    /**
     * 修改密码
     *
     * @param form 表单对象
     * @return 结果
     */
    @ApiOperationSupport(order = 18)
    @ApiOperation(value = "修改密码")
    @FormValidate
    @PutMapping(value = "/password")
    public ResultVo<?> changePassword(@RequestBody UserPasswordChangeUpdateForm form) {
        this.userService.changePassword(form.getOldPassword(), form.getNewPassword());
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP);
    }

    /**
     * 获取角色树
     *
     * @return List<App>
     */
    @ApiOperation(value = "获取角色树")
    @GetMapping("/allApps")
    public ResultVo<List<RoleTreeDto>> getAllApps() {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.appService.getAllApps());
    }

    /**
     * 获取当前部门下的用户信息
     *
     * @param deptId 部门编码
     * @param page   分页
     * @return 返回List<DeptUserDto>
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页参数-页码", defaultValue = "1", paramType = "query", dataTypeClass = Integer.class, example = "1"),
            @ApiImplicitParam(name = "size", value = "分页参数-分页大小", defaultValue = "20", paramType = "query", dataTypeClass = Integer.class, example = "20"),
            @ApiImplicitParam(name = "sort", value = "分页参数-排序", defaultValue = TableConstants.CREATED_DATETIME_FIELD_NAME + ",desc", paramType = "query", dataTypeClass = String.class, example = TableConstants.CREATED_DATETIME_FIELD_NAME + ",desc"),
    })
    @ApiOperation(value = "获取当前部门下的用户信息")
    @GetMapping("/deptUsers/{deptId}")
    public ResultVo<PageVo<DeptUserDto>> getDeptUsers(@PathVariable String deptId, Page<DeptUserDto> page) {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.userService.getDeptUsers(deptId, page));
    }

    /**
     * 获取用户基本信息
     *
     * @return 结果对象
     */
    @ApiOperationSupport(order = 20, author = "yangchangliang")
    @ApiOperation(value = "获取用户基本信息", notes = "获取用户基本信息")
    @GetMapping("/profile")
    public ResultVo<UserProfileDto> getUserProfile() {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.userService.getUserProfile());
    }
}