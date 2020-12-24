package com.markyang.framework.pojo.entity.support;

import java.lang.annotation.*;

/**
 * Dto对象数据字典
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/29 6:08 下午 星期日
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DtoDictField {

    /**
     * 表名
     *
     * @return 表名
     */
    String tableName();

    /**
     * 字段名
     *
     * @return 字段名
     */
    String fieldName() default "";
}
