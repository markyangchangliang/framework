package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 职员多部门管理(MultiDept)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 15:52:46
 */
@ApiModel(value = "职员多部门管理实体类", description = "职员多部门管理实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_multi_dept`")
public class MultiDept extends AbstractBaseEntity {

    private static final long serialVersionUID = -65656968646412358L;

    /**
     * 机构编号
     */
    @ApiModelProperty(value = "机构编号", required = true, position = 1)
    private String orgId;
    /**
     * 部门编号
     */
    @ApiModelProperty(value = "部门编号", required = true, position = 2)
    private String deptId;
    /**
     * 职员编号
     */
    @ApiModelProperty(value = "职员编号", required = true, position = 3)
    private String workerId;

}