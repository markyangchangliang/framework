package com.markyang.framework.service.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.markyang.framework.pojo.entity.BaseEntity;

import java.io.Serializable;

/**
 * 分页Service接口
 * @author yangchangliang
 */
public interface PageableService <E extends BaseEntity> extends CrudService<E> {

    /**
     * 根据分页和排序获取一页记录
     * @param page 分页和排序对象
     * @return 一页记录
     */
    IPage<E> getPage(IPage<E> page);

    /**
     * 根据分页、排序和操作用户ID获取一页记录
     * @param page 分页和排序对象
     * @param operationUserId 操作用户ID
     * @return 一页记录
     */
    IPage<E> getPage(IPage<E> page, Serializable operationUserId);

    /**
     * 根据分页、排序和业务ID获取一页记录
     * @param page 分页和排序对象
     * @param orgId 机构ID
     * @return 一页记录
     */
    IPage<E> getPageOfOrg(IPage<E> page, Serializable orgId);

}
