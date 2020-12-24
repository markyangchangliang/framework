package com.markyang.framework.app.base.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件上传配置类
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@ConfigurationProperties(prefix = "framework.upload.fs")
@Data
public class FileSystemUploadConfigProperties {
    /**
     * 文件存储的基础路径
     */
    private String path;

    /**
     * 域名
     */
    private String domain;

    /**
     * 文件最大大小
     */
    private Integer maxSize;
}
