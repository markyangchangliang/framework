package com.markyang.framework.config.schedule;

import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 定时任务Quartz配置
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@ConfigurationProperties(prefix = "framework.quartz")
@Data
public class QuartzConfigProperties {

    /**
     * Quartz数据源配置
     */
    private DataSourceProperties dataSource = new DataSourceProperties();
}
