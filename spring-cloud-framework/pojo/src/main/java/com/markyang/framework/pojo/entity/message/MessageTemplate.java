package com.markyang.framework.pojo.entity.message;

import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.auditor.CreatedOrgId;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 消息模板(MessageTemplate)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-23 17:27:38
 */
@ApiModel(value = "消息模板实体类", description = "消息模板实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`msg_message_template`")
public class MessageTemplate extends AbstractBaseEntity {

    private static final long serialVersionUID = -21769402644985559L;

    /**
     * 机构ID
     */
    @ApiModelProperty(value = "机构ID,bigint", allowableValues = "", required = true, position = 1)
    @CreatedOrgId
    private String orgId;
    /**
     * 通道名称
     */
    @ApiModelProperty(value = "通道名称,varchar(32)", allowableValues = "", required = true, position = 2)
    private String channelName;
    /**
     * 业务标识符
     */
    @ApiModelProperty(value = "业务标识符,varchar(64)", allowableValues = "", required = true, position = 3)
    private String businessKey;
    /**
     * 模板ID
     */
    @ApiModelProperty(value = "模板ID,varchar(128)", allowableValues = "", required = true, position = 4)
    private String templateId;
    /**
     * 模板名称
     */
    @ApiModelProperty(value = "模板名称,varchar(32)", allowableValues = "", required = false, position = 4)
    private String templateName;
    /**
     * 模板内容
     */
    @ApiModelProperty(value = "模板内容,text", allowableValues = "", required = false, position = 5)
    private String templateContent;
    /**
     * 模板参数映射
     */
    @ApiModelProperty(value = "模板参数映射,json", allowableValues = "", position = 6)
    private String templateParameterMappings;

}