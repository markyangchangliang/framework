package com.markyang.framework.app.system.wx.mp.form.search;

import com.markyang.framework.service.core.form.SearchBaseForm;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 微信自定义菜单(MpMenu)搜索表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:10:42
 */
@ApiModel(value = "微信自定义菜单搜索表单", description = "微信自定义菜单搜索表单类")
@NoArgsConstructor
@SuperBuilder
@Data
public class MpMenuSearchForm implements SearchBaseForm {

  /**
   * 搜索条件参数
   */
//    @ApiModelProperty(value = "参数", allowableValues = "", required = true, position = 1, notes = "")
//    @SearchCondition(strategy = ConditionEnum.LIKE)
//    private String name;
}
