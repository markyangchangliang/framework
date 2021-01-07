package com.markyang.framework.auth.token;

import com.markyang.framework.auth.filter.WxMiniAppAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

/**
 * 微信小程序token生成
 * @author markyang
 */
public class WxMiniAppTokenGranter extends AbstractFrameworkTokenGranter {
    private static final String GRANT_TYPE = "wx_mini_app_code";

    public WxMiniAppTokenGranter(AuthenticationManager authenticationManager,
                                 AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    @Override
    public Authentication extractAuthentication(Map<String, String> parameters) {
        String code = parameters.get(WxMiniAppAuthenticationFilter.SPRING_SECURITY_FORM_CODE_KEY);
        String appId = parameters.get(WxMiniAppAuthenticationFilter.SPRING_SECURITY_FORM_APP_ID_KEY);
        return new WxMiniAppAuthenticationToken(WxMiniAppAuthenticationProvider.WxMiniAppCodePrincipal.of(appId, code));
    }
}
