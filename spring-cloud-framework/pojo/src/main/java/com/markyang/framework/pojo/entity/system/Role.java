package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import com.markyang.framework.pojo.entity.support.DictField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 角色管理(Role)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:26:45
 */
@ApiModel(value = "角色管理实体类", description = "角色管理实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_role`")
public class Role extends AbstractBaseEntity {

    private static final long serialVersionUID = 741135190555890891L;

    /**
     * 应用编号
     */
    @ApiModelProperty(value = "应用编号,varchar(64)", required = true, position = 1)
    private String appId;

    @JsonIgnore
    @TableField(exist = false)
    private App app;
    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称,varchar(128)", required = true, position = 2)
    private String name;
    /**
     * 角色标识
     */
    @ApiModelProperty(value = "角色标识,varchar(128)", position = 3)
    private String intro;

    /**
     * 角色状态
     */
    @ApiModelProperty(value = "角色状态,char(1)", required = true, position = 4)
    @DictField
    private String status;
    /**
     * 角色状态名称
     */
    @ApiModelProperty(value = "角色状态名称,char(1)", required = true, position = 4)
    @TableField(exist = false)
    private String statusName;

}