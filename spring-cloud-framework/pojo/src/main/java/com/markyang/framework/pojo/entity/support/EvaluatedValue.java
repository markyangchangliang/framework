package com.markyang.framework.pojo.entity.support;

import java.lang.annotation.*;

/**
 * 解析值注解
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/4/1 3:57 下午 星期三
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EvaluatedValue {

    /**
     * 表达式
     *
     * @return 表达式
     */
    String value();
}
