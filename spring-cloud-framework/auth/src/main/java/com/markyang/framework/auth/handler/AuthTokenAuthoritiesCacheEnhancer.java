package com.markyang.framework.auth.handler;

import com.google.common.collect.Maps;
import com.markyang.framework.pojo.auth.AuthenticatedUser;
import com.markyang.framework.pojo.auth.AuthorityUriKey;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.token.AuthenticatedUserAuthenticationToken;
import com.markyang.framework.util.RedisUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 设置权限存储过期的Token加强器
 *
 * @author yangchangliang
 * @version 1
 */
@Component
public class AuthTokenAuthoritiesCacheEnhancer implements TokenEnhancer {
    /**
     * Provides an opportunity for customization of an access token (e.g. through its additional information map) during
     * the process of creating a new token for use by a client.
     *
     * @param accessToken    the current access token with its expiration and refresh token
     * @param authentication the current authentication including client and user details
     * @return a new token enhanced with additional information
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if ((authentication.getUserAuthentication() instanceof AuthenticatedUserAuthenticationToken) || !(authentication.getPrincipal() instanceof AuthenticatedUser)) {
            return accessToken;
        }
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
        // 处理Redis存储
        // 存储相关权限到Redis
        long expiredTimeSeconds = 0L;
        if (accessToken.getRefreshToken() instanceof ExpiringOAuth2RefreshToken) {
            // 过期时间设置
            expiredTimeSeconds = (((ExpiringOAuth2RefreshToken) accessToken.getRefreshToken()).getExpiration().getTime() - System.currentTimeMillis()) / 1000L;
        }
        List<String[]> antAuthorityGroups;
        if (CollectionUtils.isNotEmpty(authenticatedUser.getAntUris())) {
            antAuthorityGroups = authenticatedUser.getAntUris().parallelStream()
                .map(authority -> StringUtils.split(authority, ","))
                .collect(Collectors.toList());
        } else {
            antAuthorityGroups = Collections.emptyList();
        }
        Map<AuthorityUriKey, Boolean> uriPermissionStatusMap = Maps.newHashMapWithExpectedSize(512);
        RedisUtils.set(FrameworkConstants.USER_AUTHORITIES_CACHE_KEY + authenticatedUser.getUserId(), antAuthorityGroups, Duration.ofSeconds(expiredTimeSeconds));
        RedisUtils.set(FrameworkConstants.USER_AUTHORITY_URI_METHOD_CACHE_KEY + authenticatedUser.getUserId(), uriPermissionStatusMap, Duration.ofSeconds(expiredTimeSeconds));
        return accessToken;
    }
}
