package com.markyang.framework.pojo.dto.wx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 菜单实体类
 * @author yangchangliang
 */
@ApiModel(value = "菜单实体类", description = "菜单实体类")
@Data
public class ButtonDto {
  /**
   * appId
   */
  @ApiModelProperty(value = "appId,varchar", required = true, position = 1)
  private String appId;
  /**
   * key
   */
  @ApiModelProperty(value = "key,varchar", required = true, position = 2)
  private String key;
  /**
   * mediaId
   */
  @ApiModelProperty(value = "mediaId,varchar", required = true, position = 3)
  private String mediaId;
  /**
   * 名称或标题
   */
  @ApiModelProperty(value = "名称或标题,varchar", required = true, position = 4)
  private String name;
  /**
   * 页面路径
   */
  @ApiModelProperty(value = "页面路径,varchar", required = true, position = 5)
  private String pagePath;
  /**
   * 子菜单
   */
  @ApiModelProperty(value = "子菜单", required = true, position = 6)
  private List<ButtonDto> sub_button;
  /**
   * 菜单事件类型
   */
  @ApiModelProperty(value = "菜单事件类型,varchar", required = true, position = 9)
  private String type;
  /**
   * 跳转地址
   */
  @ApiModelProperty(value = "跳转地址,varchar", required = true, position = 11)
  private String url;
}
