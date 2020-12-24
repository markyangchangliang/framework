package com.markyang.framework.app.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheClean;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.base.exception.UpdateDeniedException;
import com.markyang.framework.app.system.form.search.RoleMenuSearchForm;
import com.markyang.framework.app.system.form.search.RoleSearchForm;
import com.markyang.framework.app.system.form.update.RoleUpdateForm;
import com.markyang.framework.app.system.service.AppService;
import com.markyang.framework.app.system.service.RoleMenuService;
import com.markyang.framework.app.system.service.RoleService;
import com.markyang.framework.app.system.repository.RoleMenuRepository;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.dto.system.RoleDto;
import com.markyang.framework.pojo.dto.system.RoleUserDto;
import com.markyang.framework.pojo.entity.system.App;
import com.markyang.framework.pojo.entity.system.Role;
import com.markyang.framework.pojo.entity.system.RoleMenu;
import com.markyang.framework.pojo.web.PageVo;
import com.markyang.framework.pojo.web.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 角色管理(Role)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:20:27
 */
@Api(tags = "角色管理控制器")
@ApiSort(1)
@CacheName("role")
@RestController
@RequestMapping("/role")
@Slf4j
@AllArgsConstructor
public class RoleController extends AbstractSystemController<Role, RoleService, RoleSearchForm, RoleUpdateForm> {

    private final RoleService roleService;
    private final RoleMenuService roleMenuService;
    private final RoleMenuRepository roleMenuRepository;
    private final AppService appService;

    /**
     * 在实体添加之前处理实体属性数据
     *
     * @param entity 实体对象
     * @param form   表单对象
     * @return bool 是否继续拷贝属性
     */
    @CacheClean(cacheNames = "menu")
    @Override
    public Object afterAdd(Role entity, RoleUpdateForm form) {
        if (CollectionUtils.isNotEmpty(form.getMenus())) {
            this.roleMenuRepository.insertBatchSomeColumn(form.getMenus().parallelStream().map(menuId -> RoleMenu.builder().menuId(menuId).roleId(entity.getId()).build()).collect(Collectors.toList()));
        }
        return null;
    }

    /**
     * 在实体创建之前处理实体属性数据
     *
     * @param entity 实体对象
     * @param form   表单对象
     */
    @Override
    public void beforeAdd(Role entity, RoleUpdateForm form) {
        Optional<App> appOptional = this.appService.get(form.getAppId());
        if (!appOptional.isPresent()) {
            throw new UpdateDeniedException("应用[" + form.getAppId() + "]不存在");
        }
        entity.setApp(appOptional.get());
    }

    /**
     * 在实体修改之前处理实体属性数据
     *
     * @param entity 实体对象
     * @param form   表单对象
     * @return bool 是否继续拷贝属性
     */
    @CacheClean(cacheNames = "menu")
    @Override
    public boolean beforeUpdate(Role entity, RoleUpdateForm form) {
        this.beforeAdd(entity, form);
        // 先找出之前的数据，删除后再插入
        if (CollectionUtils.isNotEmpty(form.getMenus())) {
            this.roleMenuService.remove(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, form.getId()));
            this.roleMenuRepository.insertBatchSomeColumn(form.getMenus().parallelStream().map(menuId -> RoleMenu.builder().menuId(menuId).roleId(entity.getId()).build()).collect(Collectors.toList()));
        }
        return true;
    }

    /**
     * 获取拥有该角色职员列表
     *
     * @param roleId 角色id
     * @return 用户id，用户姓名和部门
     */
    @ApiOperation(value = "获取拥有该角色职员列表", notes = "获取拥有该角色职员列表")
    @GetMapping(value = "/roleUsers/{roleId}", produces = "application/json;charset=UTF-8")
    public ResultVo<PageVo<RoleUserDto>> getUsersByRole(@PathVariable String roleId) {
        List<RoleUserDto> users = this.roleService.getUsersByRole(roleId);
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, PageVo.<RoleUserDto>of(1, users.size(), (long) users.size(), users));
    }


    /**
     * 删除角色授权用户
     *
     * @param roleId 角色id
     * @param userId 用户id
     */
    @ApiOperation(value = "删除角色授权用户", notes = "删除角色授权用户")
    @DeleteMapping(value = "/userRoleInfo/{roleId}/{userId}", produces = "application/json;charset=UTF-8")
    public ResultVo<?> removeUserRoleInfo(@PathVariable(name = "roleId") String roleId,
                                          @PathVariable(name = "userId") String userId) {
        this.roleService.removeUserRoleInfo(roleId, userId);
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP);
    }

    /**
     * 获取角色拥有的菜单
     */
    @ApiOperationSupport(order = 16)
    @ApiOperation(value = "获取角色拥有的菜单")
    @GetMapping(value = "/roleMenus/{roleId}")
    public ResultVo<List<RoleMenu>> getRoleMenus(@PathVariable String roleId) {
        RoleMenuSearchForm search = RoleMenuSearchForm.builder().roleId(roleId).build();
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.roleMenuService.getSearchedList(search));
    }

    /**
     * 获取用户角色
     *
     * @param userId 用户ID
     * @return 结果对象
     */
    @ApiOperationSupport(order = 17, author = "yangchangliang")
    @ApiOperation(value = "获取用户角色", notes = "根据用户ID获取用户所有的角色")
    @GetMapping("/userRoles/{userId}")
    public ResultVo<List<RoleDto>> getUserRoles(@PathVariable String userId) {
        // 此接口专用于工作流获取角色信息，请勿改动
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.roleService.getUserRoles(userId));
    }

}