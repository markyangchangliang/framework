package com.markyang.framework.auth.social.core;

/**
 * 社区属性实体
 * @author yangchangliang
 */
public class SocialAppProperties {

    private String appId;
    private String appSecret;
    public SocialAppProperties() {
    }
    public String getAppId() {
        return this.appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getAppSecret() {
        return this.appSecret;
    }
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
