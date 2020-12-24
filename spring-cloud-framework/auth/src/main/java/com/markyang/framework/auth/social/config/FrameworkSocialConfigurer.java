package com.markyang.framework.auth.social.config;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 框架层面社区配置
 * @author yangchangliang
 */
public class FrameworkSocialConfigurer extends SpringSocialConfigurer {

    private final String processingUrl;

    public FrameworkSocialConfigurer(String processingUrl) {
        this.processingUrl = processingUrl;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(this.processingUrl);
        return (T) filter;
    }
}
