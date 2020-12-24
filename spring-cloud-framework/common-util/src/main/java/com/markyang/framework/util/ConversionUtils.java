package com.markyang.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 转换工具类
 *
 * @author yangchangliang
 * @version 1
 */
public final class ConversionUtils {

    /**
     * 下划线
     */
    private static final String DASH = "_";

    /**
     * 驼峰式命名转下划线式命名
     * @param camel 驼峰式命名字符串
     * @return 下划线式字符串
     */
    public static String camelToDash (String camel) {
        String res = camel.replaceAll("([A-Z])", "_$1").toLowerCase();
        if (res.indexOf(DASH) == 0) {
            return res.substring(1);
        }
        return res;
    }

    /**
     * 下划线式命名转驼峰式命名
     * @param dash 下划线式字符串
     * @return 驼峰式字符串
     */
    public static String dashToCamel (String dash) {
        String[] arr = dash.split(DASH);
        StringBuilder res = new StringBuilder();
        for (String str :
            arr) {
            res.append(str.substring(0, 1).toUpperCase()).append(str.substring(1));
        }
        return res.toString();
    }

    /**
     * 下划线转为中划线命名形式
     * @return 中划线命名
     */
    public static String dashToKebab(String dash) {
        return StringUtils.replace(dash, "_", "-");
    }

    /**
     * 下划线式命名转首字母小写驼峰式命名
     * @param dash 下划线式字符串
     * @return 首字母小写式驼峰命名字符串
     */
    public static String dashToFirstLowerCamel (String dash) {
        return firstLower(dashToCamel(dash));
    }

    /**
     * 首字母小写
     * @param str 字符串
     * @return 首字母小写的字符串
     */
    public static String firstLower (String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
}
