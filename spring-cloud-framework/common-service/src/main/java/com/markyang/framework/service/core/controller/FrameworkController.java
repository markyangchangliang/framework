package com.markyang.framework.service.core.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.markyang.framework.service.core.form.SearchBaseForm;
import com.markyang.framework.service.core.service.support.CustomizationSearchCondition;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.entity.BaseEntity;
import com.markyang.framework.service.core.form.UpdateBaseForm;

import java.util.List;

/**
 * 框架控制器接口
 *
 * @author yangchangliang
 * @version 1
 */
public interface FrameworkController<E extends BaseEntity, S extends SearchBaseForm, U extends UpdateBaseForm> {

    /**
     * 额外的搜索条件
     * @return 条件谓词
     */
    default CustomizationSearchCondition<E> addAdditionalSearchCondition() {
        return null;
    }

    /**
     * 在获取单个实体之后触发
     * @param entity 实体对象
     * @param isLast 是否是最后一个实体
     */
    default void afterGet(E entity, boolean isLast) {
    }

    /**
     * 在实体创建之前处理实体属性数据
     * @param entity 实体对象
     * @param form 表单对象
     */
    default void beforeAdd(E entity, U form) {
    }

    /**
     * 在实体修改之前处理实体属性数据
     * @param entity 实体对象
     * @param form 表单对象
     * @return bool 是否继续拷贝属性
     */
    default boolean beforeUpdate(E entity, U form) {
        return true;
    }

    /**
     * 在实体创建之后的处理
     * @param entity 更新后的实体对象
     * @param form 表单对象
     * @return 返回数据
     */
    default Object afterAdd(E entity, U form) {
        return null;
    }

    /**
     * 在实体修改之后的处理
     * @param entity 修改后的实体对象
     * @param form 表单对象
     * @return 返回数据
     */
    default Object afterUpdate(E entity, U form) {
        return null;
    }

    /**
     * 删除之前做的检测操作
     * @param entity 实体对象
     */
    default void beforeDelete(E entity) {
    }

    /**
     * 在实体删除之后的处理
     * @param entity 删除完的实体对象
     * @return 返回数据
     */
    default Object afterDelete(E entity) {
        return null;
    }

    /**
     * 是否是机构所属数据
     * @return bool
     */
    default boolean isOrgBelonged() {
        return false;
    }

    /**
     * 是否是用户所属数据（注意：不能与机构所属数据同时为true）
     * @return bool
     */
    default boolean isUserBelonged() {
        return false;
    }

    /**
     * 复制属性时忽略的字段
     * @param form 表单数据
     * @return 忽略字段
     */
    default String[] ignoredProperties(U form) {
        return null;
    }

    /**
     * 默认排序规则
     * @return 排序对象
     */
    default List<OrderItem> customizedSort() {
        return null;
    }

    /**
     * 默认的应用ID
     * @return 应用ID
     */
    default String getAppId() {
        return FrameworkConstants.REGIONAL_MEDICAL_APP_ID;
    }
}
