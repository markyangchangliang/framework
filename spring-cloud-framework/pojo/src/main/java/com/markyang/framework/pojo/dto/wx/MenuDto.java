package com.markyang.framework.pojo.dto.wx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义菜单模型
 * @author yangchangliang
 */
@ApiModel(value = "自定义菜单实体类", description = "自定义菜单实体类")
@Data
public class MenuDto implements Serializable {


  private static final long serialVersionUID = -7083914585539687746L;
  /**
   * 菜单id
   */
  @ApiModelProperty(value = "菜单id,varchar", required = true, position = 9)
  private String menuId;
  /**
   * 菜单实体列表
   */
  @ApiModelProperty(value = "菜单实体列表", required = true, position = 1)
  private List<ButtonDto> button;
  /**
   * 微信个性化菜单实体
   */
  @ApiModelProperty(value = "微信个性化菜单实体", required = false, position = 3)
  private List<MenuDto> conditionalmenu;
  /**
   * 微信标签
   */
  @ApiModelProperty(value = "标签,varchar", required = false, position = 2)
  private RuleDto rule;
}