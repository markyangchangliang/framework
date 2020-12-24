package com.markyang.framework.auth.social.qq.service;

import com.markyang.framework.auth.social.qq.support.QQUserInfo;

/**
 * QQ服务类
 * @author yangchangliang
 */
public interface QQService {
    /**
     * 获取用户信息
     * @return 用户信息
     */
    QQUserInfo getUserInfo();
}
