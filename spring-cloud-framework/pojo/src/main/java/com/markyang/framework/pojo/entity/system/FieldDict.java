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
 * 数据字典(FieldDict)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:48:18
 */
@ApiModel(value = "数据字典实体类", description = "数据字典实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_field_dict`")
public class FieldDict extends AbstractBaseEntity {

    private static final long serialVersionUID = 769206194095939192L;

    /**
     * 数据库名称
     */
    @ApiModelProperty(value = "数据库名称，varchar(128)", required = true, position = 1)
    private String appId;
    /**
     * 表名
     */
    @ApiModelProperty(value = "表名，varchar(64)", required = true, position = 2)
    private String tableName;
    /**
     * 字典名
     */
    @ApiModelProperty(value = "字典名，varchar(64)", required = true, position = 3)
    private String fieldName;
    /**
     * 字段值
     */
    @ApiModelProperty(value = "字段值，varchar(64)", required = true, position = 4)
    private String fieldValue;
    /**
     * 字段含义
     */
    @ApiModelProperty(value = "字段含义，varchar(64)", required = true, position = 5)
    private String fieldMean;
    /**
     * 类别
     */
    @ApiModelProperty(value = "类别，char(1)", position = 6)
    @DictField
    private String type;
    /**
     * 类别
     */
    @ApiModelProperty(value = "类别,varchar", position = 6)
    @TableField(exist = false)
    private String typeName;
    /**
     * 显示状态
     */
    @ApiModelProperty(value = "显示状态，char(1)", position = 7)
    @DictField
    private String displayStatus;
    /**
     * 显示状态
     */
    @ApiModelProperty(value = "显示状态,varchar", position = 7)
    @TableField(exist = false)
    private String displayStatusName;
    /**
     * 启用状态
     */
    @ApiModelProperty(value = "启用状态，char(1)", position = 8)
    @DictField
    private String status;
    /**
     * 启用状态
     */
    @ApiModelProperty(value = "启用状态,varchar", position = 8)
    @TableField(exist = false)
    private String statusName;
    /**
     * 序号
     */
    @ApiModelProperty(value = "序号，int(4)", position = 9)
    private Integer seq;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注，varchar(2048)", position = 10)
    private String remark;

}