package com.markyang.framework.auth.social.wx.service;

import com.markyang.framework.auth.social.wx.support.WxUserInfo;
import com.markyang.framework.util.JsonUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 微信服务实现类
 * @author yangchangliang
 */
@Getter
@Slf4j
public class WxServiceImpl extends AbstractOAuth2ApiBinding implements WxService {

    private static final String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?openid=%s";
    private final String appId;

    public WxServiceImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
    }

    /**
     * 默认情况下的StringHttpMessageConverter使用的字符集是ISO，而微信使用的UTF-8，所以替换
     * MessageConverters
     * @return list
     */
    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> converters = super.getMessageConverters();
        converters.remove(0);
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return converters;
    }

    @Override
    public WxUserInfo getUserInfo(String openId) {
        String result = getRestTemplate().getForObject(String.format(USER_INFO_URL, openId), String.class);
        log.info("获取微信用户[{}]信息：{}", openId, result);
        WxUserInfo userInfo = JsonUtils.fromJson(result, WxUserInfo.class);
        log.info("反JSON化后的微信用户信息：{}", userInfo);
        return userInfo;
    }
}
