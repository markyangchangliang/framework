package com.markyang.framework.auth.social.config;

import com.markyang.framework.auth.properties.SocialProperties;
import com.markyang.framework.auth.social.core.SocialAppProperties;
import com.markyang.framework.auth.social.core.SocialAutoConfigurerAdapter;
import com.markyang.framework.auth.social.wx.service.WxServiceConnectionFactory;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * 微信配置
 * @author yangchangliang
 */
@Configuration
@ConditionalOnProperty(prefix = "framework.auth.social.wx", name = "app-id")
@AllArgsConstructor
public class WxAutoConfig extends SocialAutoConfigurerAdapter {

    private final SocialProperties socialProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        SocialAppProperties wx = this.socialProperties.getWx();
        return new WxServiceConnectionFactory("wx", wx.getAppId(), wx.getAppSecret());
    }
}
