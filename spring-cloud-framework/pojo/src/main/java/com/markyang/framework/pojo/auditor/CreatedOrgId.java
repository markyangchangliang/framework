package com.markyang.framework.pojo.auditor;

import java.lang.annotation.*;

/**
 * 创建时的机构ID自动注入注解
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/4/24 6:48 下午 星期五
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CreatedOrgId {
}
