package com.markyang.framework.service.core.validator.group;

import javax.validation.groups.Default;

/**
 * 框架验证组
 * @author yangchangliang
 */
public interface FrameworkGroups {

	/**
	 * 创建组
	 */
	interface Add extends Default {}

	/**
	 * 修改组
	 */
	interface Update extends Default {}

}
