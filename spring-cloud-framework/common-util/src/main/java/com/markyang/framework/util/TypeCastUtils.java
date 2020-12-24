package com.markyang.framework.util;

/**
 * 类型转换工具类
 *
 * @author yangchangliang
 * @version 1
 */
public final class TypeCastUtils {

    /**
     * 类型转换
     * @param object 对象
     * @param <T> 目标类型泛型
     * @return 目标对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object object) {
        return (T) object;
    }
}
