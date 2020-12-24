package com.markyang.framework.app.system.wx.config;

import com.markyang.framework.app.system.wx.exception.WechatException;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 微信服务类配置
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/31 5:20 下午 星期二
 */
@Configuration
@AllArgsConstructor
public class WxServiceConfig {

    private final WechatAccountConfig properties;

    /**
     * 企业微信Service
     * @param configProperties 配置类
     * @return 服务类
     */
    @Bean
    public WxCpService wxCpService(WxCpConfigProperties configProperties) {
        // 配置企业微信
        WxCpDefaultConfigImpl config = new WxCpDefaultConfigImpl();
        config.setCorpId(configProperties.getCorpId());
        config.setCorpSecret(configProperties.getCorpSecret());
        WxCpService wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(config);
        return wxCpService;
    }
  /**
   * 微信公众号Service
   * @return 服务类
   */
    @Bean
    public WxMpService wxMpService() {
        final List<WechatAccountConfig.MpConfig> configs = this.properties.getConfigs();
        if (configs == null) {
            throw new WechatException("未获取到微信appId等配置！");
        }
        WxMpService service = new WxMpServiceImpl();
        service.setMultiConfigStorages(configs
            .stream().map(ax -> {
                WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
                configStorage.setAppId(ax.getAppId());
                configStorage.setSecret(ax.getSecret());
                configStorage.setToken(ax.getToken());
                configStorage.setAesKey(ax.getAesKey());
                return configStorage;
            }).collect(Collectors.toMap(WxMpDefaultConfigImpl::getAppId, ax -> ax, (ox, nx) -> ox)));
        return service;
    }
}
