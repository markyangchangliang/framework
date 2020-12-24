package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 定时任务(Task)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:26:45
 */
@ApiModel(value = "定时任务实体类", description = "定时任务实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_task`")
public class Task extends AbstractBaseEntity {

    private static final long serialVersionUID = 442867954144762643L;

    /**
     * 任务名
     */
    @ApiModelProperty(value = "任务名,varchar(128)", position = 1)
    private String jobName;
    /**
     * 任务分组
     */
    @ApiModelProperty(value = "任务分组,varchar(64)", position = 2)
    private String jobGroup;
    /**
     * cron表达式
     */
    @ApiModelProperty(value = "cron表达式,varchar(128)", position = 3)
    private String cronExpression;
    /**
     * 任务描述
     */
    @ApiModelProperty(value = "任务描述,varchar(256)", position = 4)
    private String description;
    /**
     * 任务是否有状态
     */
    @ApiModelProperty(value = "任务是否有状态,varchar(256)", position = 5)
    private String isConcurrent;
    /**
     * 任务执行时调用哪个类的方法 包名+类名
     */
    @ApiModelProperty(value = "任务执行时调用哪个类的方法 包名+类名,varchar(256)", position = 6)
    private String beanClass;
    /**
     * 任务调用的方法名
     */
    @ApiModelProperty(value = "任务调用的方法名,varchar(256)", position = 7)
    private String methodName;
    /**
     * Spring bean
     */
    @ApiModelProperty(value = "Spring bean,varchar(256)", position = 8)
    private String springBean;
    /**
     * 任务状态
     */
    @ApiModelProperty(value = "任务状态,varchar(256)", position = 9)
    private String jobStatus;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间,datetime", position = 10)
    private LocalDateTime executeDate;

}