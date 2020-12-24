package com.markyang.framework.auth.social.wx.service;

import com.markyang.framework.auth.social.exception.FailureExchangeAccessTokenException;
import com.markyang.framework.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**‘
 * 微信服务授权
 * @author yangchangliang
 */
@Slf4j
public class WxServiceOAuth2Template extends OAuth2Template {

    private static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";

    private final String clientId;

    private final String clientSecret;

    private final String accessTokenUrl;

    /**
     * Constructs an OAuth2Template for a given set of client credentials.
     * Assumes that the authorization URL is the same as the authentication URL.
     *
     * @param clientId       the client ID
     * @param clientSecret   the client secret
     * @param authorizeUrl   the base URL to redirect to when doing authorization code or implicit grant authorization
     * @param accessTokenUrl the URL at which an authorization code, refresh token, or user credentials may be exchanged for an access token.
     */
    public WxServiceOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = accessTokenUrl;
    }

    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters) {
        String accessTokenRequestUrl = this.accessTokenUrl + "?appid=" + this.clientId +
            "&secret=" + this.clientSecret +
            "&code=" + authorizationCode +
            "&grant_type=authorization_code" +
            "&redirect_uri=" + redirectUri;
        return getAccessToken(accessTokenRequestUrl);
    }

    @Override
    public AccessGrant refreshAccess(String refreshToken, MultiValueMap<String, String> additionalParameters) {
        String refreshTokenUrl = REFRESH_TOKEN_URL + "?appid=" + clientId +
            "&grant_type=refresh_token" +
            "&refresh_token=" + refreshToken;
        return getAccessToken(refreshTokenUrl);
    }

    private AccessGrant getAccessToken(String accessTokenRequestUrl) {
        log.info("获取微信令牌请求URL: {}", accessTokenRequestUrl);
        @SuppressWarnings("unchecked")
        Map<String, String> result = (Map<String, String>) getRestTemplate().getForObject(accessTokenRequestUrl, Map.class);
        log.info("获取微信令牌信息: {}", ObjectUtils.stringify(result));

        if (Objects.isNull(result)) {
            throw new FailureExchangeAccessTokenException("获取AccessToken失败：" + result);
        }

        // 返回错误码时直接返回空
        if(StringUtils.isNotBlank(result.get("errcode"))) {
            String errcode = result.get("errcode");
            String errmsg = result.get("errmsg");
            throw new FailureExchangeAccessTokenException("获取AccessToken失败, errcode：" + errcode + "，errmsg：" + errmsg);
        }

        WxAccessGrant accessToken = new WxAccessGrant(
            result.get("access_token"),
            result.get("scope"),
            result.get("refresh_token"),
            Long.valueOf(result.get("expires_in"))
        );

        accessToken.setOpenId(result.get("openid"));
        return accessToken;
    }

    /**
     * 构建获取授权码的请求。也就是引导用户跳转到微信的地址。
     */
    @Override
    public String buildAuthenticateUrl(OAuth2Parameters parameters) {
        String url = super.buildAuthenticateUrl(parameters);
        url = url + "&appid=" + this.clientId + "&scope=snsapi_login";
        return url;
    }

    @Override
    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
        return this.buildAuthenticateUrl(parameters);
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }
}
