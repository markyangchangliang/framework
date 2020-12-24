package com.markyang.framework.app.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.markyang.framework.app.base.exception.UpdateDeniedException;
import com.markyang.framework.app.system.form.search.DeptSearchForm;
import com.markyang.framework.app.system.service.DeptService;
import com.markyang.framework.app.system.service.DeptWorkerService;
import com.markyang.framework.app.system.repository.DeptRepository;
import com.markyang.framework.pojo.common.support.TreeNode;
import com.markyang.framework.pojo.dto.system.DeptTreeDto;
import com.markyang.framework.pojo.dto.system.DictDto;
import com.markyang.framework.pojo.entity.system.Dept;
import com.markyang.framework.pojo.entity.system.DeptWorker;
import com.markyang.framework.service.common.CommonInfoCacheService;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import com.markyang.framework.util.BuilderUtils;
import com.markyang.framework.util.JsonUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门管理(Dept)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 14:46:17
 */
@Service
@AllArgsConstructor
public class DeptServiceImpl extends AbstractSearchableServiceImpl<Dept, DeptRepository, DeptSearchForm> implements DeptService {


    private final DeptWorkerService deptWorkerService;
    private final CommonInfoCacheService commonInfoCacheService;
    private final DeptRepository repository;

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public Dept create() {
        return Dept.builder().build();
    }

    @Override
    public void beforeAdd(Dept entity) {
        if (!StringUtils.isEmpty(entity.getAttribute())) {
            entity.setAttribute(JSONArray.toJSONString(entity.getAttribute().split(",")));
        }
    }

    @Override
    public void afterAdd(Dept entity) {
        // TODO 添加到企业微信
        // 更新部门缓存
        this.commonInfoCacheService.updateCachedDeptInfo(entity);
    }

    @Override
    public void beforeUpdate(Dept entity) {
        if (StringUtils.isNotBlank(entity.getAttribute())) {
            entity.setAttribute(JsonUtils.toJson(StringUtils.split(entity.getAttribute(), ",")));
        } else {
            entity.setAttribute("[]");
        }
    }

    @Override
    public void afterUpdate(Dept entity) {
        // TODO 修改企业微信
        // 更新部门缓存
        this.commonInfoCacheService.updateCachedDeptInfo(entity);
    }

    @Override
    public void beforeDelete(Dept entity) {
        List<Dept> deptList = this.list(Wrappers.<Dept>lambdaQuery().eq(Dept::getParentId, entity.getId()));
        if (!CollectionUtils.isEmpty(deptList)) {
            throw new UpdateDeniedException("该部门下有子部门，不允许删除！");
        }
        List<DeptWorker> deptWorkerList = this.deptWorkerService.findByDeptId(entity.getId());
        if (!CollectionUtils.isEmpty(deptWorkerList)) {
            throw new UpdateDeniedException("该部门下有职员，不允许删除！");
        }
    }

    @Override
    public void afterDelete(Dept entity) {
        // TODO 删除企业微信
        // 更新部门缓存
        this.commonInfoCacheService.deleteCachedDeptInfo(entity.getId());
    }

    /**
     * 获取部门树
     *
     * @return 部门树结构
     */
    @Override
    public List<TreeNode> getDeptTree(String orgId, String parentId) {
        if (StringUtils.isBlank(parentId)) {
            parentId = "0";
        }
        List<DeptTreeDto> treeData = this.list(Wrappers.<Dept>lambdaQuery()
                .select(Dept::getId, Dept::getParentId, Dept::getName, Dept::getType).eq(Dept::getOrgId, orgId))
                .parallelStream()
                .map(dept -> DeptTreeDto.of(dept.getId(), dept.getParentId(), dept.getName(), dept.getType(), null))
                .collect(Collectors.toList());
        return BuilderUtils.newTreeBuilder(treeData).topLevelId(parentId).build();
    }

    /**
     * 添加默认部门，不触发后置操作，用于添加默认部门
     *
     * @param dept
     * @return
     */
    @Override
    public Dept addDefaultDept(Dept dept) {
        Dept result = this.add(dept);
        // 更新部门缓存
        this.commonInfoCacheService.updateCachedDeptInfo(dept);
        return result;
    }

    /**
     * 获取部门列表
     *
     * @param orgId 机构编号
     * @param type  科室类别
     * @return 结果分页对象
     */
    @Override
    public List<DictDto> getDepts(String orgId, String type) {
        return this.repository.getDepts(orgId, type);
    }
}