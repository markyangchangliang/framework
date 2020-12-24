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


/**
 * 定时任务日志(JobLog)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:23:13
 */
@ApiModel(value = "定时任务日志数据表单", description = "定时任务日志表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class JobLogUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 任务编号
     */
    @ApiModelProperty(value = "任务编号", allowableValues = "", required = true, position = 1)
    @Size(max = 128, message = "任务编号{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "任务编号{required}")
    private String jobId;

    /**
     * 任务名
     */
    @ApiModelProperty(value = "任务名", allowableValues = "", required = true, position = 2)
    @Size(max = 128, message = "任务名{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "任务名{required}")
    private String job;

    /**
     * 任务名
     */
    @ApiModelProperty(value = "任务名", allowableValues = "", required = true, position = 3)
    @Size(max = 32, message = "任务名{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "任务名{required}")
    private String type;

    /**
     * 日志内容
     */
    @ApiModelProperty(value = "日志内容", allowableValues = "", required = true, position = 4)
    @Size(max = 4096, message = "日志内容{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "日志内容{required}")
    private String content;

    /**
     * 执行时间
     */
    @ApiModelProperty(value = "执行时间", allowableValues = "", required = true, position = 5)
    @NotNull(message = "执行时间{required}")
    private Long time;

}