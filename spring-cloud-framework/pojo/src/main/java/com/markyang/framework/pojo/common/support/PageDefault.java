package com.markyang.framework.pojo.common.support;

import java.lang.annotation.*;

/**
 * 默认分页注解
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/13 8:38 下午 星期三
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PageDefault {

    /**
     * The default-size the injected {@link org.springframework.data.domain.Pageable} should get if no corresponding
     * parameter defined in request (default is 10).
     */
    int size() default 20;

    /**
     * The default-pagenumber the injected {@link org.springframework.data.domain.Pageable} should get if no corresponding
     * parameter defined in request (default is 0).
     */
    int page() default 1;

    /**
     * The properties to sort by by default. If unset, no sorting will be applied at all.
     *
     * @return 默认排序
     */
    String sort() default "";
}
