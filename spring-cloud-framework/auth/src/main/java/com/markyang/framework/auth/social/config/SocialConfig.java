package com.markyang.framework.auth.social.config;

import com.markyang.framework.auth.social.core.JdbcUsersConnectionRepository;
import com.markyang.framework.auth.properties.SocialProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * 社区配置
 * @author yangchangliang
 */
@Configuration
@EnableSocial
@AllArgsConstructor
public class SocialConfig {

    private final DataSource dataSource;
    private final SocialProperties socialProperties;

    // 配置自动注册逻辑
    //  @Autowired(required = false)
    //  private ConnectionSignUp connectionSignUp;

    @Bean
    public UsersConnectionRepository usersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(this.dataSource, connectionFactoryLocator, Encryptors.noOpText());
        repository.setTablePrefix("oauth_");
        // repository.setConnectionSignUp(this.connectionSignUp);
        return repository;
    }

    @Bean
    public SpringSocialConfigurer springSocialConfigurer() {
        FrameworkSocialConfigurer configurer = new FrameworkSocialConfigurer(this.socialProperties.getProcessingUrl());
        configurer.signupUrl(this.socialProperties.getSignUpUrl());
        return configurer;
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator, this.usersConnectionRepository(connectionFactoryLocator));
    }
}
