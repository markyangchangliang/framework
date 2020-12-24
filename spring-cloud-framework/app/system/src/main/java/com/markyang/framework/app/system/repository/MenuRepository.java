package com.markyang.framework.app.system.repository;

import com.markyang.framework.pojo.entity.system.Menu;
import com.markyang.framework.service.core.repository.FrameworkRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单管理(Menu)仓库类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:16:18
 */
public interface MenuRepository extends FrameworkRepository<Menu> {

    /**
     * 根据应用编号及用户编号获取授权菜单
     *
     * @param appId  应用编号
     * @param userId 用户编号
     * @return 返回用户授权菜单
     */
    List<Menu> getUserAuthorizedMenus(@Param("appId") String appId, @Param("userId") String userId);

}