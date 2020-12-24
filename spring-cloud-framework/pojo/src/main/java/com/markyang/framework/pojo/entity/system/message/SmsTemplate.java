package com.markyang.framework.pojo.entity.system.message;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.support.DictField;
import com.markyang.framework.pojo.auditor.CreatedOrgId;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 短信模板(SmsTemplate)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-05-28 17:32:20
 */
@ApiModel(value = "短信模板实体类", description = "短信模板实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`msg_sms_template`")
public class SmsTemplate extends AbstractBaseEntity {

    private static final long serialVersionUID = 316849657787915359L;
    /**
     * 短信通道
     */
    @ApiModelProperty(value = "短信通道,varchar(128)", allowableValues = "", required = true, position = 2)
    @DictField
    private String channel;
    /**
     * 短信通道名称
     */
    @ApiModelProperty(value = "短信通道名称,varchar(128)", allowableValues = "", required = false, position = 2)
    @TableField(exist = false)
    private String channelName;
    /**
     * 机构编号
     */
    @ApiModelProperty(value = "机构编号,varchar(128)", allowableValues = "", required = false, position = 1)
    @CreatedOrgId
    private String orgId;
    /**
     * 模板审核结果
     */
    @ApiModelProperty(value = "模板审核结果,varchar(256)", allowableValues = "", position = 8)
    @DictField
    private String templateAuditResult;
    /**
     * 模板审核结果名称
     */
    @ApiModelProperty(value = "模板审核结果名称,varchar(256)", allowableValues = "", position = 8)
    @TableField(exist = false)
    private String templateAuditResultName;
    /**
     * 模板内容
     */
    @ApiModelProperty(value = "模板内容,varchar(1024)", allowableValues = "", required = true, position = 6)
    private String templateContent;
    /**
     * 模板编号
     */
    @ApiModelProperty(value = "模板编号,varchar(64)", allowableValues = "", required = false, position = 4)
    private String templateId;
    /**
     * 模板名称
     */
    @ApiModelProperty(value = "模板名称,varchar(256)", allowableValues = "", required = true, position = 5)
    private String templateName;
    /**
     * 模板备注 用于申请腾讯云短信模板
     */
    @ApiModelProperty(value = "模板备注用于申请腾讯云短信模板,varchar(255)", allowableValues = "", required = true, position = 6)
    private String templateRemark;
    /**
     * 模板状态
     */
    @ApiModelProperty(value = "模板状态,char(1)", allowableValues = "", required = true, position = 7)
    @DictField
    private String templateStatus;
    /**
     * 模板状态名称
     */
    @ApiModelProperty(value = "模板状态名称,varchar(64)", allowableValues = "", required = false, position = 7)
    @TableField(exist = false)
    private String templateStatusName;
    /**
     * 类别
     */
    @ApiModelProperty(value = "类别,char(1)", allowableValues = "", required = true, position = 3)
    @DictField
    private String type;
    /**
     * 类别名称
     */
    @ApiModelProperty(value = "类别名称,varchar(64)", allowableValues = "", required = false, position = 3)
    @TableField(exist = false)
    private String typeName;

}