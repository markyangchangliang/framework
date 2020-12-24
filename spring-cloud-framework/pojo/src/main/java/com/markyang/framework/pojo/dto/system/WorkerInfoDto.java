package com.markyang.framework.pojo.dto.system;

import com.markyang.framework.pojo.entity.support.DtoDictField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 职员信息
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/9 9:37 下午 星期六
 */
@ApiModel(value = "职员信息", description = "职员信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerInfoDto {

    @ApiModelProperty(value = "职员编号", position = 1)
    private String id;

    @ApiModelProperty(value = "职员姓名", position = 2)
    private String name;

    @ApiModelProperty(value = "性别", position = 3)
    @DtoDictField(tableName = "generic")
    private String gender;

    @ApiModelProperty(value = "性别含义", position = 4)
    private String genderName;

    @ApiModelProperty(value = "职称", position = 5)
    @DtoDictField(tableName = "sys_worker")
    private String positional;

    @ApiModelProperty(value = "职称含义", position = 6)
    private String positionalName;

    @ApiModelProperty(value = "职员诊查费", position = 7)
    private BigDecimal diagnoseFee;

    @ApiModelProperty(value = "职员简介", position = 8)
    private String intro;

    @ApiModelProperty(value = "照片", position = 9)
    private String pic;

    @ApiModelProperty(value = "部门编号", position = 10)
    private String deptId;

    @ApiModelProperty(value = "部门名称", position = 11)
    private String deptName;

    @ApiModelProperty(value = "机构编号", position = 12)
    private String orgId;

    @ApiModelProperty(value = "机构名称", position = 13)
    private String orgName;

    @ApiModelProperty(value = "his部门编号", position = 14)
    private String hisDeptId;

    @ApiModelProperty(value = "his职员编号", position = 15)
    private String hisWorkerId;

}
