package com.markyang.framework.app.system.message.form.search;

import com.markyang.framework.service.core.form.SearchBaseForm;
import com.markyang.framework.service.core.search.ConditionEnum;
import com.markyang.framework.service.core.search.SearchCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 短信模板(SmsTemplate)搜索表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-05-28 17:40:10
 */
@ApiModel(value = "短信模板搜索表单", description = "短信模板搜索表单类")
@NoArgsConstructor
@SuperBuilder
@Data
public class SmsTemplateSearchForm implements SearchBaseForm {

    /**
     * 短信通道
     */
    @ApiModelProperty(value = "短信通道", allowableValues = "", position = 2, notes = "")
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String channel;
    /**
     * 模板审核结果
     */
    @ApiModelProperty(value = "模板审核结果", allowableValues = "", position = 8, notes = "")
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String templateAuditResult;
    /**
     * 模板内容
     */
    @ApiModelProperty(value = "模板内容", allowableValues = "", position = 6, notes = "")
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String templateContent;
    /**
     * 模板编号
     */
    @ApiModelProperty(value = "模板编号", allowableValues = "", position = 4, notes = "")
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String templateId;
    /**
     * 模板名称
     */
    @ApiModelProperty(value = "模板名称", allowableValues = "", position = 5, notes = "")
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String templateName;
    /**
     * 模板备注 用于申请腾讯云短信模板
     */
    @ApiModelProperty(value = "模板备注用于申请腾讯云短信模板,varchar(255)", allowableValues = "", position = 6)
    @SearchCondition(strategy = ConditionEnum.LIKE)
    private String templateRemark;

    /**
     * 模板状态
     */
    @ApiModelProperty(value = "模板状态", allowableValues = "", position = 7, notes = "")
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String templateStatus;
    /**
     * 类别
     */
    @ApiModelProperty(value = "类别", allowableValues = "", position = 3, notes = "")
    @SearchCondition(strategy = ConditionEnum.EQUAL)
    private String type;
}