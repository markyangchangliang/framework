package com.markyang.framework.app.system.enumeration;

import com.markyang.framework.pojo.common.support.FrameworkEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * User枚举类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/30 9:58 星期一
 */
@AllArgsConstructor
@Getter
public enum UserEnum implements FrameworkEnum {
    /**
     * 用户启用状态
     */
    STATUS_ENABLED("a"),
    /**
     * 用户停用状态
     */
    STATUS_DISABLED("b");

    private String value;

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
