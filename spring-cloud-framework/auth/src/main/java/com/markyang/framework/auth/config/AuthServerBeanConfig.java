package com.markyang.framework.auth.config;

import com.markyang.framework.config.auth.AuthenticatedAuthenticationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * 密钥解析配置
 * @author markyang
 */
@Configuration
public class AuthServerBeanConfig {

    @Bean
    public JwtAccessTokenConverter jwtTokenEnhancer(AuthenticatedAuthenticationConverter authenticatedAuthenticationConverter) {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 配置默认的Token Converter
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(authenticatedAuthenticationConverter);
        converter.setAccessTokenConverter(accessTokenConverter);
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("keys/jwt.key"), "zxrj@123".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
        return converter;
    }

}
