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
 * 系统选项(SystemOption)搜索表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:23:13
 */
@ApiModel(value = "系统选项搜索表单", description = "系统选项搜索表单类")
@NoArgsConstructor
@SuperBuilder
@Data
public class SystemOptionSearchForm implements SearchBaseForm {


    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称", allowableValues = "", position = 2)
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String appId;
    /**
     * 数据库名称
     */
    @ApiModelProperty(value = "键名", allowableValues = "", position = 3)
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String key;
    /**
     * 键值
     */
    @ApiModelProperty(value = "键值", allowableValues = "", position = 4)
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String value;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", allowableValues = "", position = 4)
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String remark;
}