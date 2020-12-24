package com.markyang.framework.auth.social.wx.service;

import com.markyang.framework.auth.social.wx.support.WxUserInfo;
import org.springframework.social.ApiException;
import org.springframework.social.connect.*;

/**
 * 微信用户信息适配器
 * @author yangchangliang
 */
public class WxUserInfoAdapter implements ApiAdapter<WxService> {
    private String openId;

    public WxUserInfoAdapter() {
    }

    public WxUserInfoAdapter(String openId) {
        this.openId = openId;
    }

    /**
     * Implements {@link Connection#test()} for connections to the given API.
     *
     * @param api the API binding
     * @return true if the API is functional, false if not
     */
    @Override
    public boolean test(WxService api) {
        return true;
    }

    /**
     * Sets values for {@link ConnectionKey#getProviderUserId()}, {@link Connection#getDisplayName()},
     * {@link Connection#getProfileUrl()}, and {@link Connection#getImageUrl()} for connections to the given API.
     *
     * @param api    the API binding
     * @param values the connection values to set
     * @throws ApiException if there is a problem fetching connection information from the provider.
     */
    @Override
    public void setConnectionValues(WxService api, ConnectionValues values) {
        WxUserInfo userInfo = api.getUserInfo(this.openId);
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getHeadimgurl());
        values.setProfileUrl(null);
        values.setProviderUserId(userInfo.getOpenId());
    }

    /**
     * Implements {@link Connection#fetchUserProfile()} for connections to the given API.
     * Should never return null.
     * If the provider's API does not expose user profile data, this method should return {@link UserProfile#EMPTY}.
     *
     * @param api the API binding
     * @return the service provider user profile
     * @throws ApiException if there is a problem fetching a user profile from the provider.
     * @see UserProfileBuilder
     */
    @Override
    public UserProfile fetchUserProfile(WxService api) {
        return null;
    }

    /**
     * Implements {@link Connection#updateStatus(String)} for connections to the given API.
     * If the provider does not have a status concept calling this method should have no effect.
     *
     * @param api     the API binding
     * @param message the status message
     * @throws ApiException if there is a problem updating the user's status on the provider.
     */
    @Override
    public void updateStatus(WxService api, String message) {
    }
}
