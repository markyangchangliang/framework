package com.markyang.framework.config.auth;

import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

/**
 * Web安全公用配置
 *
 * @author yangchangliang
 * @version 1
 */
@Configuration
public class WebSecurityCommonBeanConfig {

    /**
     * 安全表达式执行器
     * @param applicationContext applicationContext对象
     * @return 表达式执行器
     */
    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler webSecurityExpressionHandler = new OAuth2WebSecurityExpressionHandler();
        webSecurityExpressionHandler.setApplicationContext(applicationContext);
        return webSecurityExpressionHandler;
    }

    /**
     * JWT token转换器配置 用于转为自定义的用户对象
     * @return 配置器
     */
    @Bean
    public JwtAccessTokenConverterConfigurer jwtAccessTokenConverterConfigurer(AuthenticatedAuthenticationConverter authenticatedAuthenticationConverter) {
        return converter -> {
            DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
            accessTokenConverter.setUserTokenConverter(authenticatedAuthenticationConverter);
            converter.setAccessTokenConverter(accessTokenConverter);
        };
    }

}
