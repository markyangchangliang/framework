package com.markyang.framework.auth.social.wx.service;

import com.markyang.framework.auth.social.wx.support.WxUserInfo;

/**
 * 微信服务类
 * @author yangchangliang
 */
public interface WxService {
    /**
     * 获取用户信息
     * @param openId openId
     * @return 微信用户信息
     */
    WxUserInfo getUserInfo(String openId);
}
