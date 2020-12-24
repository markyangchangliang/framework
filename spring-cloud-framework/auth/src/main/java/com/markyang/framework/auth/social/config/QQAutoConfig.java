package com.markyang.framework.auth.social.config;

import com.markyang.framework.auth.properties.SocialProperties;
import com.markyang.framework.auth.social.core.SocialAppProperties;
import com.markyang.framework.auth.social.core.SocialAutoConfigurerAdapter;
import com.markyang.framework.auth.social.qq.service.QQServiceConnectionFactory;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * QQ配置
 * @author yangchangliang
 */
@Configuration
@ConditionalOnProperty(prefix = "framework.auth.social.qq", name = "app-id")
@AllArgsConstructor
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    private final SocialProperties socialProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        SocialAppProperties qq = this.socialProperties.getQq();
        return new QQServiceConnectionFactory("qq", qq.getAppId(), qq.getAppSecret());
    }
}
