package com.markyang.framework.app.system.service.impl;

import com.markyang.framework.app.system.form.search.RoleMenuSearchForm;
import com.markyang.framework.app.system.service.RoleMenuService;
import com.markyang.framework.app.system.repository.RoleMenuRepository;
import com.markyang.framework.pojo.entity.system.RoleMenu;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import org.springframework.stereotype.Service;

 /**
 * 角色与菜单对应关系(RoleMenu)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-09 09:10:56
 */
@Service
public class RoleMenuServiceImpl extends AbstractSearchableServiceImpl<RoleMenu, RoleMenuRepository, RoleMenuSearchForm> implements RoleMenuService {

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public RoleMenu create() {
        return RoleMenu.builder().build();
    }
}