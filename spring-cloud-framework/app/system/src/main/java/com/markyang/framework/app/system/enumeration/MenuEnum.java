package com.markyang.framework.app.system.enumeration;

import com.markyang.framework.pojo.common.support.FrameworkEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统菜单枚举类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020年5月11日12:02:05
 */
@AllArgsConstructor
@Getter
public enum MenuEnum implements FrameworkEnum {
    /**
     * 启用状态
     */
    STATUS_ENABLED("a"),
    /**
     * 停用状态
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
