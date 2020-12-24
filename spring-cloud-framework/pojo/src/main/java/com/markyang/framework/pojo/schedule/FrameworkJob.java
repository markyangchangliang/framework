package com.markyang.framework.pojo.schedule;

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

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> when a <code>{@link Trigger}</code>
     * fires that is associated with the <code>Job</code>.
     * </p>
     *
     * <p>
     * The implementation may wish to set a
     * {@link JobExecutionContext#setResult(Object) result} object on the
     * {@link JobExecutionContext} before this method exits.  The result itself
     * is meaningless to Quartz, but may be informative to
     * <code>{@link JobListener}s</code> or
     * <code>{@link TriggerListener}s</code> that are watching the job's
     * execution.
     * </p>
     *
     * @param context context
     * @throws JobExecutionException if there is an exception while executing the job.
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (Objects.isNull(this.scheduledTask)) {
            return;
        }
        try {
            // 执行任务
            this.scheduledTask.execute(context, (ApplicationContext) context.getScheduler().getContext().get("applicationContext"));
        } catch (Exception e) {
            // 直接抛出异常
            /*if (e instanceof JobExecutionException) {
                throw new JobExecutionException(e);
            }
            throw new TaskScheduleException("任务执行出错", e);*/
            log.error("任务[{}]执行出错：{}", this.scheduledTask.getClass().getName(), e.getMessage(), e);
        }
    }
}
