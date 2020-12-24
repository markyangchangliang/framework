package com.markyang.framework.auth.social.qq.service;

import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * QQ认证
 * @author yangchangliang
 */
@Slf4j
public class QQServiceOAuth2Template extends OAuth2Template {
    /**
     * Constructs an OAuth2Template for a given set of client credentials.
     * Assumes that the authorization URL is the same as the authentication URL.
     *
     * @param clientId       the client ID
     * @param clientSecret   the client secret
     * @param authorizeUrl   the base URL to redirect to when doing authorization code or implicit grant authorization
     * @param accessTokenUrl the URL at which an authorization code, refresh token, or user credentials may be exchanged for an access token.
     */
    public QQServiceOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String result = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        log.info("获取QQ令牌信息：{}", result);
        Map<String, String> map = Splitter.on("&").withKeyValueSeparator("=").split(result);
        return new AccessGrant(map.get("access_token"), null, map.get("refresh_token"), Long.valueOf(map.get("expires_in")));
    }
}
