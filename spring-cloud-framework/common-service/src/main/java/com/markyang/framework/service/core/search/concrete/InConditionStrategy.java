package com.markyang.framework.service.core.search.concrete;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markyang.framework.service.core.search.ConditionStrategy;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.util.ClassOperationUtils;
import com.markyang.framework.util.TypeCastUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * IN条件策略实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/26 3:38 下午 星期二
 */
@Component
public class InConditionStrategy implements ConditionStrategy {
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
        if (!ClassOperationUtils.isString(fieldValue.getClass()) || StringUtils.isBlank(TypeCastUtils.cast(fieldValue))) {
            return queryWrapper;
        }
        String[] tokens = StringUtils.split(TypeCastUtils.cast(fieldValue), FrameworkConstants.COMMA_SEPARATOR);
        return queryWrapper.in(ArrayUtils.isNotEmpty(tokens), fieldName, tokens);
    }
}
