package com.markyang.framework.service.core.search.concrete;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markyang.framework.service.core.search.ConditionStrategy;
import org.springframework.stereotype.Component;


/**
 * 大于等于条件策略类
 * @author yangchangliang
 */
@Component
public class GreaterThanOrEqualConditionStrategy implements ConditionStrategy {

    /**
     * 处理条件构建
     *
     * @param fieldName    字段名称
     * @param fieldValue   字段值
     * @param queryWrapper 条件构建器
     * @return 条件构建器
     */
    @Override
    public QueryWrapper<?> apply(String fieldName, Object fieldValue, QueryWrapper<?> queryWrapper) {
        return queryWrapper.ge(fieldValue instanceof Number, fieldName, fieldValue);
    }
}
