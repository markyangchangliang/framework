package com.markyang.framework.service.core.service.hook;

import com.markyang.framework.service.core.service.support.HookContext;

/**
 * 服务类钩子接口
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/24 4:21 下午 星期二
 */
public interface ServiceHook {

    /**
     * 查询之后
     * @param object 实体类
     * @param context 钩子上下文对象
     */
    void postGet(Object object, HookContext context);

    /**
     * 添加之前
     * @param object 实体类
     * @param context 钩子上下文对象
     */
    void preAdd(Object object, HookContext context);

    /**
     * 添加之后
     * @param object 实体类
     * @param context 钩子上下文对象
     */
    void postAdd(Object object, HookContext context);

    /**
     * 更新之前
     * @param object 实体类
     * @param context 钩子上下文对象
     */
    void preUpdate(Object object, HookContext context);

    /**
     * 更新之后
     * @param object 实体类
     * @param context 钩子上下文对象
     */
    void postUpdate(Object object, HookContext context);

    /**
     * 删除之前
     * @param object 实体类
     * @param context 钩子上下文对象
     */
    void preDelete(Object object, HookContext context);

    /**
     * 删除之后
     * @param object 实体类
     * @param context 钩子上下文对象
     */
    void postDelete(Object object, HookContext context);
}
