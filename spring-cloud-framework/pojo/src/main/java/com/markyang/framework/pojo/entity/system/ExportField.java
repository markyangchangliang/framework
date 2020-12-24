package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 导出数据字段(ExportField)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:26:45
 */
@ApiModel(value = "导出数据字段实体类", description = "导出数据字段实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_export_field`")
public class ExportField extends AbstractBaseEntity {

    private static final long serialVersionUID = 136879863454493626L;

    /**
     * 表编号
     */
    @ApiModelProperty(value = "表编号,bigint(20)", required = true, position = 1)
    private String exportTableId;
    /**
     * 序号
     */
    @ApiModelProperty(value = "序号,int(11)", required = true, position = 2)
    private Integer seq;
    /**
     * 字段名称
     */
    @ApiModelProperty(value = "字段名称,varchar(32)", required = true, position = 3)
    private String name;
    /**
     * 字段类型
     */
    @ApiModelProperty(value = "字段类型,varchar(32)", required = true, position = 4)
    private String type;
    /**
     * 字段长度
     */
    @ApiModelProperty(value = "字段长度,int(11)", position = 5)
    private Integer length;
    /**
     * 小数位
     */
    @ApiModelProperty(value = "小数位,int(11)", position = 6)
    private Integer decimalLength;
    /**
     * 默认值
     */
    @ApiModelProperty(value = "默认值,varchar(128)", position = 7)
    private String defaultValue;
    /**
     * 转义类型
     */
    @ApiModelProperty(value = "转义类型,varchar(32)", required = true, position = 8)
    private String transType;
    /**
     * 表名
     */
    @ApiModelProperty(value = "表名,varchar(64)", required = true, position = 9)
    private String tableName;
    /**
     * 字典名
     */
    @ApiModelProperty(value = "字典名,varchar(64)", required = true, position = 10)
    private String fieldName;

}