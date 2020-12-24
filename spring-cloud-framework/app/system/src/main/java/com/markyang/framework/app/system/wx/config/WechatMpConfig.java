package com.markyang.framework.app.system.wx.config;

import com.markyang.framework.app.system.wx.handler.*;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static me.chanjar.weixin.common.api.WxConsts.EventType.SUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.EventType.UNSUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.EVENT;
import static me.chanjar.weixin.mp.constant.WxMpEventConstants.CustomerService.*;
import static me.chanjar.weixin.mp.constant.WxMpEventConstants.POI_CHECK_NOTIFY;

/**
 * 公众号创建bean实例类
 * @author yangchangliang
 */
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(WechatAccountConfig.class)
public class WechatMpConfig {
  private final KfSessionHandler kfSessionHandler;
  private final LocationHandler locationHandler;
  private final LogHandler logHandler;
  private final MenuHandler menuHandler;
  private final MsgHandler msgHandler;
  private final NullHandler nullHandler;
  private final ScanHandler scanHandler;
  private final StoreCheckNotifyHandler storeCheckNotifyHandler;
  private final SubscribeHandler subscribeHandler;
  private final UnsubscribeHandler unsubscribeHandler;
  @Bean
  public WxMpMessageRouter messageRouter(WxMpService wxMpService) {
    final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

    // 记录所有事件的日志 （异步执行）
    newRouter.rule().handler(this.logHandler).next();

    // 接收客服会话管理事件
    newRouter.rule().async(false).msgType(EVENT).event(KF_CREATE_SESSION)
        .handler(this.kfSessionHandler).end();
    newRouter.rule().async(false).msgType(EVENT).event(KF_CLOSE_SESSION)
        .handler(this.kfSessionHandler).end();
    newRouter.rule().async(false).msgType(EVENT).event(KF_SWITCH_SESSION)
        .handler(this.kfSessionHandler).end();

    // 门店审核事件
    newRouter.rule().async(false).msgType(EVENT).event(POI_CHECK_NOTIFY).handler(this.storeCheckNotifyHandler).end();

    // 自定义菜单事件
    newRouter.rule().async(false).msgType(EVENT).event(WxConsts.EventType.CLICK).handler(this.menuHandler).end();

    // 点击菜单连接事件
    newRouter.rule().async(false).msgType(EVENT).event(WxConsts.EventType.VIEW).handler(this.nullHandler).end();

    // 关注事件
    newRouter.rule().async(false).msgType(EVENT).event(SUBSCRIBE).handler(this.subscribeHandler).end();

    // 取消关注事件
    newRouter.rule().async(false).msgType(EVENT).event(UNSUBSCRIBE).handler(this.unsubscribeHandler).end();

    // 上报地理位置事件
    newRouter.rule().async(false).msgType(EVENT).event(WxConsts.EventType.LOCATION).handler(this.locationHandler).end();

    // 接收地理位置消息
    newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.LOCATION).handler(this.locationHandler).end();

    // 扫码事件
    newRouter.rule().async(false).msgType(EVENT).event(WxConsts.EventType.SCAN).handler(this.scanHandler).end();

    // 默认
    newRouter.rule().async(false).handler(this.msgHandler).end();

    return newRouter;
  }

}
