package com.markyang.framework.pojo.common.jdbc;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * 查询sql字段值转换器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/10 4:41 下午 星期日
 */
public interface ConditionSqlFieldValueTranslator {

    /**
     * 转换值
     * @param target 目标对象
     * @param targetField 目标字段
     * @param operator 操作符
     * @param originalValue 原始值
     * @return 转换后的字段值描述符
     */
    Optional<ConditionSqlFieldValueDescriptor> translate(Class<?> target, Field targetField, String operator, Object originalValue);
}
