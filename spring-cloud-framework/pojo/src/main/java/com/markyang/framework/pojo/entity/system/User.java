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
import org.springframework.beans.BeanUtils;

/**
 * 系统用户(User)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:26:45
 */
@ApiModel(value = "系统用户实体类", description = "系统用户实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_user`")
public class User extends AbstractBaseEntity {

    private static final long serialVersionUID = 974501472993686230L;

    public User(User user, String deptName, String workerName, String phone, String wxCpUserId) {
        this.deptName = deptName;
        this.workerName = workerName;
        BeanUtils.copyProperties(user, this, "worker", "deptName", "workerName");
    }

    /**
     * 职员编号
     */
    @ApiModelProperty(value = "职员编号,bigint(20)", position = 1)
    private String workerId;

    @ApiModelProperty(value = "职员名字,varchar(128)", position = 2)
    @TableField(exist = false)
    private String workerName;

    @TableField(exist = false)
    private String deptName;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名,varchar(128)", position = 3)
    private String username;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码,varchar(64)", position = 4)
    @JsonIgnore
    private String password;
    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像,varchar(128)", position = 5)
    private String img;
    /**
     * 找回密码问题
     */
    @ApiModelProperty(value = "找回密码问题,varchar(128)", position = 6)
    private String question;
    /**
     * 找回密码问题答案
     */
    @ApiModelProperty(value = "找回密码问题答案,varchar(128)", position = 7)
    private String answer;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态,char(1)", position = 8)
    @DictField
    private String status;

    /**
     * 状态名称
     */
    @ApiModelProperty(value = "状态名称,varchar", position = 9)
    @TableField(exist = false)
    private String statusName;

}