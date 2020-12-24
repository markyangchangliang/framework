package com.markyang.framework.pojo.enumeration.payment;

import com.markyang.framework.pojo.common.support.FrameworkEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 终端类型枚举类
 *
 * @author yangchangliang
 * @version 1
 */
@AllArgsConstructor
@Getter
public enum ClientTypeEnum implements FrameworkEnum {
    /**
     * APP支付类型
     */
    APP("a"),
    /**
     * 小程序支付类型
     */
    MINI_APP("b"),
    /**
     * 网页支付类型
     */
    WEB("c");

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
