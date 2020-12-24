package com.markyang.framework.app.system.wx.mp.service;


import com.markyang.framework.app.system.wx.mp.form.search.MpMessageSearchForm;
import com.markyang.framework.pojo.entity.system.wx.MpMessage;
import com.markyang.framework.service.core.service.SearchableService;

/**
 * 微信消息(MpMessage)服务类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:09:22
 */
public interface MpMessageService extends SearchableService<MpMessage, MpMessageSearchForm> {

  /**
   * 发送消息
   * @param message 消息体
   * @return boolean
   */
  boolean sendMessage(MpMessage message) throws  Exception;
}