package com.markyang.framework.util;

import com.markyang.framework.pojo.schedule.FrameworkJob;
import com.markyang.framework.pojo.schedule.ScheduledTask;
import org.quartz.*;

import java.time.LocalDateTime;

/**
 * 触发器工具类
 *
 * @author yangchangliang
 * @version 1
 */
public final class TriggerUtils {

    /**
     * 创建一个立刻执行的触发器
     * @param name 触发器名称
     * @param group 触发器组
     * @param scheduledTask 调度任务
     * @return 触发器对象
     */
    public static Trigger createTriggerAtNow(String name, String group, ScheduledTask scheduledTask) {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduledTask.SCHEDULE_TASK_NAME, scheduledTask);
        return TriggerBuilder.newTrigger()
            .forJob(FrameworkJob.JOB_KEY)
            .withIdentity(name, group)
            .usingJobData(dataMap)
            .startNow()
            .build();
    }

    /**
     * 创建一个在未来某一时刻执行的触发器
     * @param name 触发器名称
     * @param group 触发器组
     * @param scheduledTask 调度任务
     * @param startDateTime 执行时刻
     * @return 触发器对象
     */
    public static Trigger createTriggerAtDateTime(String name, String group, ScheduledTask scheduledTask, LocalDateTime startDateTime) {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduledTask.SCHEDULE_TASK_NAME, scheduledTask);
        return TriggerBuilder.newTrigger()
            .forJob(FrameworkJob.JOB_KEY)
            .withIdentity(name, group)
            .usingJobData(dataMap)
            .startAt(DateTimeUtils.toDate(startDateTime))
            .build();
    }

    /**
     * 创建一个以Cron表达式执行的触发器
     * @param name 触发器名称
     * @param group 触发器组
     * @param scheduledTask 调度任务
     * @param cronExpression Cron表达式
     * @return 触发器对象
     */
    public static Trigger createTriggerAtNowWithCronTab(String name, String group, ScheduledTask scheduledTask, String cronExpression) {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduledTask.SCHEDULE_TASK_NAME, scheduledTask);
        return TriggerBuilder.newTrigger()
            .forJob(FrameworkJob.JOB_KEY)
            .withIdentity(name, group)
            .usingJobData(dataMap)
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
            .startNow()
            .build();
    }

    /**
     * 创建一个以Cron表达式执行的触发器
     * @param name 触发器名称
     * @param group 触发器组
     * @param scheduledTask 调度任务
     * @param cronExpression Cron表达式
     * @param beginDateTime 开始日期时间
     * @return 触发器对象
     */
    public static Trigger createTriggerAtDateTimeWithCronTab(String name, String group, ScheduledTask scheduledTask, String cronExpression, LocalDateTime beginDateTime) {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduledTask.SCHEDULE_TASK_NAME, scheduledTask);
        return TriggerBuilder.newTrigger()
            .forJob(FrameworkJob.JOB_KEY)
            .withIdentity(name, group)
            .usingJobData(dataMap)
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
            .startAt(DateTimeUtils.toDate(beginDateTime))
            .build();
    }

    /**
     * 创建一个以Cron表达式执行的触发器
     * @param name 触发器名称
     * @param group 触发器组
     * @param scheduledTask 调度任务
     * @param cronExpression Cron表达式
     * @param endDateTime 结束日期时间
     * @return 触发器对象
     */
    public static Trigger createTriggerAtNowWithLimitedCronTab(String name, String group, ScheduledTask scheduledTask, String cronExpression, LocalDateTime endDateTime) {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduledTask.SCHEDULE_TASK_NAME, scheduledTask);
        return TriggerBuilder.newTrigger()
            .forJob(FrameworkJob.JOB_KEY)
            .withIdentity(name, group)
            .usingJobData(dataMap)
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
            .startNow()
            .endAt(DateTimeUtils.toDate(endDateTime))
            .build();
    }

    /**
     * 创建一个以Cron表达式执行的触发器
     * @param name 触发器名称
     * @param group 触发器组
     * @param scheduledTask 调度任务
     * @param cronExpression Cron表达式
     * @param beginDateTime 开始日期时间
     * @param endDateTime 结束日期时间
     * @return 触发器对象
     */
    public static Trigger createTriggerAtDateTimeWithLimitedCronTab(String name, String group, ScheduledTask scheduledTask, String cronExpression, LocalDateTime beginDateTime, LocalDateTime endDateTime) {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduledTask.SCHEDULE_TASK_NAME, scheduledTask);
        return TriggerBuilder.newTrigger()
            .forJob(FrameworkJob.JOB_KEY)
            .withIdentity(name, group)
            .usingJobData(dataMap)
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
            .startAt(DateTimeUtils.toDate(beginDateTime))
            .endAt(DateTimeUtils.toDate(endDateTime))
            .build();
    }

    /**
     * 创建一个以简单调度器规则执行的触发器
     * @param name 触发器名称
     * @param group 触发器组
     * @param scheduledTask 调度任务
     * @param simpleScheduleBuilder 简单的重复调度器构建器
     * @return 触发器对象
     */
    public static Trigger createTriggerAtNowWithIntervalRepeat(String name, String group, ScheduledTask scheduledTask, SimpleScheduleBuilder simpleScheduleBuilder) {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduledTask.SCHEDULE_TASK_NAME, scheduledTask);
        return TriggerBuilder.newTrigger()
            .forJob(FrameworkJob.JOB_KEY)
            .withIdentity(name, group)
            .usingJobData(dataMap)
            .withSchedule(simpleScheduleBuilder)
            .startNow()
            .build();
    }

    /**
     * 创建一个以简单调度器规则执行的触发器
     * @param name 触发器名称
     * @param group 触发器组
     * @param scheduledTask 调度任务
     * @param simpleScheduleBuilder 简单的重复调度器构建器
     * @param beginDateTime 开始时间
     * @return 触发器对象
     */
    public static Trigger createTriggerAtDateTimeWithIntervalRepeat(String name, String group, ScheduledTask scheduledTask, SimpleScheduleBuilder simpleScheduleBuilder, LocalDateTime beginDateTime) {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduledTask.SCHEDULE_TASK_NAME, scheduledTask);
        return TriggerBuilder.newTrigger()
            .forJob(FrameworkJob.JOB_KEY)
            .withIdentity(name, group)
            .usingJobData(dataMap)
            .withSchedule(simpleScheduleBuilder)
            .startAt(DateTimeUtils.toDate(beginDateTime))
            .build();
    }
}
