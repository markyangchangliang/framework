package com.markyang.framework.service.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.markyang.framework.service.core.form.SearchBaseForm;
import com.markyang.framework.service.core.service.support.CustomizationSearchCondition;
import com.markyang.framework.pojo.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索服务接口类
 * @author yangchangliang
 */
public interface SearchableService <E extends BaseEntity, S extends SearchBaseForm> extends PageableService<E> {

    /**
     * 根据搜索获取一页数据
     * @param searchForm 搜索条件表单
     * @param page 分页和排序对象
     * @return 一页数据
     */
    IPage<E> getSearchedPage(S searchForm, IPage<E> page);

    /**
     * 根据操作用户和搜索获取一页数据
     * @param searchForm 搜索条件表单
     * @param page 分页和排序对象
     * @param operationUserId 操作用户ID
     * @return 一页数据
     */
    IPage<E> getSearchedPage(S searchForm, IPage<E> page, Serializable operationUserId);

    /**
     * 根据机构ID和搜索获取一页数据
     * @param orgId 机构ID
     * @param searchForm 搜索条件表单
     * @param page 分页和排序对象
     * @return 一页数据
     */
    IPage<E> getSearchedPageOfOrg(S searchForm, IPage<E> page, Serializable orgId);

    /**
     * 根据搜索获取一页数据
     * @param page 分页和排序对象
     * @param customizationSearchCondition 额外搜索条件自定义器
     * @return 一页数据
     */
    IPage<E> getSearchedPage(IPage<E> page, CustomizationSearchCondition<E> customizationSearchCondition);

    /**
     * 根据操作用户和搜索获取一页数据
     * @param page 分页和排序对象
     * @param operationUserId 操作用户ID
     * @param customizationSearchCondition 额外搜索条件自定义器
     * @return 一页数据
     */
    IPage<E> getSearchedPage(IPage<E> page, Serializable operationUserId, CustomizationSearchCondition<E> customizationSearchCondition);

    /**
     * 根据机构ID和搜索获取一页数据
     * @param orgId 机构ID
     * @param page 分页和排序对象
     * @param customizationSearchCondition 额外搜索条件自定义器
     * @return 一页数据
     */
    IPage<E> getSearchedPageOfOrg(IPage<E> page, Serializable orgId, CustomizationSearchCondition<E> customizationSearchCondition);

    /**
     * 根据搜索获取一页数据
     * @param searchForm 搜索条件表单
     * @param page 分页和排序对象
     * @param customizationSearchCondition 额外搜索条件自定义器
     * @return 一页数据
     */
    IPage<E> getSearchedPage(S searchForm, IPage<E> page, CustomizationSearchCondition<E> customizationSearchCondition);

    /**
     * 根据操作用户和搜索获取一页数据
     * @param searchForm 搜索条件表单
     * @param page 分页和排序对象
     * @param operationUserId 操作用户ID
     * @param customizationSearchCondition 额外搜索条件自定义器
     * @return 一页数据
     */
    IPage<E> getSearchedPage(S searchForm, IPage<E> page, Serializable operationUserId, CustomizationSearchCondition<E> customizationSearchCondition);

    /**
     * 根据机构ID和搜索获取一页数据
     * @param orgId 机构ID
     * @param searchForm 搜索条件表单
     * @param page 分页和排序对象
     * @param customizationSearchCondition 额外搜索条件自定义器
     * @return 一页数据
     */
    IPage<E> getSearchedPageOfOrg(S searchForm, IPage<E> page, Serializable orgId, CustomizationSearchCondition<E> customizationSearchCondition);

    /**
     * 根据机构ID和搜索获取一页数据
     * @param searchForm 搜索条件表单
     * @return 一页数据
     */
    List<E> getSearchedList(S searchForm);

    /**
     * 根据机构ID和搜索获取一页数据
     * @param searchForm 搜索条件表单
     * @param orderItems 排序对象
     * @return 一页数据
     */
    List<E> getSearchedList(S searchForm, OrderItem... orderItems);

    /**
     * 根据搜索获取一页数据
     * @param customizationSearchCondition 额外搜索条件自定义器
     * @return 一页数据
     */
    List<E> getSearchedList(CustomizationSearchCondition<E> customizationSearchCondition);

    /**
     * 根据搜索获取一页数据
     * @param customizationSearchCondition 额外搜索条件自定义器
     * @param orderItems 排序对象
     * @return 一页数据
     */
    List<E> getSearchedList(CustomizationSearchCondition<E> customizationSearchCondition, OrderItem... orderItems);

    /**
     * 根据搜索获取一页数据
     * @param searchForm 搜索条件表单
     * @param customizationSearchCondition 额外搜索条件自定义器
     * @return 一页数据
     */
    List<E> getSearchedList(S searchForm, CustomizationSearchCondition<E> customizationSearchCondition);

    /**
     * 根据搜索获取一页数据
     * @param searchForm 搜索条件表单
     * @param customizationSearchCondition 额外搜索条件自定义器
     * @param orderItems 排序对象
     * @return 一页数据
     */
    List<E> getSearchedList(S searchForm, CustomizationSearchCondition<E> customizationSearchCondition, OrderItem... orderItems);

    /**
     * 根据操作用户和搜索获取一页数据
     * @param searchForm 搜索条件表单
     * @param operationUserId 操作用户ID
     * @param customizationSearchCondition 额外搜索条件自定义器
     * @return 一页数据
     */
    List<E> getSearchedList(S searchForm, Serializable operationUserId, CustomizationSearchCondition<E> customizationSearchCondition);

    /**
     * 根据操作用户和搜索获取一页数据
     * @param searchForm 搜索条件表单
     * @param operationUserId 操作用户ID
     * @param customizationSearchCondition 额外搜索条件自定义器
     * @param orderItems 排序对象
     * @return 一页数据
     */
    List<E> getSearchedList(S searchForm, Serializable operationUserId, CustomizationSearchCondition<E> customizationSearchCondition, OrderItem... orderItems);

    /**
     * 根据操作用户和搜索获取一页数据
     * @param searchForm 搜索条件表单
     * @param operationUserId 操作用户ID
     * @return 一页数据
     */
    List<E> getSearchedList(S searchForm, Serializable operationUserId);

    /**
     * 根据操作用户和搜索获取一页数据
     * @param searchForm 搜索条件表单
     * @param operationUserId 操作用户ID
     * @param orderItems 排序对象
     * @return 一页数据
     */
    List<E> getSearchedList(S searchForm, Serializable operationUserId, OrderItem... orderItems);

    /**
     * 根据操作用户和搜索获取一页数据
     * @param operationUserId 操作用户ID
     * @param customizationSearchCondition 额外搜索条件自定义器
     * @return 一页数据
     */
    List<E> getSearchedList(Serializable operationUserId, CustomizationSearchCondition<E> customizationSearchCondition);

    /**
     * 根据操作用户和搜索获取一页数据
     * @param operationUserId 操作用户ID
     * @param customizationSearchCondition 额外搜索条件自定义器
     * @param orderItems 排序对象
     * @return 一页数据
     */
    List<E> getSearchedList(Serializable operationUserId, CustomizationSearchCondition<E> customizationSearchCondition, OrderItem... orderItems);

    /**
     * 根据机构和搜索获取一页数据
     * @param searchForm 搜索条件表单
     * @param orgId 机构ID
     * @param customizationSearchCondition 额外搜索条件自定义器
     * @return 一页数据
     */
    List<E> getSearchedListOfOrg(S searchForm, Serializable orgId, CustomizationSearchCondition<E> customizationSearchCondition);

    /**
     * 根据机构和搜索获取一页数据
     * @param searchForm 搜索条件表单
     * @param orgId 机构ID
     * @param customizationSearchCondition 额外搜索条件自定义器
     * @param orderItems 排序对象
     * @return 一页数据
     */
    List<E> getSearchedListOfOrg(S searchForm, Serializable orgId, CustomizationSearchCondition<E> customizationSearchCondition, OrderItem... orderItems);

    /**
     * 根据机构和搜索获取一页数据
     * @param searchForm 搜索条件表单
     * @param orgId 机构ID
     * @return 一页数据
     */
    List<E> getSearchedListOfOrg(S searchForm, Serializable orgId);

    /**
     * 根据机构和搜索获取一页数据
     * @param searchForm 搜索条件表单
     * @param orgId 机构ID
     * @param orderItems 排序对象
     * @return 一页数据
     */
    List<E> getSearchedListOfOrg(S searchForm, Serializable orgId, OrderItem... orderItems);

    /**
     * 根据机构和搜索获取一页数据
     * @param orgId 机构ID
     * @param customizationSearchCondition 额外搜索条件自定义器
     * @return 一页数据
     */
    List<E> getSearchedListOfOrg(Serializable orgId, CustomizationSearchCondition<E> customizationSearchCondition);

    /**
     * 根据机构和搜索获取一页数据
     * @param orgId 机构ID
     * @param customizationSearchCondition 额外搜索条件自定义器
     * @param orderItems 排序对象
     * @return 一页数据
     */
    List<E> getSearchedListOfOrg(Serializable orgId, CustomizationSearchCondition<E> customizationSearchCondition, OrderItem... orderItems);

}
