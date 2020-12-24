package com.markyang.framework.app.system.form.update;

import com.markyang.framework.service.core.form.AbstractUpdateBaseForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;


/**
 * 职员诊察费用(WorkerDiognoseFee)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:30:07
 */
@ApiModel(value = "职员诊察费用数据表单", description = "职员诊察费用表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class WorkerDiagnoseFeeUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 机构编号
     */
    @ApiModelProperty(value = "机构编号", allowableValues = "", required = true, position = 1)
    @NotNull(message = "机构编号{required}")
    private String orgId;

    /**
     * 职称
     */
    @ApiModelProperty(value = "职称", allowableValues = "", position = 2)
    private String positional;

    /**
     * 费用
     */
    @ApiModelProperty(value = "费用", allowableValues = "", position = 3)
    private Double fee;

}