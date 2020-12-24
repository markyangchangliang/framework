package com.markyang.framework.auth.config;

import com.google.common.collect.Lists;
import com.markyang.framework.auth.handler.AuthTokenAuthoritiesCacheEnhancer;
import com.markyang.framework.auth.token.FrameworkPreAuthenticationProvider;
import com.markyang.framework.auth.token.SmsTokenGranter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.sql.DataSource;

/**
 * 认证服务配置
 * @author yangchangliang
 */
@Configuration
@AllArgsConstructor
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    private final DataSource dataSource;

    private final JwtAccessTokenConverter jwtTokenEnhancer;

    private final ClientDetailsService clientDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final WebResponseExceptionTranslator<OAuth2Exception> webResponseExceptionTranslator;

    private final AuthTokenAuthoritiesCacheEnhancer authTokenAuthoritiesCacheEnhancer;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final UserDetailsService userDetailsService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(this.dataSource).passwordEncoder(this.passwordEncoder);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setAuthenticationManager(new ProviderManager(Lists.newArrayList(new FrameworkPreAuthenticationProvider())));
        tokenServices.setClientDetailsService(this.clientDetailsService);
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(true);
        tokenServices.setTokenStore(new JwtTokenStore(this.jwtTokenEnhancer));

        // 设置Token加强器
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Lists.newArrayList(
            this.jwtTokenEnhancer,
            this.authTokenAuthoritiesCacheEnhancer
        ));

        tokenServices.setTokenEnhancer(tokenEnhancerChain);

        endpoints.authenticationManager(this.authenticationManager)
            .allowedTokenEndpointRequestMethods(HttpMethod.POST)
            .userDetailsService(this.userDetailsService)
            .allowedTokenEndpointRequestMethods(HttpMethod.GET)
            .tokenServices(tokenServices);

        // 复合自定义TokenGranter
        OAuth2RequestFactory requestFactory = new DefaultOAuth2RequestFactory(this.clientDetailsService);
        CompositeTokenGranter tokenGranter = new CompositeTokenGranter(Lists.newArrayList(
            endpoints.getTokenGranter(),
            // 短信登录Granter
            new SmsTokenGranter(this.authenticationManager, tokenServices, this.clientDetailsService, requestFactory)
        ));
        endpoints.tokenGranter(tokenGranter);
        // 异常处理
        endpoints.exceptionTranslator(this.webResponseExceptionTranslator);
    }



    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.authenticationEntryPoint(this.authenticationEntryPoint)
            .checkTokenAccess("isFullyAuthenticated()")
            .tokenKeyAccess("isFullyAuthenticated()")
            .allowFormAuthenticationForClients()
            .addObjectPostProcessor(new ClientCredentialsTokenEndpointFilterPostProcessor());
    }

    public class ClientCredentialsTokenEndpointFilterPostProcessor implements ObjectPostProcessor<ClientCredentialsTokenEndpointFilter> {

        /**
         * Initialize the object possibly returning a modified instance that should be used
         * instead.
         *
         * @param object the object to initialize
         * @return the initialized version of the object
         */
        @Override
        public <O extends ClientCredentialsTokenEndpointFilter> O postProcess(O object) {
            object.setAuthenticationEntryPoint(AuthServerConfig.this.authenticationEntryPoint);
            return object;
        }
    }
}
