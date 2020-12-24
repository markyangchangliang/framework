package com.markyang.framework.app.base.config;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;

import java.nio.charset.StandardCharsets;

/**
 * 验证配置
 *
 * @author yangchangliang
 * @version 1
 */
@Configuration
public class ValidationConfig {

    @Bean
    public static LocalValidatorFactoryBean frameworkValidator(ApplicationContext applicationContext) {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("validation-messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        ResourceBundleMessageInterpolator messageInterpolator = new ResourceBundleMessageInterpolator(
            new MessageSourceResourceBundleLocator(messageSource),
            true
        );
        factoryBean.setApplicationContext(applicationContext);
        factoryBean.setMessageInterpolator(messageInterpolator);
        return factoryBean;
    }
}
