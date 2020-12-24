package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 帮助文件(Help)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:26:45
 */
@ApiModel(value = "帮助文件实体类", description = "帮助文件实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_help`")
public class Help extends AbstractBaseEntity {

    private static final long serialVersionUID = -46489540959592695L;

    /**
     * 菜单编号
     */
    @ApiModelProperty(value = "菜单编号,bigint(20)", required = true, position = 1)
    private String menuId;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容,longtext", position = 2)
    private String content;
    /**
     * 视频地址
     */
    @ApiModelProperty(value = "视频地址,varchar(512)", position = 3)
    private String videoUrl;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态,char(1)", required = true, position = 4)
    private String status;

}