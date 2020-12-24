package com.markyang.framework.util;

import com.markyang.framework.pojo.enumeration.system.GenderEnum;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Date;

/**
 * 通用工具类
 *
 * @author yangchangliang
 * @version 1
 */
public final class CommonUtils {

    /**
     * 根据身份证抽取出生日期
     * @param identity 身份证
     * @return 日期字符串
     */
    public static String extractBirthDateStringFromIdentity(String identity) {
        if (StringUtils.length(identity) != 18) {
            return "";
        }
        String dateString = StringUtils.substring(identity, 6, 14);
        return dateString.substring(0, 4) + "-" + dateString.substring(4, 6) + "-" + dateString.substring(6, 8);
    }

    /**
     * 抽取身份证日期对象
     * @param identity 身份证
     * @return 日期对象
     */
    public static LocalDate extractBirthLocalDateFromIdentity(String identity) {
        return DateTimeUtils.parseLocalDate(extractBirthDateStringFromIdentity(identity));
    }

    /**
     * 抽取身份证日期对象
     * @param identity 身份证
     * @return 日期对象
     */
    public static Date extractBirthDateFromIdentity(String identity) {
        return DateTimeUtils.toDate(extractBirthLocalDateFromIdentity(identity).atStartOfDay());
    }

    /**
     * 根据身份证解析出性别
     * @param identity 身份证
     * @return 性别
     */
    public static GenderEnum extractGenderFromIdentity(String identity) {
        if (StringUtils.length(identity) != 18) {
            return GenderEnum.UN_KNOW;
        }
        int num = Integer.parseInt(StringUtils.substring(identity, identity.length() - 2, identity.length() - 1));
        if (num % 2 == 0) {
            return GenderEnum.FEMALE;
        }
        return GenderEnum.MALE;
    }

    /**
     * 根据身份证计算年龄
     * @param identity 身份证
     * @return 年龄
     */
    public static int computeAgeByIdentity(String identity) {
        return computeAgeByBirthDate(extractBirthLocalDateFromIdentity(identity));
    }

    /**
     * 根据生日计算年龄
     * @param localDate 日期对象
     * @return 年龄
     */
    public static int computeAgeByBirthDate(LocalDate localDate) {
        LocalDate now = LocalDate.now();
        if (now.isBefore(localDate)) {
            return 0;
        }
        return now.getYear() - localDate.getYear();
    }
}
