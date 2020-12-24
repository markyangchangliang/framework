package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 系统日志(Log)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:26:45
 */
@ApiModel(value = "系统日志实体类", description = "系统日志实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_log`")
public class Log extends AbstractBaseEntity {

    private static final long serialVersionUID = 593332368612623355L;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id,varchar(128)", position = 1)
    private String userId;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名,varchar(64)", position = 2)
    private String username;
    /**
     * 用户操作
     */
    @ApiModelProperty(value = "用户操作,varchar(64)", position = 3)
    private String operation;
    /**
     * 响应时间
     */
    @ApiModelProperty(value = "响应时间,int(11)", position = 4)
    private Integer time;
    /**
     * 请求方法
     */
    @ApiModelProperty(value = "请求方法,varchar(256)", position = 5)
    private String method;
    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数,varchar(4096)", position = 6)
    private String params;
    /**
     * IP地址
     */
    @ApiModelProperty(value = "IP地址,varchar(64)", position = 7)
    private String ip;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间,datetime", position = 8)
    private LocalDateTime gmtCreate;

}