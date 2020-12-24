package com.markyang.framework.app.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.markyang.framework.app.system.form.search.MenuSearchForm;
import com.markyang.framework.app.system.service.MenuService;
import com.markyang.framework.app.system.enumeration.MenuEnum;
import com.markyang.framework.app.system.repository.MenuRepository;
import com.markyang.framework.pojo.dto.system.MenuTreeDto;
import com.markyang.framework.pojo.entity.system.Menu;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import com.markyang.framework.util.BeanOperationUtils;
import com.markyang.framework.util.BuilderUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单管理(Menu)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
@Service
@AllArgsConstructor
public class MenuServiceImpl extends AbstractSearchableServiceImpl<Menu, MenuRepository, MenuSearchForm> implements MenuService {

    private final MenuRepository repository;


    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public Menu create() {
        return Menu.builder().build();
    }

    /**
     * 根据应用编号及用户编号获取授权菜单
     *
     * @param appId  应用编号
     * @param userId 用户编号
     * @return 返回用户授权菜单
     */
    @Override
    public List<MenuTreeDto> getAuthorizedMenusByUserId(String appId, String userId) {
        return BuilderUtils
                .newTreeBuilder(
                        this.repository.getUserAuthorizedMenus(appId, userId)
                                .parallelStream()
                                .map(menu -> BeanOperationUtils.fromBean(MenuTreeDto.class, menu))
                                .collect(Collectors.toList())
                )
                .topLevelId("0")
                .build();
    }

    /**
     * 获取树形菜单
     *
     * @param appId 应用ID
     * @return 菜单列表
     */
    @Override
    public List<Menu> getTreeMenus(String appId) {
        List<Menu> searchedList = this.list(Wrappers.<Menu>lambdaQuery()
            .eq(Menu::getAppId, appId).eq(Menu::getStatus, MenuEnum.STATUS_ENABLED.getValue())
            .orderByAsc(Menu::getSeq));
        return BuilderUtils.newTreeBuilder(searchedList)
                .topLevelId("0")
                .build();
    }
}