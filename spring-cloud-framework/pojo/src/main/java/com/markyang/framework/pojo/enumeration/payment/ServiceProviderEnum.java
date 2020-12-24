package com.markyang.framework.pojo.enumeration.payment;

import com.markyang.framework.pojo.common.support.FrameworkEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付方式枚举类
 *
 * @author yangchangliang
 * @version 1
 */
@AllArgsConstructor
@Getter
public enum ServiceProviderEnum implements FrameworkEnum {
    /**
     * 微信小程序支付
     */
    WX("a");

    private final String value;

    /**
     * 获取枚举值
     *
     * @return 枚举值
     */
    @Override
    public String getValue() {
        return this.value;
    }
}
