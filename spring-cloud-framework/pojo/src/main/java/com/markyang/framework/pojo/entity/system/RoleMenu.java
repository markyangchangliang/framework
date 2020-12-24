package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 角色与菜单对应关系(RoleMenu)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-08 19:04:26
 */
@ApiModel(value = "角色与菜单对应关系实体类", description = "角色与菜单对应关系实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_role_menu`")
public class RoleMenu extends AbstractBaseEntity {

    private static final long serialVersionUID = -51815171515120137L;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID,bigint", allowableValues = "", position = 1)
    private String roleId;
    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单ID,bigint", allowableValues = "", position = 2)
    private String menuId;

}