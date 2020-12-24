package com.markyang.framework.util;

import com.markyang.framework.pojo.constant.FrameworkConstants;
import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Date;

/**
 * 日期时间工具类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/26 2:03 下午 星期四
 */
public final class DateTimeUtils {

    /**
     * 日期时间格式化器
     */
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(FrameworkConstants.DATE_TIME_PATTERN);

    /**
     * 日期格式化器
     */
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(FrameworkConstants.DATE_PATTERN);

    /**
     * 时间格式化器
     */
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(FrameworkConstants.TIME_PATTERN);

    /**
     * 格式化日期时间
     * @param temporal 时间对象
     * @return 字符串形式
     */
    public static String toStringFormat(Temporal temporal) {
        if (temporal instanceof LocalDateTime) {
            return DATE_TIME_FORMATTER.format(temporal);
        } else if (temporal instanceof LocalDate) {
            return DATE_FORMATTER.format(temporal);
        } else if (temporal instanceof LocalTime) {
            return TIME_FORMATTER.format(temporal);
        } else {
            return "";
        }
    }

    /**
     * 格式化日期时间
     * @param temporal 时间对象
     * @param pattern 格式化模式
     * @return 字符串形式
     */
    public static String toStringFormat(Temporal temporal, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(temporal);
    }

    /**
     * 时间戳转LocalDateTime
     * @param timestamp 时间戳（单位秒）
     * @return 日期时间对象
     */
    public static LocalDateTime fromSecondTimestamp(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
    }

    /**
     * 时间戳转LocalDateTime
     * @param timestamp 时间戳（单位毫秒）
     * @return 日期时间对象
     */
    public static LocalDateTime fromMilliTimestamp(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    /**
     * 解析日期时间对象
     * @param dateTime 日期时间字符串
     * @param pattern 格式
     * @return 日期时间对象
     */
    public static LocalDateTime parseLocalDateTime(String dateTime, String pattern) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 解析日期时间对象
     * @param dateTime 日期时间字符串
     * @return 日期时间对象
     */
    public static LocalDateTime parseLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
    }

    /**
     * 解析日期对象
     * @param date 日期字符串
     * @param pattern 格式
     * @return 日期对象
     */
    public static LocalDate parseLocalDate(String date, String pattern) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 解析日期对象
     * @param date 日期字符串
     * @return 日期对象
     */
    public static LocalDate parseLocalDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    /**
     * 格式化LocalDate
     * @param localDate 日期对象
     * @return 日期字符串
     */
    public static String toDateStringFormat(LocalDate localDate) {
        return DATE_FORMATTER.format(localDate);
    }

    /**
     * 格式化LocalDateTime
     * @param localDateTime 日期时间对象
     * @return 日期字符串
     */
    public static String toDateStringFormat(LocalDateTime localDateTime) {
        return DATE_FORMATTER.format(localDateTime);
    }

    /**
     * 格式化LocalDate
     * @return 日期字符串
     */
    public static String toDateStringFormat() {
        return DATE_FORMATTER.format(LocalDate.now());
    }

    /**
     * 格式化LocalDateTime
     * @param localDateTime 日期时间对象
     * @return 日期时间字符串
     */
    public static String toDateTimeStringFormat(LocalDateTime localDateTime) {
        return DATE_TIME_FORMATTER.format(localDateTime);
    }

    /**
     * 格式化LocalDateTime
     * @param localDateTime 日期时间对象
     * @return 日期时间字符串
     */
    public static String toDateTimeStringFormat(LocalDateTime localDateTime, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(localDateTime);
    }

    /**
     * 格式化LocalDateTime
     * @return 日期时间字符串
     */
    public static String toDateTimeStringFormat() {
        return DATE_TIME_FORMATTER.format(LocalDateTime.now());
    }

    /**
     * 格式化LocalDateTime
     * @param pattern 日期时间格式
     * @return 日期时间字符串
     */
    public static String toDateTimeStringFormat(String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(LocalDateTime.now());
    }

    /**
     * LocalDateTime转为Date对象
     * @param localDateTime 时期时间对象
     * @return Date对象
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 日期对象转LocalDateTime对象
     * @param date 日期对象
     * @return 日期时间对象
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 解析时间对象
     * @param time 时间字符串
     * @return 时间对象
     */
    public static LocalTime parseLocalTime(String time) {
        return LocalTime.parse(time, TIME_FORMATTER);
    }

    /**
     * 是否是当天
     * @param date 日期
     * @return bool
     */
    public static boolean isToday(Date date) {
        return isToday(toLocalDateTime(date));
    }

    /**
     * 是否是当天
     * @param localDateTime 日期
     * @return bool
     */
    public static boolean isToday(LocalDateTime localDateTime) {
        return StringUtils.equals(toDateStringFormat(localDateTime), toDateStringFormat(LocalDate.now()));
    }
}
