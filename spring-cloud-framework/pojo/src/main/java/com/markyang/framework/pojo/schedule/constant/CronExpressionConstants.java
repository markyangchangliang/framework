package com.markyang.framework.pojo.schedule.constant;

/**
 * Cron表达式常量类
 *
 * @author yangchangliang
 * @version 1
 */
public interface CronExpressionConstants {

    /**
     * 查询支付状态cron表达式 每两分钟执行一次
     */
    String QUERY_PAYMENT_STATUS_CRON_EXPRESSION = "0 */2 * * * ? *";
}
