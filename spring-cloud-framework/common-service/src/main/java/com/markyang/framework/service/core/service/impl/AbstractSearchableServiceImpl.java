package com.markyang.framework.service.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.markyang.framework.service.core.form.SearchBaseForm;
import com.markyang.framework.service.core.repository.FrameworkRepository;
import com.markyang.framework.service.core.search.SearchConditionUtils;
import com.markyang.framework.service.core.service.SearchableService;
import com.markyang.framework.service.core.service.support.CustomizationSearchCondition;
import com.markyang.framework.pojo.constant.TableConstants;
import com.markyang.framework.pojo.entity.BaseEntity;
import com.markyang.framework.util.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 搜索服务接口实现类
 * @author yangchangliang
 */
public abstract class AbstractSearchableServiceImpl<E extends BaseEntity, R extends FrameworkRepository<E>, S extends SearchBaseForm> extends AbstractPageableServiceImpl<E, R> implements SearchableService<E, S> {

    private final CustomizationSearchCondition<E> EMPTY = wrapper -> {};

    /**
     * 注入JPA动态条件查询实现类
     */
    @Autowired
    private R repository;

    /**
     * 创造搜索条件
     * @param searchForm 搜索表单对象
     * @param customizationSearchCondition 搜索条件生成器
     * @param operationUserId 操作用户
     * @param orgId 机构ID
     * @return 条件
     */
    private QueryWrapper<E> buildQueryWrapper(S searchForm, CustomizationSearchCondition<E> customizationSearchCondition, Serializable operationUserId, Serializable orgId) {
        QueryWrapper<E> queryWrapper = Wrappers.query();
        if (Objects.nonNull(searchForm)) {
            SearchConditionUtils.applySearchCondition(searchForm, queryWrapper);
        }
        queryWrapper
            // 增加当前登录用户的过滤
            .eq(Objects.nonNull(operationUserId), ConversionUtils.camelToDash(TableConstants.OPERATION_USER_ID_FIELD_NAME), operationUserId)
            // 增加机构的过滤
            .eq(Objects.nonNull(orgId), ConversionUtils.camelToDash(TableConstants.ORG_ID_FIELD_NAME), orgId);
        // 添加额外的搜索条件
        if (Objects.nonNull(customizationSearchCondition)) {
            customizationSearchCondition.customize(queryWrapper);
        }
        return queryWrapper;
    }

    /**
     * 根据搜索获取一页数据
     *
     * @param searchForm 搜索条件表单
     * @param page       分页和排序对象
     * @return 一页数据
     */
    @Override
    public IPage<E> getSearchedPage(S searchForm, IPage<E> page) {
        return this.getSearchedPage(searchForm, page, null, EMPTY);
    }

    /**
     * 根据操作用户和搜索获取一页数据
     *
     * @param searchForm      搜索条件表单
     * @param page            分页和排序对象
     * @param operationUserId 操作用户ID
     * @return 一页数据
     */
    @Override
    public IPage<E> getSearchedPage(S searchForm, IPage<E> page, Serializable operationUserId) {
        return this.getSearchedPage(searchForm, page, operationUserId, EMPTY);
    }

    /**
     * 根据机构ID和搜索获取一页数据
     *
     * @param orgId 机构ID
     * @param searchForm 搜索条件表单
     * @param page       分页和排序对象
     * @return 一页数据
     */
    @Override
    public IPage<E> getSearchedPageOfOrg(S searchForm, IPage<E> page, Serializable orgId) {
        return this.getSearchedPageOfOrg(searchForm, page, orgId, EMPTY);
    }

    /**
     * 根据搜索获取一页数据
     *
     * @param searchForm 搜索条件表单
     * @param page       分页和排序对象
     * @param customizationSearchCondition 额外搜索条件构造器
     * @return 一页数据
     */
    @Override
    public IPage<E> getSearchedPage(S searchForm, IPage<E> page, CustomizationSearchCondition<E> customizationSearchCondition) {
        IPage<E> result = this.repository.selectPage(page, this.buildQueryWrapper(searchForm, customizationSearchCondition, null, null));
        this.applyPostGetHook(result.getRecords());
        return result;
    }

    /**
     * 根据操作用户和搜索获取一页数据
     *
     * @param searchForm      搜索条件表单
     * @param page            分页和排序对象
     * @param operationUserId 操作用户ID
     * @param customizationSearchCondition 额外搜索条件构造器
     * @return 一页数据
     */
    @Override
    public IPage<E> getSearchedPage(S searchForm, IPage<E> page, Serializable operationUserId, CustomizationSearchCondition<E> customizationSearchCondition) {
        IPage<E> result = this.repository.selectPage(page, this.buildQueryWrapper(searchForm, customizationSearchCondition, operationUserId, null));
        this.applyPostGetHook(result.getRecords());
        return result;
    }

    /**
     * 根据机构ID和搜索获取一页数据
     *
     * @param orgId 机构ID
     * @param searchForm 搜索条件表单
     * @param page       分页和排序对象
     * @param customizationSearchCondition 额外搜索条件构造器
     * @return 一页数据
     */
    @Override
    public IPage<E> getSearchedPageOfOrg(S searchForm, IPage<E> page, Serializable orgId, CustomizationSearchCondition<E> customizationSearchCondition) {
        IPage<E> result = this.repository.selectPage(page, this.buildQueryWrapper(searchForm, customizationSearchCondition, null, orgId));
        this.applyPostGetHook(result.getRecords());
        return result;
    }

    /**
     * 根据搜索获取一页数据
     *
     * @param page              分页和排序对象
     * @param customizationSearchCondition 额外搜索条件构造器
     * @return 一页数据
     */
    @Override
    public IPage<E> getSearchedPage(IPage<E> page, CustomizationSearchCondition<E> customizationSearchCondition) {
        return this.getSearchedPage(null, page, null, customizationSearchCondition);
    }

    /**
     * 根据操作用户和搜索获取一页数据
     *
     * @param page              分页和排序对象
     * @param operationUserId   操作用户ID
     * @param customizationSearchCondition 额外搜索条件构造器
     * @return 一页数据
     */
    @Override
    public IPage<E> getSearchedPage(IPage<E> page, Serializable operationUserId, CustomizationSearchCondition<E> customizationSearchCondition) {
        return this.getSearchedPage(null, page, operationUserId, customizationSearchCondition);
    }

    /**
     * 根据机构ID和搜索获取一页数据
     *
     * @param page              分页和排序对象
     * @param orgId             机构ID
     * @param customizationSearchCondition 额外搜索条件构造器
     * @return 一页数据
     */
    @Override
    public IPage<E> getSearchedPageOfOrg(IPage<E> page, Serializable orgId, CustomizationSearchCondition<E> customizationSearchCondition) {
        return this.getSearchedPageOfOrg(null, page, orgId, customizationSearchCondition);
    }

    /**
     * 根据机构ID和搜索获取一页数据
     *
     * @param searchForm 搜索条件表单
     * @return 一页数据
     */
    @Override
    public List<E> getSearchedList(S searchForm) {
        return this.getSearchedList(searchForm, EMPTY);
    }

    /**
     * 根据机构ID和搜索获取一页数据
     *
     * @param searchForm 搜索条件表单
     * @param orderItems       排序对象
     * @return 一页数据
     */
    @Override
    public List<E> getSearchedList(S searchForm, OrderItem... orderItems) {
        return this.getSearchedList(searchForm, null, EMPTY, orderItems);
    }

    /**
     * 根据搜索获取一页数据
     *
     * @param searchForm        搜索条件表单
     * @param customizationSearchCondition 额外搜索条件构造器
     * @param orderItems              排序对象
     * @return 一页数据
     */
    @Override
    public List<E> getSearchedList(S searchForm, CustomizationSearchCondition<E> customizationSearchCondition, OrderItem... orderItems) {
        List<E> entities = this.list(this.setOrderItems(this.buildQueryWrapper(searchForm, customizationSearchCondition, null, null), orderItems));
        this.applyPostGetHook(entities);
        return entities;
    }

    /**
     * 根据操作用户和搜索获取一页数据
     *
     * @param searchForm        搜索条件表单
     * @param operationUserId   操作用户ID
     * @param customizationSearchCondition 额外搜索条件构造器
     * @param orderItems              排序对象
     * @return 一页数据
     */
    @Override
    public List<E> getSearchedList(S searchForm, Serializable operationUserId, CustomizationSearchCondition<E> customizationSearchCondition, OrderItem... orderItems) {
        List<E> entities = this.list(this.setOrderItems(this.buildQueryWrapper(searchForm, customizationSearchCondition, operationUserId, null), orderItems));
        this.applyPostGetHook(entities);
        return entities;
    }

    /**
     * 根据操作用户和搜索获取一页数据
     *
     * @param searchForm      搜索条件表单
     * @param operationUserId 操作用户ID
     * @param orderItems            排序对象
     * @return 一页数据
     */
    @Override
    public List<E> getSearchedList(S searchForm, Serializable operationUserId, OrderItem... orderItems) {
        return this.getSearchedList(searchForm, operationUserId, EMPTY, orderItems);
    }

    /**
     * 根据操作用户和搜索获取一页数据
     *
     * @param operationUserId   操作用户ID
     * @param customizationSearchCondition 额外搜索条件构造器
     * @param orderItems              排序对象
     * @return 一页数据
     */
    @Override
    public List<E> getSearchedList(Serializable operationUserId, CustomizationSearchCondition<E> customizationSearchCondition, OrderItem... orderItems) {
        return this.getSearchedList(null, operationUserId, customizationSearchCondition, orderItems);
    }

    /**
     * 根据机构和搜索获取一页数据
     *
     * @param searchForm        搜索条件表单
     * @param orgId             机构ID
     * @param customizationSearchCondition 额外搜索条件构造器
     * @param orderItems              排序对象
     * @return 一页数据
     */
    @Override
    public List<E> getSearchedListOfOrg(S searchForm, Serializable orgId, CustomizationSearchCondition<E> customizationSearchCondition, OrderItem... orderItems) {
        List<E> entities = this.list(this.setOrderItems(this.buildQueryWrapper(searchForm, customizationSearchCondition, null, orgId), orderItems));
        this.applyPostGetHook(entities);
        return entities;
    }

    /**
     * 根据机构和搜索获取一页数据
     *
     * @param searchForm 搜索条件表单
     * @param orgId      机构ID
     * @param orderItems       排序对象
     * @return 一页数据
     */
    @Override
    public List<E> getSearchedListOfOrg(S searchForm, Serializable orgId, OrderItem... orderItems) {
        return this.getSearchedListOfOrg(searchForm, orgId, EMPTY, orderItems);
    }

    /**
     * 根据机构和搜索获取一页数据
     *
     * @param orgId             机构ID
     * @param customizationSearchCondition 额外搜索条件构造器
     * @param orderItems              排序对象
     * @return 一页数据
     */
    @Override
    public List<E> getSearchedListOfOrg(Serializable orgId, CustomizationSearchCondition<E> customizationSearchCondition, OrderItem... orderItems) {
        return this.getSearchedListOfOrg(null, orgId, customizationSearchCondition, orderItems);
    }

    /**
     * 根据搜索获取一页数据
     *
     * @param customizationSearchCondition 额外搜索条件构造器
     * @return 一页数据
     */
    @Override
    public List<E> getSearchedList(CustomizationSearchCondition<E> customizationSearchCondition) {
        List<E> entities = this.list(this.buildQueryWrapper(null, customizationSearchCondition, null, null));
        this.applyPostGetHook(entities);
        return entities;
    }

    /**
     * 根据搜索获取一页数据
     *
     * @param customizationSearchCondition 额外搜索条件构造器
     * @param orderItems 排序对象
     * @return 一页数据
     */
    @Override
    public List<E> getSearchedList(CustomizationSearchCondition<E> customizationSearchCondition, OrderItem... orderItems) {
        List<E> entities = this.list(this.setOrderItems(this.buildQueryWrapper(null, customizationSearchCondition, null, null), orderItems));
        this.applyPostGetHook(entities);
        return entities;
    }

    /**
     * 根据搜索获取一页数据
     *
     * @param searchForm        搜索条件表单
     * @param customizationSearchCondition 额外搜索条件构造器
     * @return 一页数据
     */
    @Override
    public List<E> getSearchedList(S searchForm, CustomizationSearchCondition<E> customizationSearchCondition) {
        List<E> entities = this.list(this.buildQueryWrapper(searchForm, customizationSearchCondition, null, null));
        this.applyPostGetHook(entities);
        return entities;
    }

    /**
     * 根据操作用户和搜索获取一页数据
     *
     * @param searchForm        搜索条件表单
     * @param operationUserId   操作用户ID
     * @param customizationSearchCondition 额外搜索条件构造器
     * @return 一页数据
     */
    @Override
    public List<E> getSearchedList(S searchForm, Serializable operationUserId, CustomizationSearchCondition<E> customizationSearchCondition) {
        List<E> entities = this.list(this.buildQueryWrapper(searchForm, customizationSearchCondition, operationUserId, null));
        this.applyPostGetHook(entities);
        return entities;
    }

    /**
     * 根据操作用户和搜索获取一页数据
     *
     * @param searchForm      搜索条件表单
     * @param operationUserId 操作用户ID
     * @return 一页数据
     */
    @Override
    public List<E> getSearchedList(S searchForm, Serializable operationUserId) {
        return this.getSearchedList(searchForm, operationUserId, EMPTY);
    }

    /**
     * 根据操作用户和搜索获取一页数据
     *
     * @param operationUserId   操作用户ID
     * @param customizationSearchCondition 额外搜索条件构造器
     * @return 一页数据
     */
    @Override
    public List<E> getSearchedList(Serializable operationUserId, CustomizationSearchCondition<E> customizationSearchCondition) {
        return this.getSearchedList(null, operationUserId, customizationSearchCondition);
    }

    /**
     * 根据机构和搜索获取一页数据
     *
     * @param searchForm        搜索条件表单
     * @param orgId             机构ID
     * @param customizationSearchCondition 额外搜索条件构造器
     * @return 一页数据
     */
    @Override
    public List<E> getSearchedListOfOrg(S searchForm, Serializable orgId, CustomizationSearchCondition<E> customizationSearchCondition) {
        List<E> entities = this.list(this.buildQueryWrapper(searchForm, customizationSearchCondition, null, orgId));
        this.applyPostGetHook(entities);
        return entities;
    }

    /**
     * 根据机构和搜索获取一页数据
     *
     * @param searchForm 搜索条件表单
     * @param orgId      机构ID
     * @return 一页数据
     */
    @Override
    public List<E> getSearchedListOfOrg(S searchForm, Serializable orgId) {
        return this.getSearchedListOfOrg(searchForm, orgId, EMPTY);
    }

    /**
     * 根据机构和搜索获取一页数据
     *
     * @param orgId             机构ID
     * @param customizationSearchCondition 额外搜索条件构造器
     * @return 一页数据
     */
    @Override
    public List<E> getSearchedListOfOrg(Serializable orgId, CustomizationSearchCondition<E> customizationSearchCondition) {
        return this.getSearchedListOfOrg(null, orgId, customizationSearchCondition);
    }
}
