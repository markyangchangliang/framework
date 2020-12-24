package com.markyang.framework.app.system.form.search;

import com.markyang.framework.service.core.form.SearchBaseForm;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 用户便捷菜单(ConvenienceUserMenu)搜索表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-05-08 17:53:05
 */
@ApiModel(value = "用户便捷菜单搜索表单", description = "用户便捷菜单搜索表单类")
@NoArgsConstructor
@SuperBuilder
@Data
public class ConvenienceUserMenuSearchForm implements SearchBaseForm {

    /**
     * 搜索条件参数
     */
//    @ApiModelProperty(value = "参数", allowableValues = "", required = true, position = 1, notes = "")
//    @SearchCondition(strategy = ConditionEnum.LIKE)
//    private String name;
}