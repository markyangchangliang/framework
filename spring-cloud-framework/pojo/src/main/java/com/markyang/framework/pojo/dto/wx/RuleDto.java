package com.markyang.framework.pojo.dto.wx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 标签实体类
 * @author yangchangliang
 */
@ApiModel(value = "标签实体类", description = "标签实体类")
@Data
public class RuleDto {
  /**
   * 城市
   */
  @ApiModelProperty(value = "城市,varchar", required = true, position = 1)
  private String city;
  /**
   * 客户平台
   */
  @ApiModelProperty(value = "客户平台,varchar", required = true, position = 1)
  private String clientPlatformType;
  /**
   * 国家
   */
  @ApiModelProperty(value = "国家,varchar", required = true, position = 1)
  private String country;
  /**
   * 语言
   */
  @ApiModelProperty(value = "语言,varchar", required = true, position = 1)
  private String language;
  /**
   * 省份
   */
  @ApiModelProperty(value = "省份,varchar", required = true, position = 1)
  private String province;
  /**
   * 性别
   */
  @ApiModelProperty(value = "性别,varchar", required = true, position = 1)
  private String sex;
  /**
   * 标签ID
   */
  @ApiModelProperty(value = "标签ID,varchar", required = true, position = 1)
  private String tagId;
}
