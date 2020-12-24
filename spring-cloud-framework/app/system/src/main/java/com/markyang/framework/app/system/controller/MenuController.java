package com.markyang.framework.app.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.form.search.MenuSearchForm;
import com.markyang.framework.app.system.form.update.MenuUpdateForm;
import com.markyang.framework.app.system.service.MenuService;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.dto.system.MenuTreeDto;
import com.markyang.framework.pojo.entity.system.Menu;
import com.markyang.framework.pojo.web.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单管理(Menu)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:20:27
 */
@Api(tags = "菜单管理控制器")
@ApiSort(1)
@CacheName("menu")
@RestController
@RequestMapping("/menu")
@AllArgsConstructor
@Slf4j
public class MenuController extends AbstractSystemController<Menu, MenuService, MenuSearchForm, MenuUpdateForm> {

    private final MenuService menuService;


    /**
     * 获取树形菜单
     * @param userId 用户ID
     * @return 结果对象
     */
    @ApiOperationSupport(order = 10, author = "yangchangliang")
    @Cacheable(cacheNames = "#root.targetClass.getAnnotation(T(com.zxrj.framework.app.base.annotation.CacheName)).value()", unless = "!#result.isSuccess()")
    @ApiOperation(value = "获取树形菜单", notes = "根据应用编号获取树形菜单")
    @GetMapping(value = "/tree/{appId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVo<List<Menu>> getTreeMenus(@PathVariable("appId") String userId) {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.menuService.getTreeMenus(userId));
    }

    /**
     * 获取用户授权菜单
     * @param userId 用户ID
     * @param appId 应用ID
     * @return 结果对象
     */
    @ApiOperationSupport(order = 11, author = "dragon")
    @Cacheable(cacheNames = "#root.targetClass.getAnnotation(T(com.zxrj.framework.app.base.annotation.CacheName)).value()", unless = "!#result.isSuccess()")
    @ApiOperation(value = "获取用户菜单", notes = "根据应用编号及用户编号获取授权菜单")
    @GetMapping(value = {"/userMenus/{userId}/{appId}", "/userMenus/{userId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVo<List<MenuTreeDto>> getUserAuthorizedMenus(@PathVariable("userId") String userId, @PathVariable(required = false) String appId) {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.menuService.getAuthorizedMenusByUserId(StringUtils.isBlank(appId) ? FrameworkConstants.REGIONAL_MEDICAL_APP_ID : appId, userId));
    }

}