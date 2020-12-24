package com.markyang.framework.pojo.dto.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 表信息
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/29 4:58 下午 星期五
 */
@ApiModel(value = "列信息", description = "列信息")
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnInfoDto {

    /**
     * 字段解释列
     */
    @ApiModelProperty("字段解释列")
    private String column0;
    /**
     * 字段名称列
     */
    @ApiModelProperty("字段名称列")
    private String column1;
    /**
     * 字段类型列
     */
    @ApiModelProperty("字段类型列")
    private String column2;
    /**
     * 非空类型列
     */
    @ApiModelProperty("非空类型列")
    private String column3;
    /**
     * 备注字段名称列
     */
    @ApiModelProperty("备注字段名称列")
    private String column4;
    /**
     * 备注列
     */
    @ApiModelProperty("备注列")
    private String column5;
    /**
     * 是否组合
     */
    @ApiModelProperty("是否组合")
    private String isCombine;
    /**
     * 序号
     */
    @ApiModelProperty("序号")
    private Integer seq;
}
