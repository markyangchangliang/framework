package com.markyang.framework.app.system.service.impl;


import com.markyang.framework.app.system.form.search.JobSearchForm;
import com.markyang.framework.app.system.service.JobService;
import com.markyang.framework.app.system.repository.JobRepository;
import com.markyang.framework.pojo.entity.system.Job;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 定时任务(Job)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
@Service
public class JobServiceImpl extends AbstractSearchableServiceImpl<Job, JobRepository, JobSearchForm> implements JobService {

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public Job create() {
        return Job.builder().build();
    }
}