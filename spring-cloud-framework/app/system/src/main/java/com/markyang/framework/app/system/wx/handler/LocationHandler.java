package com.markyang.framework.app.system.wx.handler;

import com.markyang.framework.app.system.wx.builder.TextBuilder;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * 地理位置处理器
 * @author yangchangliang
 */
@Component
public class LocationHandler extends AbstractHandler {

  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                  Map<String, Object> context, WxMpService wxMpService,
                                  WxSessionManager sessionManager) {
    if (wxMessage.getMsgType().equals(XmlMsgType.LOCATION)) {
      //TODO 接收处理用户发送的地理位置消息
      try {
        String content = "感谢反馈，您的的地理位置已收到！";
        return new TextBuilder().build(content, wxMessage, null);
      } catch (Exception ex) {
        this.logger.error("位置消息接收处理失败", ex);
        return null;
      }
    }
    //上报地理位置事件
    this.logger.info("上报地理位置，纬度 : {}，经度 : {}，精度 : {}",
        wxMessage.getLatitude(), wxMessage.getLongitude(), wxMessage.getPrecision());
    return null;
  }

}
