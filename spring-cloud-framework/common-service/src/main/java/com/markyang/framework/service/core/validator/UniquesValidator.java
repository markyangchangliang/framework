package com.markyang.framework.service.core.validator;

import com.google.common.collect.Lists;
import com.markyang.framework.service.core.ReflectionCache;
import com.markyang.framework.service.core.validator.annotation.Uniques;
import com.markyang.framework.util.ReflectionOperationUtils;
import com.markyang.framework.util.ValidationUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 记录是否唯一验证器
 * @author yangchangliang
 */
public class UniquesValidator implements ConstraintValidator<Uniques, Object>, ApplicationContextAware {

	/**
	 * 缓存唯一字段信息
	 */
	private static final Map<Class<?>, List<Field>> CLASS_UNIQUE_FIELDS_CACHE = new ConcurrentReferenceHashMap<>(128);

	private Object serviceInstance;

	private Uniques.Unique[] uniques;

	private ApplicationContext applicationContext;

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
	public void initialize(Uniques constraintAnnotation) {
		this.uniques = constraintAnnotation.uniques();
		String serviceName = constraintAnnotation.serviceName();
		Class<?> service = constraintAnnotation.service();
		if (StringUtils.isNotBlank(serviceName)) {
			this.serviceInstance = this.applicationContext.getBean(serviceName, service);
		} else {
			this.serviceInstance = this.applicationContext.getBean(service);
		}
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
		for (Uniques.Unique unique : this.uniques) {
			boolean result = this.isUnique(this.serviceInstance.getClass(), unique.method(), unique.fields(), value);
			if (!result) {
				// 不通过
				HibernateConstraintValidatorContext validatorContext = context.unwrap(HibernateConstraintValidatorContext.class);
				validatorContext.addMessageParameter("fieldNames", unique.fieldNames());
				return false;
			}
		}
		return true;
	}

	/**
	 * 获取唯一字段
	 * @param clazz 类
	 * @param fields 字段名称
	 * @param value 表单对象
	 * @return 字段列表
	 */
	private List<Field> getClassUniqueFields(Class<?> clazz, String[] fields, Object value) {
		List<Field> fieldList;
		// 缓存获取唯一字段信息
		if (CLASS_UNIQUE_FIELDS_CACHE.containsKey(clazz)) {
			fieldList = CLASS_UNIQUE_FIELDS_CACHE.get(clazz);
		} else {
			// 查找Unique字段
			fieldList = Lists.newArrayList();
			for (String field : fields) {
				Field member = ReflectionUtils.findField(value.getClass(), field);
				if (member == null) {
					return Collections.emptyList();
				}
				fieldList.add(member);
			}
			CLASS_UNIQUE_FIELDS_CACHE.put(clazz, fieldList);
		}
		return fieldList;
	}

	private boolean isUnique(Class<?> serviceClass, String serviceMethod, String[] fields, Object value) {
		List<Field> fieldList = this.getClassUniqueFields(serviceClass, fields, value);
		if (fieldList.isEmpty()) {
			return false;
		}
		// 调用service的判断方法
		Method method;
		Optional<Method> cachedMethod = ReflectionCache.getCachedMethod(serviceClass, serviceMethod);
		if (cachedMethod.isPresent()) {
			method = cachedMethod.get();
		} else {
			method = ReflectionUtils.findMethod(serviceClass, serviceMethod, fieldList.parallelStream().map(Field::getType).toArray(Class<?>[]::new));
			if (Objects.isNull(method)) {
				// 找不到方法
				return false;
			}
		}
		// 调用方法
		Object result = ReflectionOperationUtils.invokeMethod(method, this.serviceInstance, fieldList.parallelStream().map(field -> ReflectionOperationUtils.getFieldValue(field, value)).toArray());
		if (Objects.isNull(result)) {
			return true;
		}
		Optional<Object> idOptional = ValidationUtils.getIdValue(value);
		if (!idOptional.isPresent()) {
			return false;
		}
		Optional<Object> entityIdOptional = ValidationUtils.getIdValue(result);
		return entityIdOptional.filter(entityId -> Objects.equals(idOptional.get(), entityId)).isPresent();
	}

	@Override
	public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
