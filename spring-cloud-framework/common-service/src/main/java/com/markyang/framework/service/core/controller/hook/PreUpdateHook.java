package com.markyang.framework.service.core.controller.hook;

import com.markyang.framework.service.core.service.support.HookContext;

/**
 * 修改前的处理钩子接口
 * @author yangchangliang
 */
@FunctionalInterface
public interface PreUpdateHook {

	/**
	 * 预处理
	 * @param entity 实体对象
	 * @param form 表单对象
	 * @param context 钩子上下文对象
	 */
	void apply(Object entity, Object form, HookContext context);

}
