package com.markyang.framework.service.core.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.markyang.framework.service.core.repository.FrameworkRepository;
import com.markyang.framework.service.core.service.PageableService;
import com.markyang.framework.pojo.constant.TableConstants;
import com.markyang.framework.pojo.entity.BaseEntity;
import com.markyang.framework.util.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * 分页服务接口实现类
 * @author yangchangliang
 */
public abstract class AbstractPageableServiceImpl<E extends BaseEntity, R extends FrameworkRepository<E>> extends AbstractCrudServiceImpl<E, R> implements PageableService<E> {

    /**
     * Jpa仓库实现注入
     */
    @Autowired
    private R repository;

    /**
     * 根据分页和排序获取一页记录
     *
     * @param page 分页和排序对象
     * @return 一页记录
     */
    @Override
    public IPage<E> getPage(IPage<E> page) {
        IPage<E> result = this.repository.selectPage(page, Wrappers.emptyWrapper());
        this.applyPostGetHook(result.getRecords());
        return result;
    }

    /**
     * 根据分页、排序和操作用户ID获取一页记录
     *
     * @param page            分页和排序对象
     * @param operationUserId 操作用户ID
     * @return 一页记录
     */
    @Override
    public IPage<E> getPage(IPage<E> page, Serializable operationUserId) {
        Assert.notNull(operationUserId, "操作用户ID不能为空");
        IPage<E> result = this.repository.selectPage(page, Wrappers.<E>query().eq(ConversionUtils.camelToDash(TableConstants.OPERATION_USER_ID_FIELD_NAME), operationUserId));
        this.applyPostGetHook(page.getRecords());
        return result;
    }

    /**
     * 根据分页、排序和机构ID获取一页记录
     *
     * @param page  分页和排序对象
     * @param orgId 机构ID
     * @return 一页记录
     */
    @Override
    public IPage<E> getPageOfOrg(IPage<E> page, Serializable orgId) {
        Assert.notNull(orgId, "机构ID不能为空");
        IPage<E> result = repository.selectPage(page, Wrappers.<E>query().eq(ConversionUtils.camelToDash(TableConstants.ORG_ID_FIELD_NAME), orgId));
        this.applyPostGetHook(result.getRecords());
        return result;
    }

}
