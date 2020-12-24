package com.markyang.framework.app.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.markyang.framework.app.system.form.search.AppSearchForm;
import com.markyang.framework.app.system.service.AppService;
import com.markyang.framework.app.system.repository.AppRepository;
import com.markyang.framework.app.system.repository.RoleRepository;
import com.markyang.framework.pojo.common.support.ItemEntry;
import com.markyang.framework.pojo.dto.system.RoleDto;
import com.markyang.framework.pojo.dto.system.RoleTreeDto;
import com.markyang.framework.pojo.entity.system.App;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import com.markyang.framework.util.AuthUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 应用管理(App)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
@Service
@AllArgsConstructor
public class AppServiceImpl extends AbstractSearchableServiceImpl<App, AppRepository, AppSearchForm> implements AppService {

    private final RoleRepository roleRepository;

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public App create() {
        return App.builder().build();
    }

    /**
     * 获取全部应用
     *
     * @return 结果对象
     */
    @Override
    public List<ItemEntry> getAll() {
        List<App> apps = this.list(Wrappers.<App>lambdaQuery().select(App::getId, App::getName));
        return apps.parallelStream()
            .map(app -> ItemEntry.of(app.getId(), app.getName()))
            .collect(Collectors.toList());
    }

    /**
     * 获取角色树
     *
     * @return List<RoleTreeDto>
     */
    @Override
    public List<RoleTreeDto> getAllApps() {
        List<App> apps = this.getList();
        List<RoleTreeDto> roleTreeDtoList = new ArrayList<>();
        for (App app : apps) {
            List<RoleDto> roles;
            if (AuthUtils.isSuper(AuthUtils.getLoggedUserId())) {
                roles = this.roleRepository.getAppRoles(app.getId());
            } else {
                roles = this.roleRepository.getAppUserRoles(app.getId(), AuthUtils.getLoggedUserId());
            }
            RoleTreeDto roleTreeDto = RoleTreeDto.builder()
                .appId(app.getId()).appName(app.getName()).roles(roles)
                .build();
            roleTreeDtoList.add(roleTreeDto);
        }
        return roleTreeDtoList;
    }
}