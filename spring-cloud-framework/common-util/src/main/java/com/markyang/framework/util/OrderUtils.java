package com.markyang.framework.util;

import com.markyang.framework.util.exception.UtilException;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * 订单工具类
 *
 * @author yangchangliang
 * @version 1
 */
public final class OrderUtils {

    private static final String LOCKING_KEY = "generateSerialNumber";

    /**
     * 生成流水号
     * @param length 流水号长度
     * @return 流水号
     */
    public static String generateSerialNumber(int length, boolean includeAlpha) {
        boolean acquired = RedisLockUtils.lock(LOCKING_KEY, 10, 120);
        if (!acquired) {
            throw new UtilException("生成序列号失败：太挤了");
        }
        try {
            String datetimePattern = "yyyyMMddHHmmssSSS";
            int patternLength = datetimePattern.length();
            if (length < patternLength) {
                return RandomStringUtils.randomNumeric(length, length + 1);
            }
            int randomStringLength = length - patternLength;
            if (randomStringLength == 0) {
                return DateTimeUtils.toDateTimeStringFormat(datetimePattern);
            }
            return DateTimeUtils.toDateTimeStringFormat(datetimePattern) + (includeAlpha ? RandomStringUtils.randomAlphanumeric(randomStringLength, randomStringLength + 1) : RandomStringUtils.randomNumeric(randomStringLength, randomStringLength + 1));
        } finally {
            RedisLockUtils.unlock(LOCKING_KEY);
        }
    }
}
