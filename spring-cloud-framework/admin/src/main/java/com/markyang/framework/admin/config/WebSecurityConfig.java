package com.markyang.framework.admin.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.util.UUID;

/**
 * 资源服务器配置
 *
 * @author yangchangliang
 * @version 1
 */
@Configuration
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AdminServerProperties adminServerProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(this.adminServerProperties.path("/"));
        http.formLogin()
            .loginPage(this.adminServerProperties.path("/login"))
            .successHandler(successHandler)
            .and()
            .logout()
            .logoutUrl(this.adminServerProperties.path("/logout"))
            .clearAuthentication(true)
            .and()
            .httpBasic()
            .and()
            .csrf()
            .disable()
            .rememberMe()
            .key(UUID.randomUUID().toString())
            .tokenValiditySeconds(3600 * 24 * 7)
            .and()
            .authorizeRequests()
            .antMatchers(this.adminServerProperties.path("/login"), this.adminServerProperties.path("/assets/**"))
            .permitAll()
            .antMatchers("/actuator/**")
            .hasIpAddress("192.168.1.6")
            .anyRequest()
            .authenticated();
    }
}
