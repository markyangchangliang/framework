package com.markyang.framework.pojo.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import java.io.Serializable;

/**
 * 可调度任务，框架最顶层的调度任务接口
 *
 * @author yangchangliang
 * @version 1
 */
public interface ScheduledTask extends Serializable {

    /**
     * 调度任务名称
     */
    String SCHEDULE_TASK_NAME = "scheduledTask";

    /**
     * 任务执行方法
     * @param context 任务执行上下文
     * @param applicationContext Spring容器
     * @throws JobExecutionException 任务执行异常
     */
    void execute(JobExecutionContext context, ApplicationContext applicationContext) throws JobExecutionException;
}
