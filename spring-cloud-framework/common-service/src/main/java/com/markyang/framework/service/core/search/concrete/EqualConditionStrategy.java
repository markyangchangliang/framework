package com.markyang.framework.service.core.search.concrete;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markyang.framework.service.core.search.ConditionStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 相等条件策略
 * @author yangchangliang
 */
@Component
public class EqualConditionStrategy implements ConditionStrategy {


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
        if (fieldValue instanceof CharSequence) {
            return queryWrapper.eq(StringUtils.isNotBlank((CharSequence) fieldValue), fieldName, fieldValue);
        }
        return queryWrapper.eq(Objects.nonNull(fieldValue), fieldName, fieldValue);
    }
}
