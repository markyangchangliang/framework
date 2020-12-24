package com.markyang.framework.config.message;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯云短信配置
 *
 * @author yangchangliang
 * @version 1
 */
@Configuration
@AllArgsConstructor
public class SmsConfig {

    /**
     * 短信客户端
     * @param credential 认证对象
     * @return 短信客户端对象
     */
    @Bean
    public SmsClient smsClient(Credential credential) {

        /* 实例化 SMS 的 client 对象
         * 第二个参数是地域信息，可以直接填写字符串 ap-guangzhou，或者引用预设的常量 */
        return new SmsClient(credential, "");
    }
}
