package com.markyang.framework.app.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markyang.framework.app.system.form.search.WorkerSearchForm;
import com.markyang.framework.pojo.common.support.TreeNode;
import com.markyang.framework.pojo.dto.system.WorkerInfoDto;
import com.markyang.framework.pojo.entity.system.DeptWorker;
import com.markyang.framework.pojo.entity.system.Worker;
import com.markyang.framework.pojo.web.PageVo;
import com.markyang.framework.service.core.service.SearchableService;

import java.util.List;

/**
 * 职员管理(Worker)服务类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
public interface WorkerService extends SearchableService<Worker, WorkerSearchForm> {
    /**
     * 获取树形结构
     *
     * @param orgId         机构编号
     * @param type          类型
     * @param positional    职称
     * @param positionGrade 岗位等级
     * @param post          职务
     * @param online        是否支持网上项目
     * @return 树形结构
     */
    List<TreeNode> getDeptWorkerTree(String orgId, String type, String positional, String post,
                                     String positionGrade, String online);

    /**
     * 获取职员部门信息
     *
     * @param workerId 职员编号
     * @return 部门信息
     */
    DeptWorker getWorkerDept(String workerId);

    /**
     * 职员转科
     *
     * @param deptWorker 职员部门信息
     */
    void updateWorkerDept(DeptWorker deptWorker);

    /**
     * 获取机构部门职员
     *
     * @param deptId 部门ID
     * @param page   分页对象
     * @return 结果对象
     */
    PageVo<Worker> getOrgDeptWorkers(String deptId, Page<Worker> page);

}