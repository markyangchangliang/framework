package com.markyang.framework.auth.social.qq.service;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * QQ服务提供注入
 * @author yangchangliang
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQService> {

    private static final String AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize";
    private static final String ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token";

    private String appId;

    /**
     * Create a new {@link OAuth2ServiceProvider}.
     * @param appId 客户端ID
     * @param appSecret 客户端密匙
     */
    public QQServiceProvider(String appId, String appSecret) {
        super(new QQServiceOAuth2Template(appId, appSecret, AUTHORIZE_URL, ACCESS_TOKEN_URL));
        this.appId = appId;
    }

    @Override
    public QQService getApi(String accessToken) {
        return new QQServiceImpl(accessToken, this.appId);
    }

}
