package com.markyang.framework.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 安全公用配置
 *
 * @author yangchangliang
 * @version 1
 */
@Configuration
public class SecurityCommonBeanConfig {

    /**
     * 密码加密器
     * @return 加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
