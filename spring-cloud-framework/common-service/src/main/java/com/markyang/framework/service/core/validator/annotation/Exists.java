package com.markyang.framework.service.core.validator.annotation;


import com.markyang.framework.service.core.validator.ExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 存在验证器注解
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/23 12:16 下午 星期一
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ExistsValidator.class)
public @interface Exists {

	String message() default "{notExist}";

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

}
