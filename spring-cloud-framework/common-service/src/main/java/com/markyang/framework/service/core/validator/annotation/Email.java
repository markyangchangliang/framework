package com.markyang.framework.service.core.validator.annotation;


import com.markyang.framework.service.core.validator.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 邮件验证器注解
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/23 12:16 下午 星期一
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EmailValidator.class)
public @interface Email {

	String message() default "{email}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
