package com.markyang.framework.app.system.service;

import com.markyang.framework.app.system.form.search.TaskSearchForm;
import com.markyang.framework.pojo.entity.system.Task;
import com.markyang.framework.service.core.service.SearchableService;

/**
 * 定时任务(Task)服务类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
public interface TaskService extends SearchableService<Task, TaskSearchForm> {
}