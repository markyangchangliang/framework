package com.markyang.framework.auth.social.core;

import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;

/**
 * 社区自动注册适配器
 * @author yangchangliang
 */
public abstract class SocialAutoConfigurerAdapter extends SocialConfigurerAdapter {

    public SocialAutoConfigurerAdapter() {
    }

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer, Environment environment) {
        configurer.addConnectionFactory(this.createConnectionFactory());
    }
    protected abstract ConnectionFactory<?> createConnectionFactory();
}
