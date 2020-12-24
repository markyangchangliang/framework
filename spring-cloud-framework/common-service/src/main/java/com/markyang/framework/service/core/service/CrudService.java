package com.markyang.framework.service.core.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.markyang.framework.pojo.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * 增删改查服务接口
 * @author yangchangliang
 */
public interface CrudService <E extends BaseEntity> extends FrameworkService<E>, IService<E> {

    /**
     * 根据ID获取记录
     * @param id 记录ID
     * @return 实体对象
     */
    Optional<E> get(Serializable id);

    /**
     * 根据ID判断记录是否存在
     * @param id 记录ID
     * @return bool
     */
    boolean exists(Serializable id);

    /**
     * 根据ID判断记录是否存在
     * @param queryWrapper 条件构造器
     * @return bool
     */
    boolean exists(QueryWrapper<E> queryWrapper);

    /**
     * 获取所有记录
     * @return 实体对象List
     */
    List<E> getList();

    /**
     * 根据多个ID获取多条记录
     * @param ids 记录ID集合
     * @return 记录List
     */
    List<E> getList(List<Serializable> ids);

    /**
     * 根据排序获取所有记录
     * @param orderItems 排序对象
     * @return 实体对象List
     */
    List<E> getList(OrderItem... orderItems);

    /**
     * 根据操作用户ID获取所有相关记录
     * @param operationUserId 操作用户ID
     * @return 实体对象List
     */
    List<E> getList(Serializable operationUserId);

    /**
     * 根据操作用户ID和排序对象获取所有相关记录
     * @param operationUserId 操作用户ID
     * @param orderItems 排序对象
     * @return 实体对象List
     */
    List<E> getList(Serializable operationUserId, OrderItem... orderItems);

    /**
     * 根据机构ID获取所有相关记录
     * @param orgId 机构ID
     * @return 实体对象List
     */
    List<E> getListOfOrg(Serializable orgId);

    /**
     * 根据机构ID和排序对象获取相关记录
     * @param orgId 机构ID
     * @param orderItems 排序对象
     * @return 实体对象List
     */
    List<E> getListOfOrg(Serializable orgId, OrderItem... orderItems);

    /**
     * 创建空的或者带有默认值的实体对象
     * @return 实体对象
     */
    E create();

    /**
     * 添加记录
     * @param entity 实体对象
     * @return 添加后的实体对象
     */
    E add(E entity);

    /**
     * 添加多条记录
     * @param entities 实体对象集合
     * @return 添加后的实体对象集合
     */
    List<E> addAll(List<E> entities);

    /**
     * 添加多个实体
     * @param entities 实体对象
     * @return 影响行数
     */
    int batchInsertAllSomeColumn(List<E> entities);

    /**
     * 更新记录
     * @param entity 实体对象
     * @return 更新后的实体对象
     */
    E update(E entity);

    /**
     * 更新多条记录
     * @param entities 实体对象集合
     * @return 更新后的实体对象集合
     */
    List<E> updateAll(List<E> entities);

    /**
     * 根据记录ID删除记录
     * @param id 记录ID
     */
    void deleteById(Serializable id);

    /**
     * 根据实体对象删除记录
     * @param entity 实体对象
     */
    void delete(E entity);

    /**
     * 根据ID删除所有记录
     * @param ids 实体ID集合
     */
    void deleteAllByIds(List<Serializable> ids);

    /**
     * 根据ID字符串删除所有记录
     * @param ids ID字符串
     */
    void deleteAllByIds(String ids);

    /**
     * 删除所有指定实体对象的记录
     * @param entities 实体对象集合
     */
    void deleteAll(List<E> entities);

}
