package com.markyang.framework.app.system.wx.mp.service;

import com.markyang.framework.app.system.wx.mp.form.search.MpMenuSearchForm;
import com.markyang.framework.pojo.dto.wx.MenuDto;
import com.markyang.framework.pojo.entity.system.wx.MpMenu;
import com.markyang.framework.service.core.service.SearchableService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;

/**
 * 微信自定义菜单(MpMenu)服务类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:09:22
 */
public interface MpMenuService extends SearchableService<MpMenu, MpMenuSearchForm> {

    /**
     * 删除菜单
     *
     * @return
     */
    boolean delWxMenuValue();

    /**
     * 获取树形菜单
     *
     * @return 菜单列表
     */
    WxMpMenu getWxMenuValue() throws WxErrorException;

    /**
     * 保存并发布菜单
     *
     * @param menus 菜单
     * @return 成功与否
     */
    boolean saveAndRelease(MenuDto menus) throws WxErrorException;

}