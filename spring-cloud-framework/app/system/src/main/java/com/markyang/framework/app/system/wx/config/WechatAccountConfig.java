package com.markyang.framework.app.system.wx.config;

import com.markyang.framework.util.JsonUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 微信配置
 * @author yangchangliang
 */
@Data
@ConfigurationProperties(prefix = "framework.wx.mp")
public class WechatAccountConfig {

  private List<MpConfig> configs;

  @Override
  public String toString() {
    return JsonUtils.toJson(this);
  }
  @Data
  public static class MpConfig {
    /**
     * 设置微信公众号的EncodingAESKey
     */
    private String aesKey;
    /**
     * 设置微信公众号的appid
     */
    private String appId;
    /**
     * 设置微信公众号的app secret
     */
    private String secret;
    /**
     * 设置微信公众号的token
     */
    private String token;
  }
}
