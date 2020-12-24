package com.markyang.framework.gateway.config;

import com.markyang.framework.gateway.filter.AuditLogFilter;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * 资源服务器配置
 *
 * @author yangchangliang
 * @version 1
 */
@Configuration
@EnableResourceServer
@AllArgsConstructor
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuditLogFilter auditLogFilter;
    private final RouteLocator routeLocator;
    private final CorsConfigurationSource corsConfigurationSource;
    private final AuthConfigProperties authConfigProperties;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("gateway")
            .accessDeniedHandler(this.accessDeniedHandler)
            .authenticationEntryPoint(this.authenticationEntryPoint)
            .expressionHandler(this.oAuth2WebSecurityExpressionHandler);
    }

    @SuppressWarnings("SpringElInspection")
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(this.auditLogFilter, ExceptionTranslationFilter.class)
            .headers()
            .frameOptions()
            .disable()
            .and()
            .cors()
            .configurationSource(this.corsConfigurationSource)
            .and()
            .authorizeRequests()
            .antMatchers(FrameworkConstants.STATIC_RESOURCES_URI)
            .permitAll()
            .antMatchers("/*" + FrameworkConstants.API_DOC_URI, "/*" + FrameworkConstants.API_URI)
            .permitAll()
            .antMatchers(this.authConfigProperties.getPermittedUriPatterns())
            .permitAll()
            .antMatchers(this.routeLocator.getRoutes().parallelStream().map(Route::getFullPath).filter(path -> !StringUtils.startsWithAny(path, "/auth")).toArray(String[]::new))
            .access("isFullyAuthenticated() && @accessDecisionService.decide(principal, request)")
            .antMatchers(FrameworkConstants.ENDPOINT_URI)
            .hasIpAddress(this.authConfigProperties.getSpringBootAdminIp())
            .anyRequest()
            .permitAll();
    }
}
