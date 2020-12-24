package com.markyang.framework.service.core.controller.hook;

import com.markyang.framework.service.core.service.support.HookContext;

/**
 * 修改之后的处理逻辑
 * @author yangchangliang
 */
public interface PostUpdateHook {

	/**
	 * 预处理
	 * @param entity 实体对象
	 * @param form 表单对象
	 * @param context 钩子上下文对象
	 */
	void apply(Object entity, Object form, HookContext context);

}
