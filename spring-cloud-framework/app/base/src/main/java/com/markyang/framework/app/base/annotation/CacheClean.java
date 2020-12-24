package com.markyang.framework.app.base.annotation;

import java.lang.annotation.*;

/**
 * 缓存清除注解
 *
 * @author yangchangliang
 * @version 1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheClean {

    /**
     * 清除的缓存命名空间
     * @return 命名空间
     */
    String[] cacheNames();

    /**
     * 抛出异常是否继续清除缓存
     * @return bool 默认抛出异常放弃清除缓存
     */
    boolean continueCleanIfThrowException() default false;

    /**
     * 满足表单式时则不进行清除缓存操作
     * @return 表达式
     */
    String cleanUnless() default "";
}
