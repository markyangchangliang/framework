package com.markyang.framework.pojo.entity.message;

import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.auditor.CreatedOrgId;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 消息配置(MessageChannel)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-23 17:27:38
 */
@ApiModel(value = "消息配置实体类", description = "消息配置实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`msg_message_channel`")
public class MessageChannel extends AbstractBaseEntity {

    private static final long serialVersionUID = 188155477397542174L;

    /**
     * 机构ID
     */
    @ApiModelProperty(value = "机构ID,bigint", allowableValues = "", required = true, position = 1)
    @CreatedOrgId
    private String orgId;
    /**
     * 消息通道名称
     */
    @ApiModelProperty(value = "消息通道名称,varchar(32)", allowableValues = "", required = true, position = 2)
    private String name;
    /**
     * 通道配置
     */
    @ApiModelProperty(value = "通道配置,json", allowableValues = "", required = false, position = 3)
    private String configuration;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注,varchar(256)", allowableValues = "", required = false, position = 4)
    private String remark;

}