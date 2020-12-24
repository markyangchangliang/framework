package com.markyang.framework.app.system.wx.builder;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 图片builder
 * @author yangchangliang
 */
public class ImageBuilder extends AbstractBuilder {

  @Override
  public WxMpXmlOutMessage build(String content, WxMpXmlMessage wxMessage,
                                 WxMpService service) {

    return WxMpXmlOutMessage.IMAGE().mediaId(content)
        .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
        .build();
  }

}
