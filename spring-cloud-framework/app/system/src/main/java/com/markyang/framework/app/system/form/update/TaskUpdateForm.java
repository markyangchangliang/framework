package com.markyang.framework.app.system.form.update;

import com.markyang.framework.service.core.form.AbstractUpdateBaseForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;


/**
 * 定时任务(Task)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:30:07
 */
@ApiModel(value = "定时任务数据表单", description = "定时任务表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class TaskUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 任务名
     */
    @ApiModelProperty(value = "任务名", allowableValues = "", position = 1)
    @Size(max = 128, message = "任务名{size}")
    private String jobName;

    /**
     * 任务分组
     */
    @ApiModelProperty(value = "任务分组", allowableValues = "", position = 2)
    @Size(max = 64, message = "任务分组{size}")
    private String jobGroup;

    /**
     * cron表达式
     */
    @ApiModelProperty(value = "cron表达式", allowableValues = "", position = 3)
    @Size(max = 128, message = "cron表达式{size}")
    private String cronExpression;

    /**
     * 任务描述
     */
    @ApiModelProperty(value = "任务描述", allowableValues = "", position = 4)
    @Size(max = 256, message = "任务描述{size}")
    private String description;

    /**
     * 任务是否有状态
     */
    @ApiModelProperty(value = "任务是否有状态", allowableValues = "", position = 5)
    @Size(max = 256, message = "任务是否有状态{size}")
    private String isConcurrent;

    /**
     * 任务执行时调用哪个类的方法 包名+类名
     */
    @ApiModelProperty(value = "任务执行时调用哪个类的方法 包名+类名", allowableValues = "", position = 6)
    @Size(max = 256, message = "任务执行时调用哪个类的方法 包名+类名{size}")
    private String beanClass;

    /**
     * 任务调用的方法名
     */
    @ApiModelProperty(value = "任务调用的方法名", allowableValues = "", position = 7)
    @Size(max = 256, message = "任务调用的方法名{size}")
    private String methodName;

    /**
     * Spring bean
     */
    @ApiModelProperty(value = "Spring bean", allowableValues = "", position = 8)
    @Size(max = 256, message = "Spring bean{size}")
    private String springBean;

    /**
     * 任务状态
     */
    @ApiModelProperty(value = "任务状态", allowableValues = "", position = 9)
    @Size(max = 256, message = "任务状态{size}")
    private String jobStatus;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", allowableValues = "", position = 10)
    private LocalDateTime executeDate;

}