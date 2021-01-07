package com.markyang.framework.app.system.enumeration;

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
public enum DeptEnum implements FrameworkEnum {
    /**
     * XX科室
     */
    TYPE_MEDICAL_TECHNOLOGY ("a"),

    /**
     * 行政科室
     */
    TYPE_ADMINISTRATIVE("b");

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
