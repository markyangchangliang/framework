package com.markyang.framework.auth.social.qq.service;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * QQ服务链接工厂
 * @author yangchangliang
 */
public class QQServiceConnectionFactory extends OAuth2ConnectionFactory<QQService> {

    /**
     * Create a {@link OAuth2ConnectionFactory}.
     *
     * @param providerId      the provider id e.g. "facebook"
     * @param appId 客户端ID
     * @param appSecret 客户端密匙
     */
    public QQServiceConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQUserInfoAdapter());
    }

}
