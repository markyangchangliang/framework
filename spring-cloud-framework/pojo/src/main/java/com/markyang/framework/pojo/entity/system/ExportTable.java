package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 导出数据表名(ExportTable)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:26:45
 */
@ApiModel(value = "导出数据表名实体类", description = "导出数据表名实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_export_table`")
public class ExportTable extends AbstractBaseEntity {

    private static final long serialVersionUID = 551173166571889594L;

    /**
     * 类别
     */
    @ApiModelProperty(value = "类别,varchar(16)", required = true, position = 1)
    private String type;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称,varchar(256)", required = true, position = 2)
    private String name;
    /**
     * sql语句
     */
    @ApiModelProperty(value = "sql语句,text", position = 3)
    private String querySql;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注,varchar(512)", position = 4)
    private String remark;

}