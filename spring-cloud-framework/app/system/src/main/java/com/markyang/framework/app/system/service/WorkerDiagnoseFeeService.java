package com.markyang.framework.app.system.service;

import com.markyang.framework.app.system.form.search.WorkerDiagnoseFeeSearchForm;
import com.markyang.framework.pojo.entity.system.WorkerDiagnoseFee;
import com.markyang.framework.service.core.service.SearchableService;

/**
 * 职员诊察费用(WorkerDiognoseFee)服务类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
public interface WorkerDiagnoseFeeService extends SearchableService<WorkerDiagnoseFee, WorkerDiagnoseFeeSearchForm> {
  /**
   * 同步当前职称信息
   */
  void synchronization();
}