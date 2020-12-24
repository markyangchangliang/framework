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
 * 数据字典(FieldDict)搜索表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:23:13
 */
@ApiModel(value = "数据字典搜索表单", description = "数据字典搜索表单类")
@NoArgsConstructor
@SuperBuilder
@Data
public class FieldDictSearchForm implements SearchBaseForm {

    /**
     * 应用ID
     */
    @ApiModelProperty(value = "应用ID", allowableValues = "", position = 1)
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String appId;

    /**
     * 表名
     */
    @ApiModelProperty(value = "表名", allowableValues = "", position = 1)
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String tableName;

    /**
     * 字典名
     */
    @ApiModelProperty(value = "字典名", allowableValues = "", position = 1)
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String fieldName;

    /**
     * 字段值
     */
    @ApiModelProperty(value = "字段值", allowableValues = "", position = 1)
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String fieldValue;

    /**
     * 字段含义
     */
    @ApiModelProperty(value = "字段含义", allowableValues = "", position = 1)
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String fieldMean;
}