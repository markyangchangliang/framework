package com.markyang.framework.app.system.form.search;

import com.markyang.framework.service.core.search.ConditionEnum;
import com.markyang.framework.service.core.search.SearchCondition;
import com.markyang.framework.service.core.form.SearchBaseForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 系统用户(User)搜索表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:23:13
 */
@ApiModel(value = "系统用户搜索表单", description = "系统用户搜索表单类")
@NoArgsConstructor
@SuperBuilder
@Data
public class UserSearchForm implements SearchBaseForm {

    /**
     * 搜索条件参数
     */
    @ApiModelProperty(value = "参数", allowableValues = "", required = true, position = 1)
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String username;
}