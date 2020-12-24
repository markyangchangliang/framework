package com.markyang.framework.pojo.constant;

import java.time.Duration;

/**
 * 缓存常量类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/26 3:44 下午 星期四
 */
public interface CacheConstants {

    /**
     * 缓存键分隔符
     */
    String CACHE_KEY_SEPARATOR = ":";

    /**
     * 缓存前缀
     */
    String CACHE_PREFIX = "framework" + CACHE_KEY_SEPARATOR + "cache";

    /**
     * 公用信息缓存键前缀
     */
    String COMMON_INFO_CACHE_KEY_PREFIX = "_commonInfo";

    /**
     * 支付信息缓存前缀
     */
    String PAYMENT_DETAILS_CACHE_KEY_PREFIX = CACHE_PREFIX + CACHE_KEY_SEPARATOR + "paymentDetails";

    /**
     * 退款信息缓存前缀
     */
    String REFUND_DETAILS_CACHE_KEY_PREFIX = CACHE_PREFIX + CACHE_KEY_SEPARATOR + "refundDetails";

    /**
     * 支付信息缓存前缀
     */
    String PAYMENT_INFO_CACHE_KEY_PREFIX = CACHE_PREFIX + CACHE_KEY_SEPARATOR + "paymentInfo";

    /**
     * 支付持续时间
     */
    Duration PAYMENT_DURATION_TIME = Duration.ofMinutes(9);

    /**
     * 支付信息缓存键缓存前缀
     */
    String PAYMENT_INFO_CACHE_KEY_CACHE_KEY_PREFIX = CACHE_PREFIX + CACHE_KEY_SEPARATOR + "paymentInfoCacheKey";
}
