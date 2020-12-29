package com.markyang.framework.pojo.schedule;

import com.markyang.framework.pojo.schedule.exception.TaskScheduleException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.ApplicationContext;

import java.util.Objects;

/**
 * 框架任务
 *
 * @author yangchangliang
 * @version 1
 */
@Slf4j
public class FrameworkJob implements Job {

    /**
     * 任务Key
     */
    public static final JobKey JOB_KEY = JobKey.jobKey("job", "framework");

    /**
     * 定时任务
     */
    @Setter
    private ScheduledTask scheduledTask;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (Objects.isNull(this.scheduledTask)) {
            return;
        }
        try {
            // 执行任务
            this.scheduledTask.execute(context, (ApplicationContext) context.getScheduler().getContext().get("applicationContext"));
        } catch (Exception e) {
            log.error("任务[{}]执行出错：{}", this.scheduledTask.getClass().getName(), e.getMessage(), e);
            // 直接抛出异常
            if (e instanceof JobExecutionException) {
                throw new JobExecutionException(e);
            }
            throw new TaskScheduleException("任务执行出错", e);
        }
    }
}
