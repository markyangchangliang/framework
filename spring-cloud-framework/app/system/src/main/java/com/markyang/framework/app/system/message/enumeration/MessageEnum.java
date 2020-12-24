package com.markyang.framework.app.system.message.enumeration;

import com.markyang.framework.pojo.common.support.FrameworkEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 部门职员枚举类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/30 11:14 星期一
 */
@AllArgsConstructor
@Getter
public enum MessageEnum implements FrameworkEnum {
    /**
     * 审核结果
     */
    REFUSED("2"),
    AUDIT("1"),
    THROUGH("0"),
    AUDITED("待审核"),
    PAST("审核通过"),
    UNPAST("审核未通过");

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
