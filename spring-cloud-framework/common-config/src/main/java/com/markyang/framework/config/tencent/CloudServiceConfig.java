package com.markyang.framework.config.tencent;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯云服务配置类
 *
 * @author yangchangliang
 * @version 1
 */
@Configuration
@AllArgsConstructor
public class CloudServiceConfig {

    private final TencentCloudServiceConfigProperties configProperties;

    /**
     * 认证对象
     * @return 认证对象
     */
    @Bean
    public Credential credential() {
        /*
         * 必要步骤：
         * 实例化一个认证对象，入参需要传入腾讯云账户密钥对 secretId 和 secretKey
         * 本示例采用从环境变量读取的方式，需要预先在环境变量中设置这两个值
         * 您也可以直接在代码中写入密钥对，但需谨防泄露，不要将代码复制、上传或者分享给他人
         * CAM 密钥查询：https://console.cloud.tencent.com/cam/capi
         * */
        return new Credential(this.configProperties.getSecretId(), this.configProperties.getSecretKey());
    }

    /**
     * OCR图像识别客户端
     * @param credential 认证对象
     * @return 图像识别对象
     */
    @Bean
    public OcrClient ocrClient(Credential credential) {
        return new OcrClient(credential, "ap-guangzhou");
    }
}
