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
 * 机构管理(Org)搜索表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:23:13
 */
@ApiModel(value = "机构管理搜索表单", description = "机构管理搜索表单类")
@NoArgsConstructor
@SuperBuilder
@Data
public class OrgSearchForm implements SearchBaseForm {

    /**
     * 搜索条件参数
     */
    @ApiModelProperty(value = "名字", allowableValues = "", required = false, position = 1)
    @SearchCondition(strategy = ConditionEnum.LIKE)
    String name;

    @ApiModelProperty(value = "编码", allowableValues = "", required = false, position = 1)
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String code;

    @ApiModelProperty(value = "父级编号", allowableValues = "", required = false, position = 1)
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String parentId;
}