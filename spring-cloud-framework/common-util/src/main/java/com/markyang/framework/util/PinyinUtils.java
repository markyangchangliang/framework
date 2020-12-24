package com.markyang.framework.util;

import com.github.promeg.pinyinhelper.Pinyin;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 拼音工具类
 * @author yangchangliang
 */
public final class PinyinUtils {

    /**
     * 获取汉字拼音首字母
     * @param string 汉字
     * @return 首字母
     */
    public static String getFirstLetter(String string) {
        string = StringUtils.trim(string);
        if (StringUtils.isNotBlank(string)) {
            String pinyin = Pinyin.toPinyin(string.charAt(0));
            if (pinyin.matches("[A-Za-z]+")) {
                return String.valueOf(pinyin.charAt(0));
            }
            return "#";
        }
        return "#";
    }

    /**
     * 获取汉字拼音首字母
     * @param string 汉字
     * @return 拼音首字母
     */
    private static String toPinyin(String string) {
        string = StringUtils.trim(string);
        if (StringUtils.isBlank(string)) {
            return "z";
        }
        String[] arr = StringUtils.split(Pinyin.toPinyin(string, " "), " ");
        return StringUtils.join(Arrays.stream(arr).parallel().map(s -> {
            String chr = String.valueOf(s.charAt(0));
            if (chr.matches("[A-Za-z]")) {
                return chr;
            } else {
                return "a";
            }
        }).toArray(String[]::new), "");
    }

    /**
     * 获取汉字拼音
     * @param chinese 汉字
     * @return 拼音
     */
    public static String getPinyin(String chinese) {
        return Pinyin.toPinyin(chinese, " ");
    }

}
