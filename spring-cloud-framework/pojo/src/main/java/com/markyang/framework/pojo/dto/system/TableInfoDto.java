package com.markyang.framework.pojo.dto.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 表信息
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/29 5:08 下午 星期五
 */
@ApiModel(value = "表信息", description = "表信息")
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableInfoDto {

    /**
     * 树显示名称
     */
    @ApiModelProperty("树显示名称")
    private String label;
    /**
     * 数据库名称
     */
    @ApiModelProperty("数据库名称")
    private String databaseName;
    /**
     * 表名称
     */
    @ApiModelProperty("表名称")
    private String tableName;
    /**
     * 表注释
     */
    @ApiModelProperty("表注释")
    private String tableComment;
    /**
     * 表列信息
     */
    @ApiModelProperty("表列信息")
    private List<ColumnInfoDto> tableColumns;
}
