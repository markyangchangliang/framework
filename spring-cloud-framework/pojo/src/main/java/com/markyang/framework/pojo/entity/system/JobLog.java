package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;


/**
 * 定时任务日志(JobLog)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:48:18
 */
@ApiModel(value = "定时任务日志实体类", description = "定时任务日志实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_job_log`")
public class JobLog extends AbstractBaseEntity {

    private static final long serialVersionUID = -93814236266406044L;
    /**
     * 任务编号
     */
    @ApiModelProperty(value = "任务编号", dataType = "varchar(128)", required = true, position = 1)

    private String jobId;
    /**
     * 任务名
     */
    @ApiModelProperty(value = "任务名", dataType = "varchar(128)", required = true, position = 2)

    private String job;
    /**
     * 任务名
     */
    @ApiModelProperty(value = "任务名", dataType = "varchar(32)", required = true, position = 3)

    private String type;
    /**
     * 日志内容
     */
    @ApiModelProperty(value = "日志内容", dataType = "varchar(4096)", required = true, position = 4)
    private String content;
    /**
     * 执行时间
     */
    @ApiModelProperty(value = "执行时间", dataType = "bigint(20)", required = true, position = 5)
    private Long time;

}