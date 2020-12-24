package com.markyang.framework.auth.social.wx.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.social.oauth2.AccessGrant;

/**
 * 微信访问授予
 * @author yangchangliang
 */
@Getter
@Setter
public class WxAccessGrant extends AccessGrant {

    /**
     * 微信OPENID
     */
    private String openId;

    public WxAccessGrant(String accessToken) {
        super(accessToken);
    }

    public WxAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
        super(accessToken, scope, refreshToken, expiresIn);
    }


}
