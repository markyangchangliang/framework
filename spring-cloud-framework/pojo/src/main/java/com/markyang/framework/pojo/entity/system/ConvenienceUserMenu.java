package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

 /**
 * 用户便捷菜单(SysConvenienceUserMenu)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-05-08 17:32:09
 */
@ApiModel(value = "用户便捷菜单实体类", description = "用户便捷菜单实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_convenience_user_menu`")
public class ConvenienceUserMenu extends AbstractBaseEntity {

    private static final long serialVersionUID = -85224375969556760L;
    
    /**
    * 用户菜单
    */
    @ApiModelProperty(value = "用户菜单,varchar(128)", allowableValues = "", required = true, position = 1)
    private String userId;
    /**
    * 菜单ID
    */
    @ApiModelProperty(value = "菜单ID,varchar(128)", allowableValues = "", required = true, position = 2)
    private String menuId;
    /**
    * 排序
    */
    @ApiModelProperty(value = "排序,int", allowableValues = "", position = 3)
    private Integer seq;

}