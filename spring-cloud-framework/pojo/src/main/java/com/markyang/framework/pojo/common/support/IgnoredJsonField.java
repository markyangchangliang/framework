package com.markyang.framework.pojo.common.support;

import java.lang.annotation.*;

/**
 * Gson序列化时忽略字典注解
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/4/22 9:19 上午 星期三
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoredJsonField {
}
