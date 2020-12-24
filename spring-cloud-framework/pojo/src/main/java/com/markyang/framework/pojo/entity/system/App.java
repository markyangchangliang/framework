package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.BaseEntity;
import com.markyang.framework.pojo.entity.support.DictField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 应用管理(App)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:48:18
 */
@ApiModel(value = "应用管理实体类", description = "应用管理实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_app`")
public class App extends BaseEntity {

    private static final long serialVersionUID = -48986622070727025L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", reference = "varchar(128)", required = true, position = 0)
    @TableId(type = IdType.NONE)
    protected String id;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", reference = "varchar(128)", required = true, position = 1)
    private String name;
    /**
     * 数据库名称
     */
    @ApiModelProperty(value = "数据库名称", reference = "varchar(128)", position = 2)
    private String dbName;
    /**
     * 类别
     */
    @ApiModelProperty(value = "类别", reference = "char(1)", required = true, position = 3)
    @DictField
    private String type;
    /**
     * 类别
     */
    @ApiModelProperty(value = "类别,varchar", position = 6)
    @TableField(exist = false)
    private String typeName;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址", reference = "varchar(128)", position = 4)
    private String url;
    /**
     * 图标
     */
    @ApiModelProperty(value = "图标", reference = "varchar(128)", position = 5)
    private String icon;

}