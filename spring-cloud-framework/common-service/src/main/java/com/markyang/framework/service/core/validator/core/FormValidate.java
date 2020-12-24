package com.markyang.framework.service.core.validator.core;

import javax.validation.groups.Default;
import java.lang.annotation.*;

/**
 * 验证注解标识
 * @author yangchangliang
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FormValidate {

	/**
	 * 验证组
	 * @return 组标识类型
	 */
	Class<?>[] value() default Default.class;

}
