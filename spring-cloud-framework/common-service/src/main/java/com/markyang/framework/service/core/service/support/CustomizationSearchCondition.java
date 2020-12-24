package com.markyang.framework.service.core.service.support;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 定制化的搜索条件
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/13 10:08 上午 星期三
 */
@FunctionalInterface
public interface CustomizationSearchCondition<E> {

    /**
     * 自定义条件
     * @param queryWrapper 条件包装器
     */
    void customize(QueryWrapper<E> queryWrapper);
}
