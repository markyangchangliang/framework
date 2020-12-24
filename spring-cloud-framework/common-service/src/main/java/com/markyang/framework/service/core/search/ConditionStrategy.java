package com.markyang.framework.service.core.search;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 处理搜索表单时的条件策略接口
 * @author yangchangliang
 */
public interface ConditionStrategy {

    /**
     * 处理条件构建
     * @param fieldName 字段名称
     * @param fieldValue 字段值
     * @param queryWrapper 条件构建器
     * @return 条件构建器
     */
    QueryWrapper<?> apply(String fieldName, Object fieldValue, QueryWrapper<?> queryWrapper);

}
