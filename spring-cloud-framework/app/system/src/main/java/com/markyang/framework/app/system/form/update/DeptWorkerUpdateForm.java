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
 * 部门职员(DeptWorker)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-28 13:35:33
 */
@ApiModel(value = "部门职员数据表单", description = "部门职员表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class DeptWorkerUpdateForm extends AbstractUpdateBaseForm {

    /**
    * 部门编号
    */    
    @ApiModelProperty(value = "部门编号", allowableValues = "", required = true, position = 1, notes = "")
	@NotNull(message = "部门编号{required}")
    private String deptId;
    
    /**
    * 职员编号
    */    
    @ApiModelProperty(value = "职员编号", allowableValues = "", required = true, position = 2, notes = "")
	@NotNull(message = "职员编号{required}")
    private String workerId;
    
    /**
    * 状态
    */    
    @ApiModelProperty(value = "状态", allowableValues = "", required = true, position = 3, notes = "")
    @NotNull(message = "状态{required}")
    private String status;    
    
}