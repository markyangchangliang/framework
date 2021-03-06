package com.markyang.framework.service.core.validator.annotation;


import com.markyang.framework.service.core.validator.TimeStringValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * URL验证器注解
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/23 12:16 下午 星期一
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = TimeStringValidator.class)
public @interface TimeString {

	String message() default "{timeString}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
