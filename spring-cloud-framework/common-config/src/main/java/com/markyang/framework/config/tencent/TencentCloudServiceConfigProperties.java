package com.markyang.framework.config.tencent;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 腾讯云资源配置
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@ConfigurationProperties(prefix = "framework.tencent")
@Data
public class TencentCloudServiceConfigProperties {

    /**
     * APP_ID
     */
    private String appId;

    /**
     * 密匙ID
     */
    private String secretId;

    /**
     * 密匙Key
     */
    private String secretKey;
}
