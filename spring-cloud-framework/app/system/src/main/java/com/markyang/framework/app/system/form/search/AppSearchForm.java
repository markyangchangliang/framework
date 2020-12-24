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
 * 应用管理(App)搜索表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:23:13
 */
@ApiModel(value = "应用管理搜索表单", description = "应用管理搜索表单类")
@NoArgsConstructor
@SuperBuilder
@Data
public class AppSearchForm implements SearchBaseForm {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", allowableValues = "", position = 1)
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String id;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", allowableValues = "", position = 2)
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String name;
    /**
     * 数据库名称
     */
    @ApiModelProperty(value = "数据库名称", allowableValues = "", position = 3)
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String dbName;
    /**
     * 类别
     */
    @ApiModelProperty(value = "类别", allowableValues = "", position = 4)
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String type;
}