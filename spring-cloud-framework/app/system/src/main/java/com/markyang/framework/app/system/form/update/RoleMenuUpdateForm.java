package com.markyang.framework.app.system.form.update;

import com.markyang.framework.service.core.form.AbstractUpdateBaseForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


/**
 * 角色与菜单对应关系(RoleMenu)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-09 09:12:33
 */
@ApiModel(value = "角色与菜单对应关系数据表单", description = "角色与菜单对应关系表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class RoleMenuUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID", allowableValues = "", position = 1, notes = "")
    private String roleId;

    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单ID", allowableValues = "", position = 2, notes = "")
    private String menuId;

}