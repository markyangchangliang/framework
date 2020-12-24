package com.markyang.framework.service.core.search.concrete;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markyang.framework.service.core.search.ConditionStrategy;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 不相等条件策略类
 * @author yangchangliang
 */
@Component
public class NotEqualConditionStrategy implements ConditionStrategy {


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
        return queryWrapper.ne(Objects.nonNull(fieldValue), fieldName, fieldValue);
    }
}
