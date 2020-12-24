package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import com.markyang.framework.pojo.entity.support.DictField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 部门职员(DeptWorker)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 15:52:46
 */
@ApiModel(value = "部门职员实体类", description = "部门职员实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_dept_worker`")
public class DeptWorker extends AbstractBaseEntity {

    private static final long serialVersionUID = -11083125873612845L;

    /**
     * 部门编号
     */
    @ApiModelProperty(value = "部门编号", required = true, position = 1)
    private String deptId;
    /**
     * 职员编号
     */
    @ApiModelProperty(value = "职员编号", required = true, position = 2)
    private String workerId;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", required = true, position = 3)
    @DictField
    private String status;

    /**
     * 状态名称
     */
    @ApiModelProperty(value = "状态名称,varchar", position = 4)
    @TableField(exist = false)
    private String statusName;

}