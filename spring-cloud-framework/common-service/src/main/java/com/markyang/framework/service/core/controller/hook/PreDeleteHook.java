package com.markyang.framework.service.core.controller.hook;

import com.markyang.framework.service.core.service.support.HookContext;

/**
 * 删除之前的处理逻辑
 * @author yangchangliang
 */
public interface PreDeleteHook {

	/**
	 * 预处理
	 * @param entity 实体对象
	 * @param context 钩子上下文对象
	 */
	void apply(Object entity, HookContext context);

}
