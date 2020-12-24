package com.markyang.framework.util;

import com.markyang.framework.util.exception.RedisLockingUtilException;
import com.markyang.framework.pojo.constant.CacheConstants;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 分布式锁工具类
 * @author yangchangliang
 */
@Slf4j
public final class RedisLockUtils {

    /**
     * 值
     */
    private static final String LOCKING_VALUE = "locking";

    /**
     * Redis锁存储键名
     */
    private static final String LOCKING_KEY_PREFIX = "framework" + CacheConstants.CACHE_KEY_SEPARATOR + "locking" + CacheConstants.CACHE_KEY_SEPARATOR;

    /**
     * 获取锁
     * @param key 锁标识
     * @param timeout 超时时间
     * @param expiredTime 锁过期时间（防止死锁）
     * @return bool 如果获取锁成功则返回true，否则返回false
     */
    @SuppressWarnings("BusyWait")
    public static boolean lock(String key, int timeout, int expiredTime) {
        timeout = timeout * 1000;
        long currentTime = System.currentTimeMillis();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        while (System.currentTimeMillis() - currentTime <= timeout) {
            if (RedisUtils.setIfAbsent(LOCKING_KEY_PREFIX + key, LOCKING_VALUE, Duration.ofSeconds(expiredTime))) {
                // 获取锁成功
                return true;
            }
            // 获锁不成功
            try {
                Thread.sleep(20, random.nextInt(40));
            } catch (InterruptedException e) {
                log.error("获取不到锁，等待过程中，休眠出现异常：{}", e.getMessage());
                throw new RedisLockingUtilException("等待锁出现异常");
            }
        }
        return false;
    }

    /**
     * 取消锁
     * @param key 键
     */
    public static void unlock(String key) {
        RedisUtils.remove(LOCKING_KEY_PREFIX + key);
    }

}
