package com.markyang.framework.app.base.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.markyang.framework.pojo.dto.system.OrgConfigDto;
import com.markyang.framework.service.common.CommonInfoCacheService;
import com.markyang.framework.service.wx.CompositeWxMaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 微信服务类配置
 *
 * @author yangchangliang
 * @version 1
 */
@Configuration
@Slf4j
@AllArgsConstructor
public class WxMiniAppConfig {

    private final CommonInfoCacheService commonInfoCacheService;

    /**
     * 微信小程序Service对象
     * @return 小程序服务类
     */
    @ConditionalOnProperty(prefix = "framework.beans", name = "composite-wx-ma-service", havingValue = "true")
    @Bean
    public CompositeWxMaService wxMaService() {
        Map<String, WxMaService> wxMaServiceMap = this.commonInfoCacheService.getOrgInfo().parallelStream()
            .filter(orgInfoDto -> Objects.nonNull(orgInfoDto.getConfigDto())
                && Objects.nonNull(orgInfoDto.getConfigDto().getWxConfig())
                && StringUtils.isNotBlank(orgInfoDto.getConfigDto().getWxConfig().getAppId()))
            .collect(Collectors.toMap(orgInfoDto -> orgInfoDto.getConfigDto().getWxConfig().getAppId(),
                orgInfoDto -> orgInfoDto.getConfigDto().getWxConfig(), (v1, v2) -> v1))
            .entrySet()
            .parallelStream()
            .collect(Collectors.toMap(Map.Entry::getKey, entry -> {
                // 配置
                OrgConfigDto.WxConfig wxConfig = entry.getValue();
                WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
                config.setAppid(wxConfig.getAppId());
                config.setSecret(wxConfig.getAppSecret());
                // 构建Service
                WxMaService wxMaService = new WxMaServiceImpl();
                wxMaService.setWxMaConfig(config);
                return wxMaService;
            }));
        return new CompositeWxMaService(wxMaServiceMap);
    }
}
