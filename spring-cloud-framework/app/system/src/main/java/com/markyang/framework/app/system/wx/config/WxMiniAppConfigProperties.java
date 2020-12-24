package com.markyang.framework.app.system.wx.config;

import com.markyang.framework.pojo.properties.wx.WxMiniAppProperties;
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
@ConfigurationProperties(prefix = "framework.wx.mini-app")
public class WxMiniAppConfigProperties {

    /**
     * 机构小程序配置
     */
    private WxMiniAppProperties org = new WxMiniAppProperties();
}
