package com.markyang.framework.pojo.dto.system;

import lombok.Data;

import java.io.Serializable;

/**
 * 机构配置Dto
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/4/23 9:15 下午 星期四
 */
@Data
public class OrgConfigDto implements Serializable {

    private static final long serialVersionUID = -3990347428138100197L;
    /**
     * 微信配置
     */
    private WxConfig wxConfig;

    @Data
    public static class WxConfig implements Serializable {
        private static final long serialVersionUID = 7647500429423551853L;
        /**
         * 小程序ID
         */
        private String appId;
        /**
         * 小程序密匙
         */
        private String appSecret;
        /**
         * 支付的子商户ID
         */
        private String subMerchantId;
    }
}
