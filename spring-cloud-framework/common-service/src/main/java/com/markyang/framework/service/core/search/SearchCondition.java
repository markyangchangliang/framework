package com.markyang.framework.service.core.search;


import java.lang.annotation.*;

/**
 * 搜索条件注解
 * @author yangchangliang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SearchCondition {

    /**
     * 条件搜索策略 默认等于
     * @return 策略
     */
    ConditionEnum strategy() default ConditionEnum.EQUAL;

}
