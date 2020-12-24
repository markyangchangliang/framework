package com.markyang.framework.service.core.service.support;

import com.markyang.framework.service.core.service.hook.ServiceHook;

/**
 * 服务类钩子接口适配器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/24 4:26 下午 星期二
 */
public class ServiceHookAdapter implements ServiceHook {

    /**
     * 查询之后
     *
     * @param object  实体类
     * @param context 钩子上下文对象
     */
    @Override
    public void postGet(Object object, HookContext context) {

    }

    /**
     * 添加之前
     *
     * @param object  实体类
     * @param context 钩子上下文对象
     */
    @Override
    public void preAdd(Object object, HookContext context) {

    }

    /**
     * 添加之后
     *
     * @param object  实体类
     * @param context 钩子上下文对象
     */
    @Override
    public void postAdd(Object object, HookContext context) {

    }

    /**
     * 更新之前
     *
     * @param object  实体类
     * @param context 钩子上下文对象
     */
    @Override
    public void preUpdate(Object object, HookContext context) {

    }

    /**
     * 更新之后
     *
     * @param object  实体类
     * @param context 钩子上下文对象
     */
    @Override
    public void postUpdate(Object object, HookContext context) {

    }

    /**
     * 删除之前
     *
     * @param object  实体类
     * @param context 钩子上下文对象
     */
    @Override
    public void preDelete(Object object, HookContext context) {

    }

    /**
     * 删除之后
     *
     * @param object  实体类
     * @param context 钩子上下文对象
     */
    @Override
    public void postDelete(Object object, HookContext context) {

    }
}
