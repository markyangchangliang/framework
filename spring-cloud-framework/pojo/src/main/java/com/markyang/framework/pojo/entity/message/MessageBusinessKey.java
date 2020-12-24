package com.markyang.framework.pojo.entity.message;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.auditor.CreatedOrgId;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import com.markyang.framework.pojo.entity.support.DictField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 业务key信息表(MessageBusinessKey)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-05-11 15:24:07
 */
@ApiModel(value = "业务key信息表实体类", description = "业务key信息表实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`msg_message_business_key`")
public class MessageBusinessKey extends AbstractBaseEntity {

    private static final long serialVersionUID = -11533681568049209L;

    /**
     * 机构ID
     */
    @ApiModelProperty(value = "机构ID,varchar(128)", allowableValues = "", required = true, position = 1)
    @CreatedOrgId
    private String orgId;
    /**
     * 业务key名称
     */
    @ApiModelProperty(value = "业务key名称,varchar(32)", allowableValues = "", required = true, position = 1)
    private String keyName;
    /**
     * 执行类型，a->直接执行，b->按规则执行，c->禁止执行
     */
    @ApiModelProperty(value = "执行类型，a->直接执行，b->按规则执行，c->禁止执行,char(1)", allowableValues = "", position = 2)
    @DictField
    private String executionType;
    /**
     * 执行类型名称
     */
    @TableField(exist = false)
    private String executionTypeName;
    /**
     * 规则名称
     */
    @ApiModelProperty(value = "规则名称,varchar(32)", allowableValues = "", position = 3)
    private String ruleName;
    /**
     * 执行通道名称
     */
    @ApiModelProperty(value = "执行通道名称,varchar(128)", allowableValues = "", position = 4)
    private String channelNames;
    /**
     * 参数DTO
     */
    @ApiModelProperty(value = "参数DTO,varchar(128)", allowableValues = "", position = 5)
    private String parameterDto;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注,varchar(256)", allowableValues = "", position = 6)
    private String remark;

}