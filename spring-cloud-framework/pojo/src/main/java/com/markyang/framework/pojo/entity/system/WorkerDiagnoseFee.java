package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import com.markyang.framework.pojo.entity.support.DictField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;

/**
 * 职员诊察费用(WorkerDiognoseFee)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:26:45
 */
@ApiModel(value = "职员诊察费用实体类", description = "职员诊察费用实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_worker_diagnose_fee`")
public class WorkerDiagnoseFee extends AbstractBaseEntity {

    private static final long serialVersionUID = 209798276230748216L;

    /**
     * 机构编号
     */
    @ApiModelProperty(value = "机构编号,bigint(20)", required = true, position = 1)
    private String orgId;
    /**
     * 职称
     */
    @ApiModelProperty(value = "职称,char(10)", position = 2)
    @DictField(tableName = "sys_worker")
    private String positional;
    /**
     * 职称名称
     */
    @ApiModelProperty(value = "职称名称,varchar", position = 4)
    @TableField(exist = false)
    private String positionalName;
    /**
     * 费用
     */
    @ApiModelProperty(value = "费用,float", position = 3)
    private BigDecimal fee;
}