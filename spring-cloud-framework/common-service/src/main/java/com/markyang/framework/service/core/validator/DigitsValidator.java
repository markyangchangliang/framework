package com.markyang.framework.service.core.validator;


import com.markyang.framework.service.core.validator.annotation.Digits;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 数字验证器
 * @author yangchangliang
 */
public class DigitsValidator implements ConstraintValidator<Digits, String> {

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
	public void initialize(Digits constraintAnnotation) {
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
		return value.matches("(^\\s*$)|^([0-9]*)$");
	}

}
