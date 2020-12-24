package com.markyang.framework.app.system.service;

import com.markyang.framework.app.system.form.search.DeptSearchForm;
import com.markyang.framework.pojo.common.support.TreeNode;
import com.markyang.framework.pojo.dto.system.DictDto;
import com.markyang.framework.pojo.entity.system.Dept;
import com.markyang.framework.service.core.service.SearchableService;

import java.util.List;

/**
 * 部门管理(Dept)服务类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 14:46:17
 */
public interface DeptService extends SearchableService<Dept, DeptSearchForm> {

    /**
     * 获取部门树
     *
     * @param orgId    机构编号
     * @param parentId 父级编号
     * @return 部门树结构
     */
    List<TreeNode> getDeptTree(String orgId, String parentId);


    /**
     * 添加默认部门，不同步到企业微信，用于添加默认部门
     *
     * @param dept 部门信息
     * @return 部门信息
     */
    Dept addDefaultDept(Dept dept);


    /**
     * 获取部门列表
     *
     * @param orgId 机构编号
     * @param type  科室类别
     * @return 结果分页对象
     */
    List<DictDto> getDepts(String orgId, String type);
}