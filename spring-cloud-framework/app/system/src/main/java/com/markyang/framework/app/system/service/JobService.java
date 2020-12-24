package com.markyang.framework.app.system.service;

import com.markyang.framework.app.system.form.search.JobSearchForm;
import com.markyang.framework.service.core.service.SearchableService;
import com.markyang.framework.pojo.entity.system.Job;

/**
 * 定时任务(Job)服务类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
public interface JobService extends SearchableService<Job, JobSearchForm> {
}