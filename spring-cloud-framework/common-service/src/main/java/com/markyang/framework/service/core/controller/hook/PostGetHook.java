package com.markyang.framework.service.core.controller.hook;

import com.markyang.framework.service.core.service.support.HookContext;

/**
 * 获取单条数据之后的处理逻辑
 * @author yangchangliang
 */
public interface PostGetHook {

	/**
	 * 预处理
	 * @param entity 实体对象
	 * @param context 钩子上下文对象
	 */
	void apply(Object entity, HookContext context);

}
