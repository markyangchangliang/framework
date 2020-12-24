package com.markyang.framework.pojo.auth;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 认证患者
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/4/5 3:19 下午 星期日
 */
@Data
@Builder
public class AuthenticatedPatient implements Serializable {

    private static final long serialVersionUID = 2461479646548962432L;

    /**
     * 患者ID
     */
    private String userId;

    /**
     * 患者姓名
     */
    private String name;

    /**
     * 微信OpenId
     */
    private String wxOpenId;

    /**
     * 微信SessionKey
     */
    private String wxSessionKey;

    /**
     * 支付宝OpenId
     */
    private String aliOpenId;

    /**
     * 微信UnionId
     */
    private String wxUnionId;

    /**
     * 支付宝UnionId
     */
    private String aliUnionId;
}
