package com.markyang.framework.pojo.dto.system;

import com.markyang.framework.pojo.entity.support.DtoDictField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息Dto
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/4/26 10:32 上午 星期日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户基本信息")
public class UserProfileDto {

    /**
     * 机构名称
     */
    @ApiModelProperty("机构名称")
    private String orgName;
    /**
     * 科室名称
     */
    @ApiModelProperty("科室名称")
    private String deptName;
    /**
     * 职员姓名
     */
    @ApiModelProperty("职员姓名")
    private String workerName;
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;
    /**
     * 性别
     */
    @ApiModelProperty("性别")
    @DtoDictField(tableName = "sys_worker", fieldName = "gender")
    private String gender;
    /**
     * 性别含义
     */
    @ApiModelProperty("性别含义")
    private String genderName;
    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String pic;

}
