package com.markyang.framework.app.system.wx.builder;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 *基类
 * @author yangchangliang
 */
public abstract class AbstractBuilder {
  /**
   *微信build基类
   */
  public abstract WxMpXmlOutMessage build(String content,
                                          WxMpXmlMessage wxMessage, WxMpService service);

}
