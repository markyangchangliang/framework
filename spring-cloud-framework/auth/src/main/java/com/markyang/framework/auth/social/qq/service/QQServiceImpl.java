package com.markyang.framework.auth.social.qq.service;

import com.markyang.framework.auth.social.qq.support.QQUserInfo;
import com.markyang.framework.util.JsonUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * QQ服务实现类
 * @author yangchangliang
 */
@Getter
@Slf4j
public class QQServiceImpl extends AbstractOAuth2ApiBinding implements QQService {

    private static final String OPENID_URL = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    private static final String USER_INFO_URL = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
    private final String openId;
    private final String appId;

    public QQServiceImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String result = getRestTemplate().getForObject(String.format(OPENID_URL, accessToken), String.class);
        log.info("获取到QQ用户OPENID：{}", result);
        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    @Override
    public QQUserInfo getUserInfo() {
        String result = getRestTemplate().getForObject(String.format(USER_INFO_URL, this.appId, this.openId), String.class);
        log.info("获取QQ用户[{}]信息：{}", this.openId, result);
        QQUserInfo userInfo = JsonUtils.fromUnderscoreJson(result, QQUserInfo.class);
        userInfo.setOpenId(this.openId);
        log.info("反JSON化后的QQ用户信息：{}", userInfo);
        return userInfo;
    }
}
