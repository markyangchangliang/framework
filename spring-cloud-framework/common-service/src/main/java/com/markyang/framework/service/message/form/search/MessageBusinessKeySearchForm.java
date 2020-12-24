package com.markyang.framework.service.message.form.search;

import com.markyang.framework.service.core.form.SearchBaseForm;
import io.swagger.annotations.ApiModel;
import lombok.Data;


 /**
 * 业务key信息表(MessageBusinessKey)搜索表单
 *
 * @author yangchangliang
 * @version 1
 */
@ApiModel(value = "业务key信息表搜索表单", description = "业务key信息表搜索表单类")
@Data
public class MessageBusinessKeySearchForm implements SearchBaseForm {

    /**
     * 搜索条件参数
     */
//    @ApiModelProperty(value = "参数", allowableValues = "", required = true, position = 1, notes = "")
//    @SearchCondition(strategy = ConditionEnum.LIKE)
//    private String name;
}