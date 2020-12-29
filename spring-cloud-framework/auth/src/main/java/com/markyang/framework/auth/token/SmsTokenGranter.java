package com.markyang.framework.auth.token;

import com.markyang.framework.auth.filter.SmsAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

public class SmsTokenGranter extends AbstractFrameworkTokenGranter {
    private static final String GRANT_TYPE = "sms";

    public SmsTokenGranter(AuthenticationManager authenticationManager,
                                             AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    @Override
    public Authentication extractAuthentication(Map<String, String> parameters) {
        String phone = parameters.get(SmsAuthenticationFilter.SPRING_SECURITY_FORM_PHONE_KEY);
        return new SmsAuthenticationToken(phone);
    }
}
