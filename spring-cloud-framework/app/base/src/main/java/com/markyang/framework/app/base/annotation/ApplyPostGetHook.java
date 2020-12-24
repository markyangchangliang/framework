package com.markyang.framework.app.base.annotation;

import com.markyang.framework.service.core.service.hook.ServiceHook;

import java.lang.annotation.*;

/**
 * 应用数据查询后置钩子注解
 *
 * @author yangchangliang
 * @version 1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApplyPostGetHook {
    /**
     * 包含哪些钩子（注意，这个属性优先，将忽略另一个属性）
     * @return 钩子类数组
     */
    Class<ServiceHook>[] includes() default {};

    /**
     * 排除哪些钩子
     * @return 钩子类数组
     */
    Class<ServiceHook>[] excludes() default {};
}
