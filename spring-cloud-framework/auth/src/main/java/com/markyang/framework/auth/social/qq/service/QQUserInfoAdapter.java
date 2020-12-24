package com.markyang.framework.auth.social.qq.service;

import com.markyang.framework.auth.social.qq.support.QQUserInfo;
import org.springframework.social.ApiException;
import org.springframework.social.connect.*;

/**
 * QQ用户信息适配
 * @author yangchangliang
 */
public class QQUserInfoAdapter implements ApiAdapter<QQService> {
    /**
     * Implements {@link Connection#test()} for connections to the given API.
     *
     * @param api the API binding
     * @return true if the API is functional, false if not
     */
    @Override
    public boolean test(QQService api) {
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
    public void setConnectionValues(QQService api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUserInfo();
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurlQq_1());
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
    public UserProfile fetchUserProfile(QQService api) {
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
    public void updateStatus(QQService api, String message) {
    }
}
