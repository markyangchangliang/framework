package com.markyang.framework.app.base.annotation;

import java.lang.annotation.*;

/**
 * 缓存名称注解
 *
 * @author yangchangliang
 * @version 1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheName {

    /**
     * 缓存键值
     * @return key
     */
    String value();
}
