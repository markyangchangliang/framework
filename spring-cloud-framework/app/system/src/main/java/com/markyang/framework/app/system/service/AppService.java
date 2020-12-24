package com.markyang.framework.app.system.service;

import com.markyang.framework.app.system.form.search.AppSearchForm;
import com.markyang.framework.pojo.common.support.ItemEntry;
import com.markyang.framework.pojo.dto.system.RoleTreeDto;
import com.markyang.framework.pojo.entity.system.App;
import com.markyang.framework.service.core.service.SearchableService;

import java.util.List;

/**
 * 应用管理(App)服务类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
public interface AppService extends SearchableService<App, AppSearchForm> {
    /**
     * 获取全部应用
     * @return 结果对象
     * */
    List<ItemEntry> getAll();

    /**
     * 获取角色树
     *
     * @return List<App>
     */
    List<RoleTreeDto> getAllApps();
}