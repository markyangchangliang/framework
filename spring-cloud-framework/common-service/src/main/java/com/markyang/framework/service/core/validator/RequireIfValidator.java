package com.markyang.framework.service.core.validator;

import com.markyang.framework.service.core.validator.annotation.RequireIf;
import com.markyang.framework.util.ReflectionOperationUtils;
import com.markyang.framework.util.ValidationUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * 条件必须验证器
 * @author yangchangliang
 */
public class RequireIfValidator implements ConstraintValidator<RequireIf, Object> {

	private RequireIf.If[] judgements;

	/**
	 * Initializes the validator in preparation for
	 * {@link #isValid(Object, ConstraintValidatorContext)} calls.
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
	public void initialize(RequireIf constraintAnnotation) {
		this.judgements = constraintAnnotation.conditions();
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
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		for (RequireIf.If judgement : this.judgements) {
			Field fieldField = ReflectionOperationUtils.getField(value.getClass(), judgement.field());
			if (fieldField == null) {
				return false;
			}
			Object fieldValue = ReflectionOperationUtils.getFieldValue(fieldField, value);
			if (!judgement.multi()) {
				if (this.require(judgement.required(), judgement.requiredValue(), value) && ValidationUtils.isNil(fieldValue)) {
					// 不通过
					HibernateConstraintValidatorContext validatorContext = context.unwrap(HibernateConstraintValidatorContext.class);
					validatorContext.addMessageParameter("fieldName", judgement.fieldName());
					return false;
				}
			} else {
				// 多条件判断
				RequireIf.Assert[] conditions = judgement.asserts();
				if (Arrays.stream(conditions).parallel().allMatch(condition -> this.require(condition.required(), condition.requiredValue(), value)) && ValidationUtils.isNil(fieldValue)) {
					// 不通过
					HibernateConstraintValidatorContext validatorContext = context.unwrap(HibernateConstraintValidatorContext.class);
					validatorContext.addMessageParameter("fieldName", judgement.fieldName());
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 判断某一个条件是否得出需要
	 * @param required 依赖的字段
	 * @param requiredValue 依赖字段的值
	 * @param value 实体对象
	 * @return bool
	 */
	private boolean require(String required, String[] requiredValue, Object value) {
		Field requiredField = ReflectionUtils.findField(value.getClass(), required);
		if (requiredField == null) {
			return false;
		}
		Object assertValue = ReflectionOperationUtils.getFieldValue(requiredField, value);

		return Arrays.stream(requiredValue)
			.parallel()
			.anyMatch(val -> {
				boolean notRequire = (StringUtils.isBlank(val) && ValidationUtils.isNil(assertValue))
					|| (!StringUtils.isBlank(val) && !ValidationUtils.equals(assertValue, val));
				return !notRequire;
			});
	}

}
