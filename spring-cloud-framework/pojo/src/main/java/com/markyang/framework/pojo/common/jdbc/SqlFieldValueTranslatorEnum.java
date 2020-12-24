package com.markyang.framework.pojo.common.jdbc;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 字段值转换枚举类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/10 4:52 下午 星期日
 */
@AllArgsConstructor
@Getter
public enum SqlFieldValueTranslatorEnum {

    /**
     * 默认字段值转换器
     */
    DEFAULT(new DefaultConditionSqlFieldValueTranslator());

    private final ConditionSqlFieldValueTranslator translator;
}
