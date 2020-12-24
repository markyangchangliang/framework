package com.markyang.framework.app.system.config;

import com.markyang.framework.pojo.constant.FrameworkConstants;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * 资源配置
 * @author yangchangliang
 */
@Configuration
@EnableResourceServer
@AllArgsConstructor
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("system").authenticationEntryPoint(this.authenticationEntryPoint);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(FrameworkConstants.STATIC_RESOURCES_URI)
            .permitAll()
            .antMatchers(FrameworkConstants.API_DOC_URI,
                FrameworkConstants.API_URI, FrameworkConstants.ENDPOINT_URI,
                "/user/username/*", "/user/phone/*", "/commonInfo/**")
            .permitAll()
            .anyRequest()
            .fullyAuthenticated();
    }
}
