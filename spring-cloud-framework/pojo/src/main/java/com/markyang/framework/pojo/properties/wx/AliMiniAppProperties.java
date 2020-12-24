package com.markyang.framework.pojo.properties.wx;

import lombok.Data;

/**
 * 支付宝小程序配置
 *
 * @author yangchangliang
 * @version 1
 */
@Data
public class AliMiniAppProperties {

    /**
     * 小程序的appId
     */
    private String appId;
    /**
     * 小程序的appSecret
     */
    private String appSecret;
}
