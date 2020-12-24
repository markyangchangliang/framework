package com.markyang.framework.auth.social.wx.service;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * 微信服务链接工厂
 * @author yangchangliang
 */
public class WxServiceConnectionFactory extends OAuth2ConnectionFactory<WxService> {

    /**
     * Create a {@link OAuth2ConnectionFactory}.
     *
     * @param providerId      the provider id e.g. "facebook"
     * @param appId 客户端ID
     * @param appSecret 客户端密匙
     */
    public WxServiceConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new WxServiceProvider(appId, appSecret), new WxUserInfoAdapter());
    }

    /**
     * 由于微信的openId是和accessToken一起返回的，所以在这里直接根据accessToken设置providerUserId即可，不用像QQ那样通过QQAdapter来获取
     */
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if(accessGrant instanceof WxAccessGrant) {
            return ((WxAccessGrant)accessGrant).getOpenId();
        }
        return null;
    }

    @Override
    public Connection<WxService> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
            accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter(extractProviderUserId(accessGrant)));
    }

    @Override
    public Connection<WxService> createConnection(ConnectionData data) {
        return new OAuth2Connection<>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));
    }

    private ApiAdapter<WxService> getApiAdapter(String providerUserId) {
        return new WxUserInfoAdapter(providerUserId);
    }

    private OAuth2ServiceProvider<WxService> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<WxService>) getServiceProvider();
    }

}
