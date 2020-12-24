package com.markyang.framework.pojo.common.support;

import java.lang.annotation.*;

/**
 * 模板消息数据对象注解
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/26 11:26 上午 星期二
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TemplateMessageArgumentsData {

    /**
     * 描述
     * @return 描述
     */
    String value() default "";
}
