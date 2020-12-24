package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 报表管理(RptSql)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:26:45
 */
@ApiModel(value = "报表管理实体类", description = "报表管理实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_rpt_sql`")
public class RptSql extends AbstractBaseEntity {

    private static final long serialVersionUID = -85389665559425453L;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称,varchar(256)", required = true, position = 1)
    private String name;
    /**
     * 类别
     */
    @ApiModelProperty(value = "类别,varchar(32)", required = true, position = 2)
    private String type;
    /**
     * sql
     */
    @ApiModelProperty(value = "sql,text", position = 3)
    private String sqlText;

}