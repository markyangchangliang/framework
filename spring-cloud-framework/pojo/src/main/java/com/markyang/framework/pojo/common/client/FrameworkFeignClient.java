package com.markyang.framework.pojo.common.client;

import java.lang.annotation.*;

/**
 * 框架FeignClient标注注解
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/17 5:33 下午 星期日
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FrameworkFeignClient {

    /**
     * 请求基础URL
     * @return 基础URL
     */
    String baseUrl() default "";

    /**
     * Target 类
     * @return Target
     */
    Class<? extends feign.Target> targetClass() default feign.Target.EmptyTarget.class;

    /**
     * 失败回调类
     * @return fallback
     */
    Class<?> fallbackClass() default Void.class;
}
