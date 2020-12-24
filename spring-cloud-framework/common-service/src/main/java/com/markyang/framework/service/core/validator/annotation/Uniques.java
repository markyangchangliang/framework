package com.markyang.framework.service.core.validator.annotation;


import com.markyang.framework.service.core.validator.UniquesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 唯一验证器注解
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/23 12:16 下午 星期一
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UniquesValidator.class)
public @interface Uniques {

	String message() default "{fieldNames}{unique}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * 服务类在容器中的名字，一般可不用指定
	 * @return 名字
	 */
	String serviceName() default "";

	/**
	 * 实体服务类，用于检测指定主键对应的记录是否在数据库中存在
	 * @return 服务类
	 */
	Class<?> service();

	/**
	 * 唯一验证
	 * @return 验证列表
	 */
	Unique[] uniques();

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface Unique {

		/**
		 * 提示信息
		 * @return 信息
		 */
		String fieldNames();

		/**
		 * 判断方法
		 * @return 判断方法名
		 */
		String method();

		/**
		 * 唯一的字段
		 * @return 字段
		 */
		String[] fields();

	}

}
