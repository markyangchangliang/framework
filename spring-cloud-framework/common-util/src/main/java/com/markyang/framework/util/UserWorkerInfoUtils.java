package com.markyang.framework.util;

import com.markyang.framework.pojo.constant.CacheConstants;
import com.markyang.framework.pojo.dto.system.UserWorkerInfoDto;

import java.util.Optional;

/**
 * 用户职员信息工具类
 *
 * @author yangchangliang
 * @version 1
 */
public final class UserWorkerInfoUtils {

    /**
     * UserWorkerInfo缓存键
     */
    public static final String USER_WORKER_INFO_CACHE_KEY = CacheConstants.CACHE_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR + CacheConstants.COMMON_INFO_CACHE_KEY_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR + "userWorkerInfo" + CacheConstants.CACHE_KEY_SEPARATOR;

    /**
     * 获取用户职员信息
     * @param userId 用户ID
     * @return 用户职员信息Optional对象
     */
    public static Optional<UserWorkerInfoDto> getUserWorkerInfoByUserId(String userId) {
        return Optional.ofNullable(RedisUtils.get(USER_WORKER_INFO_CACHE_KEY + "userId" + CacheConstants.CACHE_KEY_SEPARATOR + userId));
    }

    /**
     * 获取用户职员信息
     * @param workerId 职员ID
     * @return 用户职员信息Optional对象
     */
    public static Optional<UserWorkerInfoDto> getUserWorkerInfoByWorkerId(String workerId) {
        return Optional.ofNullable(RedisUtils.get(USER_WORKER_INFO_CACHE_KEY + "workerId" + CacheConstants.CACHE_KEY_SEPARATOR + workerId));
    }

    /**
     * 获取用户职员信息
     * @param orgId 机构ID
     * @param orgUserId HIS职员ID
     * @return 用户职员信息Optional对象
     */
    public static Optional<UserWorkerInfoDto> getUserWorkerInfoByHisWorkerId(String orgId, String orgUserId) {
        return Optional.ofNullable(RedisUtils.get(USER_WORKER_INFO_CACHE_KEY + "hisUserId" + CacheConstants.CACHE_KEY_SEPARATOR + orgId + "-" + orgUserId));
    }
}
