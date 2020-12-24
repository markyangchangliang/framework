package com.markyang.framework.app.system.service;

import com.markyang.framework.app.system.form.search.DeptWorkerSearchForm;
import com.markyang.framework.service.core.service.SearchableService;
import com.markyang.framework.pojo.entity.system.DeptWorker;

import java.util.List;

/**
 * 部门职员(DeptWorker)服务类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-28 13:25:33
 */
public interface DeptWorkerService extends SearchableService<DeptWorker, DeptWorkerSearchForm> {
  /**
   * 通过部门获取信息
   * @param deptId 部门信息
   * @return 职员部门信息
   */
  List<DeptWorker> findByDeptId(String deptId);

  /**
   * 删除职员部门信息
   * @param workerId 职员编号
   * @return 结果
   */
  boolean deleteByWorkerId(String workerId);
}