package com.markyang.framework.pojo.common.jdbc;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

/**
 * 默认值转换器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/10 4:43 下午 星期日
 */
public class DefaultConditionSqlFieldValueTranslator implements ConditionSqlFieldValueTranslator {

    /**
     * 转换值
     *
     * @param target        目标对象
     * @param targetField   目标字段
     * @param operator      操作符
     * @param originalValue 原始值
     * @return 转换后的字段值描述符
     */
    @Override
    public Optional<ConditionSqlFieldValueDescriptor> translate(Class<?> target, Field targetField, String operator, Object originalValue) {
        // 😄哈哈最简单的实现
        if (Objects.isNull(originalValue)) {
            return Optional.empty();
        }
        if (originalValue instanceof CharSequence && StringUtils.isBlank((CharSequence) originalValue)) {
            return Optional.empty();
        }
        ConditionSqlFieldValueDescriptor descriptor = new ConditionSqlFieldValueDescriptor();
        descriptor.setValuePlaceholder("?");
        descriptor.addValue(originalValue);
        return Optional.of(descriptor);
    }
}
