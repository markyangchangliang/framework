package com.markyang.framework.app.system.wx.mp.service;

import com.markyang.framework.app.system.wx.mp.form.search.MpUserSearchForm;
import com.markyang.framework.pojo.entity.system.wx.MpUser;
import com.markyang.framework.service.core.service.SearchableService;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 微信用户(MpUser)服务类
 *
 * @author makejava
 * @version 1
 * @date 2020-04-16 11:09:22
 */
public interface MpUserService extends SearchableService<MpUser, MpUserSearchForm> {

  /**
   * 同步微信用户信息
   * @return 结果对象
   */
  Boolean synchron() throws WxErrorException;
}