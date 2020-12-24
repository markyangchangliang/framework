package com.markyang.framework.app.system.service;

import com.markyang.framework.app.system.form.search.JobLogSearchForm;
import com.markyang.framework.service.core.service.SearchableService;
import com.markyang.framework.pojo.entity.system.JobLog;

/**
 * 定时任务日志(JobLog)服务类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
public interface JobLogService extends SearchableService<JobLog, JobLogSearchForm> {
}