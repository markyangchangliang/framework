package com.markyang.framework.pojo.enumeration.system;

import com.markyang.framework.pojo.common.support.FrameworkEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别枚举类
 *
 * @author yangchangliang
 * @version 1
 */
@AllArgsConstructor
@Getter
public enum GenderEnum implements FrameworkEnum {

    /**
     * 男性
     */
    MALE("a"),
    /**
     * 女性
     */
    FEMALE("b"),
    /**
     * 未知
     */
    UN_KNOW("c");

    /**
     * 值
     */
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
