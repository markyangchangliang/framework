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
 * 职员管理(Worker)搜索表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:23:13
 */
@ApiModel(value = "职员管理搜索表单", description = "职员管理搜索表单类")
@NoArgsConstructor
@SuperBuilder
@Data
public class WorkerSearchForm implements SearchBaseForm {

    /**
     * 搜索条件参数
     */
    @ApiModelProperty(value = "机构编号", allowableValues = "", required = true, position = 1)
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String orgId;

    @ApiModelProperty(value = "部门编码", allowableValues = "", required = true, position = 1)
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String deptId;

    @ApiModelProperty(value = "名字", allowableValues = "", required = true, position = 2)
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String name;


    @ApiModelProperty(value = "类型", allowableValues = "", required = true, position = 3)
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String type;


    @ApiModelProperty(value = "职称", allowableValues = "", required = true, position = 4)
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String positional;

    @ApiModelProperty(value = "职务", allowableValues = "", required = true, position = 5)
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String post;

    @ApiModelProperty(value = "职务", allowableValues = "", required = true, position = 5)
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String positionGrade;

    @ApiModelProperty(value = "是否可以在线业务", allowableValues = "", required = true, position = 5)
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String online;



}