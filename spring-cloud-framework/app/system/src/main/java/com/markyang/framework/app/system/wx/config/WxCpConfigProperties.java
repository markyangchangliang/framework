package com.markyang.framework.app.system.wx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 企业微信配置
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/31 5:14 下午 星期二
 */
@Component
@Data
@ConfigurationProperties(prefix = "framework.wx.cp")
public class WxCpConfigProperties {

    /**
     * 微信企业号的corpId
     */
    private String corpId;
    /**
     * 设置微信企业号的corpSecret
     */
    private String corpSecret;

    /**
     * 机构端代理小程序配置
     */
    private AgentAppProperties org = new AgentAppProperties();

    @Data
    public static class AgentAppProperties {
        /**
         * 微信企业号应用ID
         */
        private Integer agentId;
        /**
         * 微信企业号应用的secret
         */
        private String secret;
        /**
         * 微信企业号应用的EncodingAESKey
         */
        private String aesKey;
    }
}
