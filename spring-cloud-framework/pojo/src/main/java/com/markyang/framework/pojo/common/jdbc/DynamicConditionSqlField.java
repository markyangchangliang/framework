package com.markyang.framework.pojo.common.jdbc;

import java.lang.annotation.*;

/**
 * 动态Sql字段注解
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/10 4:31 下午 星期日
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DynamicConditionSqlField {

    /**
     * 条件字段名称
     * @return 字段名称
     */
    String name() default "";

    /**
     * 条件执行操作符
     * @return 操作符
     */
    String operator() default "=";

    /**
     * 值转换器
     * @return 枚举
     */
    SqlFieldValueTranslatorEnum translator() default SqlFieldValueTranslatorEnum.DEFAULT;
}
