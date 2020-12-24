package com.markyang.framework.app.system.service;

import com.markyang.framework.app.system.form.search.MenuSearchForm;
import com.markyang.framework.service.core.service.SearchableService;
import com.markyang.framework.pojo.dto.system.MenuTreeDto;
import com.markyang.framework.pojo.entity.system.Menu;

import java.util.List;

/**
 * 菜单管理(Menu)服务类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
public interface MenuService extends SearchableService<Menu, MenuSearchForm> {

    /**
     * 根据应用编号及用户编号获取授权菜单
     *
     * @param appId  应用编号
     * @param userId 用户编号
     * @return 返回用户授权菜单
     */
    List<MenuTreeDto> getAuthorizedMenusByUserId(String appId, String userId);

    /**
     * 获取树形菜单
     * @param appId 应用ID
     * @return 菜单列表
     */
    List<Menu> getTreeMenus(String appId);

}