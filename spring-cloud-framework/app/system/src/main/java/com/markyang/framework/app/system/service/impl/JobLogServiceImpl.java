package com.markyang.framework.app.system.service.impl;

import com.markyang.framework.app.system.form.search.JobLogSearchForm;
import com.markyang.framework.app.system.service.JobLogService;
import com.markyang.framework.app.system.repository.JobLogRepository;
import com.markyang.framework.pojo.entity.system.JobLog;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 定时任务日志(JobLog)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
@Service
public class JobLogServiceImpl extends AbstractSearchableServiceImpl<JobLog, JobLogRepository, JobLogSearchForm> implements JobLogService {

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public JobLog create() {
        return JobLog.builder().build();
    }
}