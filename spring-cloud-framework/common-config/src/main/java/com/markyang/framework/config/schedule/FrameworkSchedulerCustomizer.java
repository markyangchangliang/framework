package com.markyang.framework.config.schedule;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * Scheduler工厂自定义器
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@ConditionalOnProperty(prefix = "spring.quartz", name = "auto-startup")
@Order(1)
@AllArgsConstructor
public class FrameworkSchedulerCustomizer implements SchedulerFactoryBeanCustomizer {


    private final QuartzConfig.QuartzDataSourceHolder quartzDataSourceHolder;

    /**
     * Customize the {@link SchedulerFactoryBean}.
     *
     * @param schedulerFactoryBean the scheduler to customize
     */
    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        schedulerFactoryBean.setDataSource(quartzDataSourceHolder.getDruidDataSource());
        // 自动赋值applicationContext给SchedulerContext
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        schedulerFactoryBean.setTransactionManager(new DataSourceTransactionManager(quartzDataSourceHolder.getDruidDataSource()));
    }
}
