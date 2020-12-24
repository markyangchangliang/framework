package com.markyang.framework.app.system.form.update;

import com.markyang.framework.service.core.form.AbstractUpdateBaseForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Size;


/**
 * 系统用户(User)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:30:06
 */
@ApiModel(value = "系统用户数据表单", description = "系统用户表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class UserUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 职员编号
     */
    @ApiModelProperty(value = "职员编号", allowableValues = "", position = 1)
    private String workerId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", allowableValues = "", position = 2)
    @Size(max = 128, message = "用户名{size}")
    private String name;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", allowableValues = "", position = 3)
    @Size(max = 64, message = "密码{size}")
    private String pwd;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像", allowableValues = "", position = 4)
    @Size(max = 128, message = "用户头像{size}")
    private String img;

    /**
     * 找回密码问题
     */
    @ApiModelProperty(value = "找回密码问题", allowableValues = "", position = 5)
    @Size(max = 128, message = "找回密码问题{size}")
    private String question;

    /**
     * 找回密码问题答案
     */
    @ApiModelProperty(value = "找回密码问题答案", allowableValues = "", position = 6)
    @Size(max = 128, message = "找回密码问题答案{size}")
    private String answer;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", allowableValues = "", position = 7)
    private String status;

}