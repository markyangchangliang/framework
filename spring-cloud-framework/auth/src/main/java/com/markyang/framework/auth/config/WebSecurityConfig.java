package com.markyang.framework.auth.config;

import com.markyang.framework.auth.handler.AuthAuthenticationFailureHandler;
import com.markyang.framework.auth.properties.AuthProperties;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

import java.util.List;

/**
 * web安全拦截配置
 * @author markyang
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;
    private final AuthSecurityConfig authSecurityConfig;
    private final AuthProperties authProperties;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuthAuthenticationFailureHandler authAuthenticationFailureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService)
            .passwordEncoder(this.passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.apply(this.authSecurityConfig)
            .and()
            .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .failureHandler(this.authAuthenticationFailureHandler)
            .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(this.authenticationEntryPoint)
            .and()
            .authorizeRequests()
            .antMatchers(FrameworkConstants.API_DOC_URI, FrameworkConstants.ENDPOINT_URI)
            .permitAll()
            .antMatchers(this.authProperties.getPermittedUrls())
            .permitAll()
            .anyRequest()
            .fullyAuthenticated()
            .and()
            .csrf()
            .disable()
            .objectPostProcessor(new ProviderManagerPostProcessor());
    }

    public static class ProviderManagerPostProcessor implements ObjectPostProcessor<Object> {

        /**
         * Initialize the object possibly returning a modified instance that should be used
         * instead.
         *
         * @param object the object to initialize
         * @return the initialized version of the object
         */
        @Override
        public <O> O postProcess(O object) {
            if (object instanceof ProviderManager) {
                ProviderManager manager = (ProviderManager) object;
                List<AuthenticationProvider> providers = manager.getProviders();
                for (AuthenticationProvider provider : providers) {
                    if (provider instanceof DaoAuthenticationProvider) {
                        ((DaoAuthenticationProvider) provider).setHideUserNotFoundExceptions(false);
                    }
                }
            }
            return object;
        }
    }
}
