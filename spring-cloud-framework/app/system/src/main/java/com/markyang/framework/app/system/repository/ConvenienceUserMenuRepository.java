package com.markyang.framework.app.system.repository;

import com.markyang.framework.pojo.dto.system.ConvenienceUserMenuDto;
import com.markyang.framework.pojo.entity.system.ConvenienceUserMenu;
import com.markyang.framework.service.core.repository.FrameworkRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户便捷菜单(ConvenienceUserMenu)仓库类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-05-08 17:37:53
 */
public interface ConvenienceUserMenuRepository extends FrameworkRepository<ConvenienceUserMenu> {

    /**
     * 获取用户便携菜单
     * @param userId 用户ID
     * @return 用户便携菜单
     */
    List<ConvenienceUserMenuDto> getConvenienceUserMenus(@Param("userId") String userId);
}