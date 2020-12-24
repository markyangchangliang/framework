package com.markyang.framework.app.system.wx.handler;

import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基类
 * @author yangchangliang
 */
public abstract class AbstractHandler implements WxMpMessageHandler {

  protected Logger logger = LoggerFactory.getLogger(getClass());

}
