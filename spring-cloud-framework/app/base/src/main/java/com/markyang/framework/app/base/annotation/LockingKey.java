package com.markyang.framework.app.base.annotation;

import java.lang.annotation.*;

/**
 * 锁定目标
 * @author yangchangliang
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LockingKey {

    /**
     * 字段名称（当指定键为对象时有用）
     * @return 字段名称
     */
    String[] fields() default {};

    /**
     * 方法名称（当指定键为对象时有用）
     * @return 方法名称
     */
    String[] methods() default {};

}
