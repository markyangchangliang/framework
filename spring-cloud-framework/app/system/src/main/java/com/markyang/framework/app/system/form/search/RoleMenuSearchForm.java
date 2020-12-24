package com.markyang.framework.app.system.form.search;

import com.markyang.framework.service.core.form.SearchBaseForm;
import com.markyang.framework.service.core.search.ConditionEnum;
import com.markyang.framework.service.core.search.SearchCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 角色与菜单对应关系(RoleMenu)搜索表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-09 09:12:33
 */
@ApiModel(value = "角色与菜单对应关系搜索表单", description = "角色与菜单对应关系搜索表单类")
@NoArgsConstructor
@SuperBuilder
@Data
public class RoleMenuSearchForm implements SearchBaseForm {


  /**
   * 角色ID
   */
  @ApiModelProperty(value = "角色ID,bigint", allowableValues = "", position = 1)
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private String roleId;
  /**
   * 菜单ID
   */
  @ApiModelProperty(value = "菜单ID,bigint", allowableValues = "", position = 2)
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private String menuId;

}