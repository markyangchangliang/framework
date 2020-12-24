package com.markyang.framework.util;

import com.markyang.framework.pojo.constant.CacheConstants;
import com.markyang.framework.pojo.dto.system.DeptInfoDto;

import java.util.Optional;

/**
 * 部门信息工具类
 *
 * @author yangchangliang
 * @version 1
 */
public final class DeptInfoUtils {

    /**
     * DeptInfo缓存键
     */
    public static final String DEPT_INFO_CACHE_KEY = CacheConstants.CACHE_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR + CacheConstants.COMMON_INFO_CACHE_KEY_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR + "deptInfo" + CacheConstants.CACHE_KEY_SEPARATOR;

    /**
     * 获取部门信息
     * @param deptId 部门ID
     * @return 部门信息Optional对象
     */
    public static Optional<DeptInfoDto> getDeptInfo(String deptId) {
        return Optional.ofNullable(RedisUtils.get(DEPT_INFO_CACHE_KEY + deptId));
    }
}
