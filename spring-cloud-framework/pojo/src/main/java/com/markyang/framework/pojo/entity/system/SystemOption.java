package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 系统选项(SystemOption)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:26:45
 */
@ApiModel(value = "系统选项实体类", description = "系统选项实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_system_option`")
public class SystemOption extends BaseEntity {

    private static final long serialVersionUID = 944095747028040162L;
    /**
     * 主键
     */
    @TableId(type = IdType.NONE)
    protected String id;
    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称,varchar(128)", required = true, position = 1)
    private String appId;
    /**
     * 键名
     */
    @ApiModelProperty(value = "键名,varchar(128)", required = true, position = 1)
    @TableField("`key`")
    private String key;
    /**
     * 键值
     */
    @ApiModelProperty(value = "键值,varchar(128)", required = true, position = 2)
    @TableField("`value`")
    private String value;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注,varchar(1024)", position = 3)
    private String remark;

}