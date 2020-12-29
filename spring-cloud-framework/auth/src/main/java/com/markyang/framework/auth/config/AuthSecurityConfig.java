package com.markyang.framework.auth.config;

import com.markyang.framework.auth.filter.SmsAuthenticationFilter;
import com.markyang.framework.auth.filter.WxMiniAppAuthenticationFilter;
import com.markyang.framework.auth.properties.AuthProperties;
import com.markyang.framework.auth.token.AuthenticatedUserAuthenticationProvider;
import com.markyang.framework.auth.token.SmsAuthenticationProvider;
import com.markyang.framework.auth.token.WxMiniAppAuthenticationProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 认证配置
 * @author markyang
 */
@Configuration
@AllArgsConstructor
public class AuthSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final AuthProperties authProperties;
    private final SmsAuthenticationProvider smsAuthenticationProvider;
    private final WxMiniAppAuthenticationProvider wxMiniAppAuthenticationProvider;
    private final AuthenticatedUserAuthenticationProvider authenticatedUserAuthenticationProvider;

    @Override
    public void configure(HttpSecurity builder) {
        // 自定义短信认证
        SmsAuthenticationFilter smsAuthenticationFilter = new SmsAuthenticationFilter(this.authProperties.getSmsLoginProcessingUrl());
        smsAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));

        builder.authenticationProvider(this.smsAuthenticationProvider)
            .addFilterBefore(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 微信小程序Code认证
        WxMiniAppAuthenticationFilter wxMiniAppAuthenticationFilter = new WxMiniAppAuthenticationFilter(this.authProperties.getSmsLoginProcessingUrl());
        wxMiniAppAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));

        builder.authenticationProvider(this.wxMiniAppAuthenticationProvider)
            .addFilterBefore(wxMiniAppAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // 用户信息认证
        builder.authenticationProvider(this.authenticatedUserAuthenticationProvider);
    }
}
