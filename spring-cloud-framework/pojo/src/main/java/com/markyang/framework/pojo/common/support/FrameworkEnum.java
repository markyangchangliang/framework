package com.markyang.framework.pojo.common.support;

import java.util.Optional;

/**
 * 定义规范枚举类
 * @author yangchangliang
 */
public interface FrameworkEnum {

	/**
	 * 获取枚举值
	 * @return 枚举值
	 */
	String getValue();

	/**
	 * 获取指定值枚举项
	 * @param clazz 枚举类
	 * @param value 枚举值
	 * @return 枚举项Optional对象
	 */
	static<T> Optional<T> getEnum(Class<T> clazz, String value) {
		if (clazz.isEnum() && FrameworkEnum.class.isAssignableFrom(clazz)) {
			T[] constants = clazz.getEnumConstants();
			for (T constant : constants) {
				FrameworkEnum frameworkEnum = (FrameworkEnum) constant;
				if (frameworkEnum.getValue().equalsIgnoreCase(value)) {
					return Optional.of(constant);
				}
			}
		}
		// 找不到或者不是枚举类，则返回null
		return Optional.empty();
	}

}
