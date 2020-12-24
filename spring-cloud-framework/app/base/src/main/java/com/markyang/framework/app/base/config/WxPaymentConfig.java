package com.markyang.framework.app.base.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.markyang.framework.config.payment.PaymentConfigProperties;
import com.markyang.framework.pojo.dto.system.OrgInfoDto;
import com.markyang.framework.pojo.properties.wx.WxPaymentProperties;
import com.markyang.framework.service.common.CommonInfoCacheService;
import com.markyang.framework.service.wx.CompositeWxPayService;
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
public class WxPaymentConfig {

    private final CommonInfoCacheService commonInfoCacheService;

    /**
     * 微信支付Service对象
     * @return 支付Service
     */
    @Bean
    @ConditionalOnProperty(prefix = "framework.beans", name = "composite-wx-pay-service", havingValue = "true")
    public CompositeWxPayService wxPayService(PaymentConfigProperties paymentConfigProperties) {
        WxPaymentProperties wx = paymentConfigProperties.getWx();
        Map<String, WxPayService> wxPayServiceMap = this.commonInfoCacheService.getOrgInfo().parallelStream()
            .filter(orgInfoDto -> Objects.nonNull(orgInfoDto.getConfigDto()) && Objects.nonNull(orgInfoDto.getConfigDto().getWxConfig()) && StringUtils.isNotBlank(orgInfoDto.getConfigDto().getWxConfig().getAppId()))
            .collect(Collectors.toMap(OrgInfoDto::getId, orgInfoDto -> {
                // 微信支付服务类配置
                WxPayConfig wxPayConfig = new WxPayConfig();
                wxPayConfig.setAppId(wx.getAppId());
                wxPayConfig.setMchId(wx.getMchId());
                wxPayConfig.setMchKey(wx.getMchKey());
                wxPayConfig.setKeyPath(wx.getKeyPath());

                wxPayConfig.setSubMchId(orgInfoDto.getConfigDto().getWxConfig().getSubMerchantId());
                wxPayConfig.setSubAppId(orgInfoDto.getConfigDto().getWxConfig().getAppId());

                // 不使用沙箱环境
                wxPayConfig.setUseSandboxEnv(false);

                WxPayService wxPayService = new WxPayServiceImpl();
                wxPayService.setConfig(wxPayConfig);
                return wxPayService;
            }));
        return new CompositeWxPayService(wxPayServiceMap);
    }
}
