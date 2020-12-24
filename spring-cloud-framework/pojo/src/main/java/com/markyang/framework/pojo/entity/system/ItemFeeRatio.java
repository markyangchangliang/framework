package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 项目分成比例(ItemFeeRatio)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:26:45
 */
@ApiModel(value = "项目分成比例实体类", description = "项目分成比例实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_item_fee_ratio`")
public class ItemFeeRatio extends AbstractBaseEntity {

    private static final long serialVersionUID = 388642169185803581L;

    /**
     * 机构编号
     */
    @ApiModelProperty(value = "机构编号,bigint(20)", required = true, position = 1)
    private String orgId;
    /**
     * 部门编号
     */
    @ApiModelProperty(value = "部门编号,bigint(20)", required = true, position = 2)
    private String deptId;
    /**
     * 项目编号
     */
    @ApiModelProperty(value = "项目编号,varchar(128)", required = true, position = 3)
    private String itemId;
    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称,varchar(256)", required = true, position = 4)
    private String itemName;
    /**
     * 检查机构比例
     */
    @ApiModelProperty(value = "检查机构比例,float", required = true, position = 5)
    private Double checkRatio;
    /**
     * 预约机构比例
     */
    @ApiModelProperty(value = "预约机构比例,float", required = true, position = 6)
    private Double applyRatio;
    /**
     * 平台比例
     */
    @ApiModelProperty(value = "平台比例,float", required = true, position = 7)
    private Double platformRatio;

}