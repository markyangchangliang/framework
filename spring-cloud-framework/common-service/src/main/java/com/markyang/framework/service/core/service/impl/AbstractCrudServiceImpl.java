package com.markyang.framework.service.core.service.impl;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.markyang.framework.service.core.repository.FrameworkRepository;
import com.markyang.framework.service.core.service.CrudService;
import com.markyang.framework.service.core.service.hook.ServiceHook;
import com.markyang.framework.service.core.service.support.HookContext;
import com.markyang.framework.service.core.service.support.ServiceHookHolder;
import com.markyang.framework.service.exception.CrudServiceException;
import com.markyang.framework.pojo.constant.TableConstants;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import com.markyang.framework.pojo.entity.BaseEntity;
import com.markyang.framework.util.ConversionUtils;
import com.markyang.framework.util.ReflectionOperationUtils;
import com.markyang.framework.util.TypeCastUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 增删改查Service实现类
 * @author yangchangliang
 */
@Slf4j
public abstract class AbstractCrudServiceImpl<E extends BaseEntity, R extends FrameworkRepository<E>> extends ServiceImpl<R, E> implements CrudService<E> {

    /**
     * 仓库实现注入
     */
    @Autowired
    private R repository;

    @Override
    protected Class<E> currentModelClass() {
        return TypeCastUtils.cast(ReflectionKit.getSuperClassGenericType(getClass(), 0));
    }

    /**
     * 根据ID获取记录
     *
     * @param id 记录ID
     * @return 实体对象
     */
    @Override
    public Optional<E> get(Serializable id) {
        Assert.notNull(id, "需要查找记录的主键不能为空");
        Optional<E> entityOptional = Optional.ofNullable(this.getById(id));
        // 应用钩子
        entityOptional.ifPresent(this::applyPostGetHook);
        return entityOptional;
    }

    /**
     * 根据ID判断记录是否存在
     *
     * @param id 记录ID
     * @return bool
     */
    @Override
    public boolean exists(Serializable id) {
        Assert.notNull(id, "需要判断是否存在的记录的主键不能为空");
        return BooleanUtils.isTrue(this.repository.existsById(id));
    }

    /**
     * 根据ID判断记录是否存在
     *
     * @param queryWrapper 条件构造器
     * @return bool
     */
    @Override
    public boolean exists(QueryWrapper<E> queryWrapper) {
        return BooleanUtils.isTrue(this.repository.exists(queryWrapper));
    }

    /**
     * 添加多个实体
     *
     * @param entities 实体对象
     * @return 影响行数
     */
    @Override
    public int batchInsertAllSomeColumn(List<E> entities) {
        // 解析代理对象
        AbstractCrudServiceImpl<E, R> proxy = this.resolveProxy();
        HookContext context = HookContext.of();
        for (E entity : entities) {
            // 应用钩子
            for (ServiceHook serviceHook : ServiceHookHolder.serviceHooks) {
                serviceHook.preAdd(entity, context);
            }
            // 应用自定义钩子实现
            proxy.beforeAdd(entity);
        }
        return this.repository.insertBatchSomeColumn(entities);
    }

    /**
     * 应用查询后处理钩子
     * @param entity 实体对象
     */
    protected void applyPostGetHook(E entity) {
        this.applyPostGetHook(entity, null);
    }

    /**
     * 应用查询后处理钩子
     * @param entity 实体对象
     * @param context 上下文对象
     */
    protected void applyPostGetHook(E entity, HookContext context) {
        context = Objects.isNull(context) ? HookContext.of() : context;
        for (ServiceHook serviceHook : ServiceHookHolder.serviceHooks) {
            serviceHook.postGet(entity, context);
        }
        // 自定义处理钩子
        this.afterGet(entity);
    }

    /**
     * 应用查询后处理钩子
     * @param entities 实体列表
     */
    protected void applyPostGetHook(List<E> entities) {
        // 应用钩子
        HookContext context = HookContext.of();
        for (E entity : entities) {
            this.applyPostGetHook(entity, context);
        }
    }

    /**
     * 获取所有记录
     *
     * @return 实体对象List
     */
    @Override
    public List<E> getList() {
        List<E> entities = this.list();
        this.applyPostGetHook(entities);
        return entities;
    }

    /**
     * 根据多个ID获取多条记录
     *
     * @param ids 记录ID集合
     * @return 记录List
     */
    @Override
    public List<E> getList(List<Serializable> ids) {
        List<E> entities = this.listByIds(ids);
        // 应用钩子
        this.applyPostGetHook(entities);
        return entities;
    }

    /**
     * 设置查询Wrapper排序字段
     * @param wrapper 查询Wrapper
     * @param orderItems 排序字段
     * @return 查询Wrapper原样返回
     */
    protected QueryWrapper<E> setOrderItems(QueryWrapper<E> wrapper, OrderItem... orderItems) {
        for (OrderItem orderItem : orderItems) {
            wrapper.orderBy(true, orderItem.isAsc(), orderItem.getColumn());
        }
        return wrapper;
    }

    /**
     * 根据排序获取所有记录
     *
     * @param orderItems 排序对象
     * @return 实体对象List
     */
    @Override
    public List<E> getList(OrderItem... orderItems) {
        List<E> entities = this.list(this.setOrderItems(Wrappers.query(), orderItems));
        // 应用钩子
        this.applyPostGetHook(entities);
        return entities;
    }

    /**
     * 根据操作用户ID获取所有相关记录
     *
     * @param operationUserId 操作用户ID
     * @return 实体对象List
     */
    @Override
    public List<E> getList(Serializable operationUserId) {
        Assert.notNull(operationUserId, "操作用户ID不能为空");
        List<E> entities = this.list(Wrappers.<E>query().eq(ConversionUtils.camelToDash(TableConstants.OPERATION_USER_ID_FIELD_NAME), operationUserId));
        // 应用钩子
        this.applyPostGetHook(entities);
        return entities;
    }

    /**
     * 根据操作用户ID和排序对象获取所有相关记录
     *
     * @param operationUserId 操作用户ID
     * @param orderItems            排序对象
     * @return 实体对象List
     */
    @Override
    public List<E> getList(Serializable operationUserId, OrderItem... orderItems) {
        Assert.notNull(operationUserId, "操作用户ID不能为空");
        List<E> entities = this.list(this.setOrderItems(Wrappers.query(), orderItems).eq(ConversionUtils.camelToDash(TableConstants.OPERATION_USER_ID_FIELD_NAME), operationUserId));
        // 应用钩子
        this.applyPostGetHook(entities);
        return entities;
    }

    /**
     * 根据机构ID获取所有相关记录
     *
     * @param orgId 机构ID
     * @return 实体对象List
     */
    @Override
    public List<E> getListOfOrg(Serializable orgId) {
        Assert.notNull(orgId, "机构ID不能为空");
        List<E> entities = this.list(Wrappers.<E>query().eq(ConversionUtils.camelToDash(TableConstants.ORG_ID_FIELD_NAME), orgId));
        // 应用钩子
        this.applyPostGetHook(entities);
        return entities;
    }

    /**
     * 根据业务ID和排序对象获取相关记录
     *
     * @param orgId 机构ID
     * @param orderItems       排序对象
     * @return 实体对象List
     */
    @Override
    public List<E> getListOfOrg(Serializable orgId, OrderItem... orderItems) {
        Assert.notNull(orgId, "机构ID不能为空");
        List<E> entities = this.list(this.setOrderItems(Wrappers.query(), orderItems).eq(ConversionUtils.camelToDash(TableConstants.ORG_ID_FIELD_NAME), orgId));
        // 应用钩子
        this.applyPostGetHook(entities);
        return entities;
    }

    /**
     *代理对象
     * @return 代理对象
     */
    protected AbstractCrudServiceImpl<E, R> resolveProxy() {
        return TypeCastUtils.cast(AopContext.currentProxy());
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return 添加后的实体对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public E add(E entity) {
        Assert.notNull(entity, "需要添加的实体对象不能为空");
        // 应用钩子
        HookContext context = HookContext.of();
        for (ServiceHook serviceHook : ServiceHookHolder.serviceHooks) {
            serviceHook.preAdd(entity, context);
        }
        // 解析代理对象
        AbstractCrudServiceImpl<E, R> proxy = this.resolveProxy();
        proxy.beforeAdd(entity);
        boolean result = this.save(entity);
        if (!result) {
            log.error("实体[{}]，添加失败", entity);
            throw new CrudServiceException("实体[" + entity.getClass().getName() + "]记录添加失败");
        }
        // 应用钩子
        for (ServiceHook serviceHook : ServiceHookHolder.serviceHooks) {
            serviceHook.postAdd(entity, context);
        }
        proxy.afterAdd(entity);
        return entity;
    }

    /**
     * 添加多条记录
     *
     * @param entities 实体对象集合
     * @return 添加后的实体对象集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<E> addAll(List<E> entities) {
        Assert.isTrue(CollectionUtils.isNotEmpty(entities), "需要添加的实体迭代对象不能为空");
        // 解析代理对象
        AbstractCrudServiceImpl<E, R> proxy = this.resolveProxy();
        HookContext context = HookContext.of();
        for (E entity : entities) {
            // 应用钩子
            for (ServiceHook serviceHook : ServiceHookHolder.serviceHooks) {
                serviceHook.preAdd(entity, context);
            }
            // 应用自定义钩子实现
            proxy.beforeAdd(entity);
        }
        boolean result = this.saveBatch(entities);
        if (!result) {
            throw new CrudServiceException("实体[" + entities.get(0).getClass().getName() + "]记录批量添加失败");
        }
        for (E entity : entities) {
            // 应用钩子
            for (ServiceHook serviceHook : ServiceHookHolder.serviceHooks) {
                serviceHook.postAdd(entity, context);
            }
            // 应用自定义钩子实现
            proxy.afterAdd(entity);
        }
        return entities;
    }

    /**
     * 更新记录
     *
     * @param entity 实体对象
     * @return 更新后的实体对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public E update(E entity) {
        Assert.notNull(entity, "需要更新的实体对象不能为空");
        // 应用钩子
        HookContext context = HookContext.of();
        for (ServiceHook serviceHook : ServiceHookHolder.serviceHooks) {
            serviceHook.preUpdate(entity, context);
        }
        // 解析代理对象
        AbstractCrudServiceImpl<E, R> proxy = this.resolveProxy();
        proxy.beforeUpdate(entity);
        boolean result = this.updateById(entity);
        if (!result) {
            log.error("实体[{}]，更新失败", entity);
            throw new CrudServiceException("实体[" + entity.getClass().getName() + "]记录更新失败");
        }
        // 应用钩子
        for (ServiceHook serviceHook : ServiceHookHolder.serviceHooks) {
            serviceHook.postUpdate(entity, context);
        }
        proxy.afterUpdate(entity);
        return entity;
    }

    /**
     * 更新多条记录
     *
     * @param entities 实体对象集合
     * @return 更新后的实体对象集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<E> updateAll(List<E> entities) {
        Assert.isTrue(CollectionUtils.isNotEmpty(entities), "需要更新的实体迭代对象不能为空");
        // 解析代理对象
        AbstractCrudServiceImpl<E, R> proxy = this.resolveProxy();
        HookContext context = HookContext.of();
        for (E entity : entities) {
            // 应用钩子
            for (ServiceHook serviceHook : ServiceHookHolder.serviceHooks) {
                serviceHook.preUpdate(entity, context);
            }
            // 应用自定义钩子实现
            proxy.beforeUpdate(entity);
        }
        boolean result = this.updateBatchById(entities);
        if (!result) {
            throw new CrudServiceException("实体[" + entities.get(0).getClass().getName() + "]记录批量更新失败");
        }
        for (E entity : entities) {
            // 应用钩子
            for (ServiceHook serviceHook : ServiceHookHolder.serviceHooks) {
                serviceHook.postUpdate(entity, context);
            }
            // 应用自定义钩子实现
            proxy.afterUpdate(entity);
        }
        return entities;
    }

    /**
     * 根据记录ID删除记录
     *
     * @param id 记录ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Serializable id) {
        Assert.notNull(id, "需要删除的记录主键不能为空");
        this.get(id).ifPresent(this::delete);
    }

    /**
     * 获取实体主键
     * @param entity 实体对象
     * @return 主键
     */
    private Serializable getEntityId(E entity) {
        if (entity instanceof AbstractBaseEntity) {
            return ((AbstractBaseEntity) entity).getId();
        }
        return TypeCastUtils.cast(ReflectionOperationUtils.getAnnotatedField(this.entityClass, TableId.class)
            .flatMap(field -> ReflectionOperationUtils.getFieldValue(field, entity))
            .orElseThrow(() -> new CrudServiceException("实体[" + this.entityClass.getName() + "]不存在主键字段")));
    }

    /**
     * 根据实体对象删除记录
     *
     * @param entity 实体对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(E entity) {
        Assert.notNull(entity, "需要删除记录的实体对象不能为空");
        // 应用钩子
        HookContext context = HookContext.of();
        for (ServiceHook serviceHook : ServiceHookHolder.serviceHooks) {
            serviceHook.preDelete(entity, context);
        }
        // 解析代理对象
        AbstractCrudServiceImpl<E, R> proxy = this.resolveProxy();
        proxy.beforeDelete(entity);
        boolean result = this.removeById(this.getEntityId(entity));
        if (!result) {
            log.warn("删除实体[{}]失败", entity);
        }
        // 应用钩子
        for (ServiceHook serviceHook : ServiceHookHolder.serviceHooks) {
            serviceHook.postDelete(entity, context);
        }
        proxy.afterDelete(entity);
    }

    /**
     * 根据ID删除所有记录
     *
     * @param ids 实体ID集合
     */
    @Override
    public void deleteAllByIds(List<Serializable> ids) {
        Assert.isTrue(CollectionUtils.isNotEmpty(ids), "需要删除记录的ID集合不能为空");
        List<E> entities = this.getList(ids);
        entities.removeIf(Objects::isNull);
        this.deleteAll(entities);
    }

    /**
     * 根据ID字符串删除所有记录
     *
     * @param ids ID字符串
     */
    @Override
    public void deleteAllByIds(String ids) {
        // 断言主键类型
        Assert.isTrue(StringUtils.isNotBlank(ids), "主键字符串不能为空");
        List<String> idList = Arrays.asList(StringUtils.split(ids, ","));
        this.deleteAllByIds(idList.parallelStream()
            .collect(Collectors.toList()));
    }

    /**
     * 删除所有指定实体对象的记录
     *
     * @param entities 实体对象集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<E> entities) {
        Assert.isTrue(CollectionUtils.isNotEmpty(entities), "需要删除的实体迭代对象不能为空");
        // 解析代理对象
        AbstractCrudServiceImpl<E, R> proxy = this.resolveProxy();
        HookContext context = HookContext.of();
        for (E entity : entities) {
            // 应用钩子
            for (ServiceHook serviceHook : ServiceHookHolder.serviceHooks) {
                serviceHook.preDelete(entity, context);
            }
            // 应用自定义钩子实现
            proxy.beforeDelete(entity);
        }
        boolean result = this.removeByIds(entities.parallelStream().map(this::getEntityId).collect(Collectors.toList()));
        if (!result) {
            log.warn("批量实体[{}]删除失败", entities.get(0).getClass().getName());
            log.warn("批量实体[{}]删除失败", StringUtils.join(entities.parallelStream().map(this::getEntityId).collect(Collectors.toList()), ","));
        }
        for (E entity : entities) {
            // 应用钩子
            for (ServiceHook serviceHook : ServiceHookHolder.serviceHooks) {
                serviceHook.postDelete(entity, context);
            }
            // 应用自定义钩子实现
            proxy.afterDelete(entity);
        }
    }

}
