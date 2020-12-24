package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import com.markyang.framework.pojo.entity.support.DictField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 定时任务(Job)实体类
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
@TableName("`sys_job`")
public class Job extends AbstractBaseEntity {

    private static final long serialVersionUID = 732495532164175613L;

    /**
     * 应用id
     */
    @ApiModelProperty(value = "应用id,varchar(128)", position = 1)
    private String appId;
    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称,varchar(128)", required = true, position = 2)
    private String jobName;
    /**
     * 分组名称
     */
    @ApiModelProperty(value = "分组名称,varchar(64)", required = true, position = 3)
    private String jobGroup;
    /**
     * cron表达式
     */
    @ApiModelProperty(value = "cron表达式,varchar(128)", required = true, position = 4)
    private String cronExpression;
    /**
     * 任务描述
     */
    @ApiModelProperty(value = "任务描述,varchar(256)", position = 5)
    private String description;
    /**
     * 任务是否有状态
     */
    @ApiModelProperty(value = "任务是否有状态,char(1)", required = true, position = 6)
    private String isConcurrent;
    /**
     * 包名+类名
     */
    @ApiModelProperty(value = "包名+类名,varchar(128)", position = 7)
    private String beanClass;
    /**
     * 方法名
     */
    @ApiModelProperty(value = "方法名,varchar(64)", position = 8)
    private String methodName;
    /**
     * spring_bean
     */
    @ApiModelProperty(value = "spring_bean ,varchar(128)", position = 9)
    private String springBean;
    /**
     * 任务状态
     */
    @ApiModelProperty(value = "任务状态,char(1)", required = true, position = 10)
    @DictField
    private String jobStatus;
    /**
     * 任务状态
     */
    @ApiModelProperty(value = "",  position = 10)
    @TableField(exist = false)
    private String jobStatusName;
    /**
     * 最近执行时间
     */
    @ApiModelProperty(value = "最近执行时间,datetime", position = 11)
    private LocalDateTime executeDate;

}