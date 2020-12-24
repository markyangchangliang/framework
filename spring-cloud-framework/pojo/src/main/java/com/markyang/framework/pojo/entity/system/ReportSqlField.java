package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 报表转义字段(ReportSqlField)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:26:45
 */
@ApiModel(value = "报表转义字段实体类", description = "报表转义字段实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_report_sql_field`")
public class ReportSqlField extends AbstractBaseEntity {

    private static final long serialVersionUID = -22595040628115218L;

    /**
     * 表编号
     */
    @ApiModelProperty(value = "表编号,bigint(20)", required = true, position = 1)
    private String rptSqlId;
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
     * 转义类型
     */
    @ApiModelProperty(value = "转义类型,varchar(32)", required = true, position = 4)
    private String transType;
    /**
     * 表名
     */
    @ApiModelProperty(value = "表名,varchar(64)", position = 5)
    private String tableName;
    /**
     * 字典名
     */
    @ApiModelProperty(value = "字典名,varchar(64)", position = 6)
    private String fieldName;

}