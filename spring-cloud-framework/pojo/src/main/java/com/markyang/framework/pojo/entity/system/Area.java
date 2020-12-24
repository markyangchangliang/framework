package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 区域信息(Area)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:26:45
 */
@ApiModel(value = "区域信息实体类", description = "区域信息实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_area`")
public class Area extends AbstractBaseEntity {

    private static final long serialVersionUID = -36643885476644621L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号,varchar(32)", required = true, position = 1)
    private String code;
    /**
     * 父编号
     */
    @ApiModelProperty(value = "父编号,varchar(32)", required = true, position = 2)
    private String parentCode;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称,varchar(256)", required = true, position = 3)
    private String name;
    /**
     * 拼音
     */
    @ApiModelProperty(value = "拼音,varchar(256)", position = 4)
    private String pinyin;
    /**
     * 级别
     */
    @ApiModelProperty(value = "级别,varchar(8)", required = true, position = 5)
    private String level;
    /**
     * 全名
     */
    @ApiModelProperty(value = "全名,varchar(512)", required = true, position = 6)
    private String fullName;

}