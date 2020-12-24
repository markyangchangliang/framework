package com.markyang.framework.app.system.service.impl;

import com.markyang.framework.app.system.form.search.ConvenienceUserMenuSearchForm;
import com.markyang.framework.app.system.service.ConvenienceUserMenuService;
import com.markyang.framework.app.system.repository.ConvenienceUserMenuRepository;
import com.markyang.framework.pojo.entity.system.ConvenienceUserMenu;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import org.springframework.stereotype.Service;

 /**
 * 用户便捷菜单(ConvenienceUserMenu)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-05-08 17:36:38
 */
@Service
public class ConvenienceUserMenuServiceImpl extends AbstractSearchableServiceImpl<ConvenienceUserMenu, ConvenienceUserMenuRepository, ConvenienceUserMenuSearchForm> implements ConvenienceUserMenuService {


    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public ConvenienceUserMenu create() {
        return ConvenienceUserMenu.builder().build();
    }
}