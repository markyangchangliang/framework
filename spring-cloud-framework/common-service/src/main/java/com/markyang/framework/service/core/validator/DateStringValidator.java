package com.markyang.framework.service.core.validator;


import com.markyang.framework.service.core.validator.annotation.DateString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * url验证器
 * @author yangchangliang
 */
public class DateStringValidator implements ConstraintValidator<DateString, String> {

	/**
	 * Initializes the validator in preparation for
	 * {@link #(Object, ConstraintValidatorContext)} calls.
	 * The constraint annotation for a given constraint declaration
	 * is passed.
	 * <p>
	 * This method is guaranteed to be called before any use of this instance for
	 * validation.
	 * <p>
	 * The default implementation is a no-op.
	 *
	 * @param constraintAnnotation annotation instance for a given constraint declaration
	 */
	@Override
	public void initialize(DateString constraintAnnotation) {
	}

	/**
	 * Implements the validation logic.
	 * The state of {@code value} must not be altered.
	 * <p>
	 * This method can be accessed concurrently, thread-safety must be ensured
	 * by the implementation.
	 *
	 * @param value   object to validate
	 * @param context context in which the constraint is evaluated
	 * @return {@code false} if {@code value} does not pass the constraint
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		return value.matches("(^\\s*$)|(^[1-2][0-9][0-9][0-9]-[0-1]?[0-9]-[0-3]?[0-9]$)");
	}

}
