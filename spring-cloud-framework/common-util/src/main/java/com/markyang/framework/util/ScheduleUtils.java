package com.markyang.framework.util;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 任务调度工具类
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@Slf4j
public final class ScheduleUtils {

    /**
     * 调度器
     */
    private static Scheduler scheduler;

    public ScheduleUtils(@Autowired(required = false) Scheduler scheduler) {
        ScheduleUtils.scheduler = scheduler;
    }

    private static void handleException(Exception e) {
        log.error("任务调度出现错误：{}", e.getMessage());
    }

    /**
     * 调度任务
     * @param trigger 触发器
     */
    public static void schedule(Trigger trigger) {
        try {
            if (!scheduler.checkExists(trigger.getKey())) {
                // 调度任务
                scheduler.scheduleJob(trigger);
            }
        } catch (SchedulerException e) {
            handleException(e);
        }
    }

    /**
     * 停止调度任务
     * @param key 触发器key
     */
    public static void cancelSchedule(TriggerKey key) {
        try {
            if (scheduler.checkExists(key)) {
                scheduler.unscheduleJob(key);
            }
        } catch (SchedulerException e) {
            handleException(e);
        }
    }

    /**
     * 停止调度任务
     * @param name 触发器名称
     * @param group 触发器分组
     */
    public static void cancelSchedule(String name, String group) {
        TriggerKey key = TriggerKey.triggerKey(name, group);
        try {
            if (scheduler.checkExists(key)) {
                scheduler.unscheduleJob(key);
            }
        } catch (SchedulerException e) {
            handleException(e);
        }
    }
}
