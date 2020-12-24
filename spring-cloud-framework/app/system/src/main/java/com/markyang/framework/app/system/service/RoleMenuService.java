package com.markyang.framework.app.system.service;

import com.markyang.framework.app.system.form.search.RoleMenuSearchForm;
import com.markyang.framework.pojo.entity.system.RoleMenu;
import com.markyang.framework.service.core.service.SearchableService;

/**
 * 角色与菜单对应关系(RoleMenu)服务类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-09 09:10:56
 */
public interface RoleMenuService extends SearchableService<RoleMenu, RoleMenuSearchForm> {
}