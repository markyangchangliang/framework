package com.markyang.framework.service.core.service;

import com.markyang.framework.service.core.service.support.CustomizationSearchCondition;
import com.markyang.framework.service.core.service.support.HookContext;
import com.markyang.framework.service.core.service.support.ServiceHookHolder;
import com.markyang.framework.pojo.entity.BaseEntity;
import com.markyang.framework.service.core.service.hook.ServiceHook;

import java.util.List;
import java.util.Objects;

/**
 * 框架服务类
 * @author yangchangliang
 */
public interface FrameworkService <E extends BaseEntity> {

    /**
     * 额外的搜索条件，自定义
     * @return 条件谓词List
     */
    default CustomizationSearchCondition<E> additionalSearchConditions() {
        return null;
    }

    /**
     * 获取实体之后的处理逻辑
     * @param entity 实体对象
     */
    default void afterGet(E entity) {
    }

    /**
     * 添加之前的处理逻辑
     * @param entity 实体对象
     */
    default void beforeAdd(E entity) {
    }

    /**
     * 添加之后的处理逻辑
     * @param entity 实体对象
     */
    default void afterAdd(E entity) {
    }

    /**
     * 修改之前的处理逻辑
     * @param entity 实体对象
     */
    default void beforeUpdate(E entity) {
    }

    /**
     * 修改之后的处理逻辑
     * @param entity 实体对象
     */
    default void afterUpdate(E entity) {
    }

    /**
     * 删除之前的处理逻辑
     * @param entity 实体对象
     */
    default void beforeDelete(E entity) {
    }

    /**
     * 删除之后的处理逻辑
     * @param entity 实体对象
     */
    default void afterDelete(E entity) {
    }

    /**
     * 应用钩子
     * @param object 对象
     * @param context 上下文对象
     */
    default void applyHooks(Object object, HookContext context) {
        context = Objects.isNull(context) ? HookContext.of() : context;
        for (ServiceHook serviceHook : ServiceHookHolder.serviceHooks) {
            serviceHook.postGet(object, context);
        }
    }

    /**
     * 应用钩子
     * @param objects 对象数组
     */
    default void applyHooks(List<?> objects) {
        HookContext context = HookContext.of();
        for (Object object : objects) {
            this.applyHooks(object, context);
        }
    }
}
