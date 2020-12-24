package com.markyang.framework.config.schedule;

import com.alibaba.druid.pool.DruidDataSource;
import com.markyang.framework.pojo.schedule.FrameworkJob;
import com.markyang.framework.util.ScheduleUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.quartz.SchedulerException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSourceInitializer;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

/**
 * Job详情配置
 *
 * @author yangchangliang
 * @version 1
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.quartz", name = "auto-startup")
@Import(ScheduleUtils.class)
public class QuartzConfig {

    @Bean
    public JobDetailFactoryBean frameworkJobDetail() throws SchedulerException {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setDurability(true);
        factoryBean.setName(FrameworkJob.JOB_KEY.getName());
        factoryBean.setGroup(FrameworkJob.JOB_KEY.getGroup());
        factoryBean.setJobClass(FrameworkJob.class);
        factoryBean.setDescription("框架顶级任务");
        return factoryBean;
    }

    @Bean
    public QuartzDataSourceInitializer quartzDataSourceInitializer(QuartzDataSourceHolder quartzDataSourceHolder, ResourceLoader resourceLoader, QuartzProperties properties) {
        return new QuartzDataSourceInitializer(quartzDataSourceHolder.getDruidDataSource(), resourceLoader, properties);
    }

    @Bean
    public QuartzDataSourceHolder quartzDataSourceHolder(QuartzConfigProperties quartzConfigProperties) {
        // 构建数据源
        DruidDataSource dataSource = quartzConfigProperties.getDataSource().initializeDataSourceBuilder().type(DruidDataSource.class).build();
        dataSource.setMaxActive(2);
        dataSource.setInitialSize(2);
        dataSource.setMinIdle(2);
        dataSource.setAsyncInit(true);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(-1);
        return new QuartzDataSourceHolder(dataSource);
    }

    @Data
    @AllArgsConstructor
    public static class QuartzDataSourceHolder {

        /**
         * 数据源
         */
        private DruidDataSource druidDataSource;
    }
}
