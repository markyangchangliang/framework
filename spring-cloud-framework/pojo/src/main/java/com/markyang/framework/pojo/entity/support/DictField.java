package com.markyang.framework.pojo.entity.support;

import java.lang.annotation.*;

/**
 * 数据字典字段注解
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/28 1:35 下午 星期六
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictField {

    /**
     * 表名
     *
     * @return 表名
     */
    String tableName() default "";

    /**
     * 字段名
     *
     * @return 字段名
     */
    String fieldName() default "";
}
