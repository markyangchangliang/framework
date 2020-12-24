package com.markyang.framework.pojo.enumeration.payment;

import com.markyang.framework.pojo.common.support.FrameworkEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 退款状态枚举类
 *
 * @author yangchangliang
 * @version 1
 */
@AllArgsConstructor
@Getter
public enum RefundStatusEnum implements FrameworkEnum {
    /**
     * 退款中
     */
    REFUNDING("a"),
    /**
     * 退款成功
     */
    REFUNDED_SUCCESS("b"),
    /**
     * 退款失败
     */
    REFUNDED_FAILURE("c"),

    /**
     * 退款已经关闭
     */
    CLOSED("d");

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
