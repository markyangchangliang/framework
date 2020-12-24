package com.markyang.framework.app.system.service.impl;

import com.markyang.framework.app.system.form.search.TaskSearchForm;
import com.markyang.framework.app.system.service.TaskService;
import com.markyang.framework.app.system.repository.TaskRepository;
import com.markyang.framework.pojo.entity.system.Task;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 定时任务(Task)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
@Service
public class TaskServiceImpl extends AbstractSearchableServiceImpl<Task, TaskRepository, TaskSearchForm> implements TaskService {

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public Task create() {
        return Task.builder().build();
    }
}