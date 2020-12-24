package com.markyang.framework.service.message.form.search;

import com.markyang.framework.service.core.form.SearchBaseForm;
import com.markyang.framework.service.core.search.ConditionEnum;
import com.markyang.framework.service.core.search.SearchCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


 /**
 * 消息配置(MessageChannel)搜索表单
 *
 * @author yangchangliang
 * @version 1
 */
@ApiModel(value = "消息配置搜索表单", description = "消息配置搜索表单类")
@Data
public class MessageChannelSearchForm implements SearchBaseForm {

    /**
     * 搜索条件参数
     */
    @ApiModelProperty(value = "通道名称", allowableValues = "", required = true, position = 1, notes = "")
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String name;
}