package com.markyang.framework.app.system.form.update;

import com.markyang.framework.service.core.form.AbstractUpdateBaseForm;
import com.markyang.framework.service.core.validator.group.FrameworkGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


/**
 * 定时任务(Job)更新表单
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
public class JobUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 应用id
     */
    @ApiModelProperty(value = "应用id", allowableValues = "", position = 1)
    @Size(max = 128, message = "应用id{size}")
    private String appId;

    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称", allowableValues = "", required = true, position = 2)
    @Size(max = 128, message = "任务名称{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "任务名称{required}")
    private String jobName;

    /**
     * 分组名称
     */
    @ApiModelProperty(value = "分组名称", allowableValues = "", required = true, position = 3)
    @Size(max = 64, message = "分组名称{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "分组名称{required}")
    private String jobGroup;

    /**
     * cron表达式
     */
    @ApiModelProperty(value = "cron表达式", allowableValues = "", required = true, position = 4)
    @Size(max = 128, message = "cron表达式{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "cron表达式{required}")
    private String cronExpression;

    /**
     * 任务描述
     */
    @ApiModelProperty(value = "任务描述", allowableValues = "", position = 5)
    @Size(max = 256, message = "任务描述{size}")
    private String description;

    /**
     * 任务是否有状态
     */
    @ApiModelProperty(value = "任务是否有状态", allowableValues = "", required = true, position = 6)
    @NotNull(message = "任务是否有状态{required}")
    private String isConcurrent;

    /**
     * 包名+类名
     */
    @ApiModelProperty(value = "包名+类名", allowableValues = "", position = 7)
    @Size(max = 128, message = "包名+类名{size}")
    private String beanClass;

    /**
     * 方法名
     */
    @ApiModelProperty(value = "方法名", allowableValues = "", position = 8)
    @Size(max = 64, message = "方法名{size}")
    private String methodName;

    /**
     * spring_bean
     */
    @ApiModelProperty(value = "spring_bean ", allowableValues = "", position = 9)
    @Size(max = 128, message = "spring_bean {size}")
    private String springBean;

    /**
     * 任务状态
     */
    @ApiModelProperty(value = "任务状态", allowableValues = "", required = true, position = 10)
    @NotNull(message = "任务状态{required}")
    private String jobStatus;

    /**
     * 最近执行时间
     */
    @ApiModelProperty(value = "最近执行时间", allowableValues = "", position = 11)
    private LocalDateTime executeDate;

}