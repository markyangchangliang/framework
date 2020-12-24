package com.markyang.framework.app.system.wx.mp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.markyang.framework.app.system.wx.mp.form.search.MpMenuSearchForm;
import com.markyang.framework.app.system.wx.mp.service.MpMenuService;
import com.markyang.framework.app.system.wx.mp.repository.MpMenuRepository;
import com.markyang.framework.pojo.dto.wx.ButtonDto;
import com.markyang.framework.pojo.dto.wx.MenuDto;
import com.markyang.framework.pojo.entity.system.wx.MpMenu;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 微信自定义菜单(MpMenu)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:09:22
 */
@Service
@AllArgsConstructor
public class MpMenuServiceImpl extends AbstractSearchableServiceImpl<MpMenu, MpMenuRepository, MpMenuSearchForm> implements MpMenuService {
  private final WxMpService wxService;

  /**
   * 创建空的或者带有默认值的实体对象
   *
   * @return 实体对象
   */
  @Override
  public MpMenu create() {
    return MpMenu.builder().build();
  }

  /**
   * 保存并创建自定义菜单
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean saveAndRelease(MenuDto strWxMenu) throws WxErrorException {
    List<ButtonDto> buttons = strWxMenu.getButton();
    //先删除
    this.remove(Wrappers.emptyWrapper());
    for (ButtonDto menuButton : buttons) {
      //入库
      MpMenu mpMenu1 = setWxMenuValue(menuButton);
      MpMenu add = this.add(mpMenu1);
      if (CollectionUtils.isEmpty(menuButton.getSub_button())) {
        continue;
      }
      for (ButtonDto menuButton1 : menuButton.getSub_button()) {
        MpMenu menu1 = setWxMenuValue(menuButton1);
        menu1.setParentId(add.getId());
        this.add(menu1);
      }
    }
    //删除微信菜单
    wxService.getMenuService().menuDelete();
    //创建菜单
    wxService.getMenuService().menuCreate(JSONObject.toJSONString(strWxMenu));
    return true;
  }

  //构造菜单实体
  MpMenu setWxMenuValue(ButtonDto menuButton) {
    return MpMenu.builder()
        .type(menuButton.getType())
        .name(menuButton.getName()).url(menuButton.getUrl())
        .replyMediaId(menuButton.getMediaId())
        .maPagePath(menuButton.getPagePath())
        .build();
  }

  /**
   * 删除自定义菜单
   *
   * @return boolean
   */
  @Override
  public boolean delWxMenuValue() {
    try {
      //先删除微信菜单，成功后删除本地记录
      wxService.getMenuService().menuDelete();
      //删除本地记录
      this.remove(Wrappers.emptyWrapper());
      return true;
    } catch (WxErrorException ex) {
      ex.printStackTrace();
      return false;
    }

  }

  /**
   * 获取微信自定义菜单
   */
  @Override
  public WxMpMenu getWxMenuValue() throws WxErrorException {
    return wxService.getMenuService().menuGet();
  }

}
