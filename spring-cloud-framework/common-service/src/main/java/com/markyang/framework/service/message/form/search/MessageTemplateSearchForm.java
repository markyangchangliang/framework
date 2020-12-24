package com.markyang.framework.service.message.form.search;

import com.markyang.framework.service.core.form.SearchBaseForm;
import io.swagger.annotations.ApiModel;
import lombok.Data;


 /**
 * 消息模板(MessageTemplate)搜索表单
 *
 * @author yangchangliang
 * @version 1
 */
@ApiModel(value = "消息模板搜索表单", description = "消息模板搜索表单类")
@Data
public class MessageTemplateSearchForm implements SearchBaseForm {

    /**
     * 搜索条件参数
     */
//    @ApiModelProperty(value = "参数", allowableValues = "", required = true, position = 1, notes = "")
//    @SearchCondition(strategy = ConditionEnum.LIKE)
//    private String name;
}