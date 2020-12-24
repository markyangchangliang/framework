package com.markyang.framework.auth.social.wx.service;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * 微信服务提供注入
 * @author yangchangliang
 */
public class WxServiceProvider extends AbstractOAuth2ServiceProvider<WxService> {

    private static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/qrconnect";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    private final String appId;

    /**
     * Create a new {@link OAuth2ServiceProvider}.
     * @param appId 客户端ID
     * @param appSecret 客户端密匙
     */
    public WxServiceProvider(String appId, String appSecret) {
        super(new WxServiceOAuth2Template(appId, appSecret, AUTHORIZE_URL, ACCESS_TOKEN_URL));
        this.appId = appId;
    }

    @Override
    public WxService getApi(String accessToken) {
        return new WxServiceImpl(accessToken, this.appId);
    }

}
