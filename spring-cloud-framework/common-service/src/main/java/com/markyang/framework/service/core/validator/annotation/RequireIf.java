package com.markyang.framework.service.core.validator.annotation;


import com.markyang.framework.service.core.validator.RequireIfValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 条件必须验证器注解
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/23 12:16 下午 星期一
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = RequireIfValidator.class)
public @interface RequireIf {

	String message() default "{fieldName}{requireIf}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * 所有的断定
	 * @return If数组
	 */
	If[] conditions();

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface If {

		/**
		 * 提示名称
		 * @return 字段名
		 */
		String fieldName();

		/**
		 * 字段
		 * @return 字段
		 */
		String field();

		/**
		 * 前提字段
		 * @return 字段
		 */
		String required() default "";

		/**
		 * 需要等于某一个值
		 * @return 值
		 */
		String[] requiredValue() default "";

		/**
		 * 是否多条件必须
		 * @return bool
		 */
		boolean multi() default false;

		/**
		 * 条件
		 * @return 数组
		 */
		Assert[] asserts() default {};

	}

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface Assert {

		/**
		 * 前提字段
		 * @return 字段
		 */
		String required();

		/**
		 * 需要等于某一个值
		 * @return 值
		 */
		String[] requiredValue() default "";

	}

}
