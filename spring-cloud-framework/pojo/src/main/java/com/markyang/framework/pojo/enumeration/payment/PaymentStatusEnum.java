package com.markyang.framework.pojo.enumeration.payment;

import com.markyang.framework.pojo.common.support.FrameworkEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付状态枚举类
 *
 * @author yangchangliang
 * @version 1
 */
@AllArgsConstructor
@Getter
public enum PaymentStatusEnum implements FrameworkEnum {
    /**
     * 待支付（新支付）
     */
    WAITING_PAY("a"),
    /**
     * 支付中
     */
    PAYING("b"),
    /**
     * 支付成功
     */
    PAID_SUCCESS("c"),
    /**
     * 支付失败
     */
    PAID_FAILURE("d"),
    /**
     * 支付已关闭
     */
    CLOSED("e"),
    /**
     * 支付已退款
     */
    REFUNDED("f"),
    /**
     * 支付已撤销
     */
    REVERSED("g");

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
