package com.markyang.framework.pojo.common.support;

import java.lang.annotation.*;

/**
 * 模板消息参数注解
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/26 11:27 上午 星期二
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TemplateMessageArgument {

    /**
     * 参数名称
     * @return 名称
     */
    String value() default "";
}
