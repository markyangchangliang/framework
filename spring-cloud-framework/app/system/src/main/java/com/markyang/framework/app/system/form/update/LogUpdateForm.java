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
import java.time.LocalDateTime;


/**
 * 系统日志(Log)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:30:07
 */
@ApiModel(value = "系统日志数据表单", description = "系统日志表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class LogUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", allowableValues = "", position = 1)
    @Size(max = 128, message = "用户id{size}")
    private String user_Id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", allowableValues = "", position = 2)
    @Size(max = 64, message = "用户名{size}")
    private String username;

    /**
     * 用户操作
     */
    @ApiModelProperty(value = "用户操作", allowableValues = "", position = 3)
    @Size(max = 64, message = "用户操作{size}")
    private String operation;

    /**
     * 响应时间
     */
    @ApiModelProperty(value = "响应时间", allowableValues = "", position = 4)
    private Integer time;

    /**
     * 请求方法
     */
    @ApiModelProperty(value = "请求方法", allowableValues = "", position = 5)
    @Size(max = 256, message = "请求方法{size}")
    private String method;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数", allowableValues = "", position = 6)
    @Size(max = 4096, message = "请求参数{size}")
    private String params;

    /**
     * IP地址
     */
    @ApiModelProperty(value = "IP地址", allowableValues = "", position = 7)
    @Size(max = 64, message = "IP地址{size}")
    private String ip;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", allowableValues = "", position = 8)
    private LocalDateTime gmtCreate;

}