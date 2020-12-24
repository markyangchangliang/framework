package com.markyang.framework.app.system.form.update;

import com.markyang.framework.service.core.form.AbstractUpdateBaseForm;
import com.markyang.framework.service.core.validator.group.FrameworkGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


/**
 * 角色管理(Role)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:30:07
 */
@ApiModel(value = "角色管理数据表单", description = "角色管理表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class RoleUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 应用编号
     */
    @ApiModelProperty(value = "应用编号", allowableValues = "", required = true, position = 1)
    @Size(max = 64, message = "应用编号{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "应用编号{required}")
    private String appId;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称", allowableValues = "", required = true, position = 2)
    @Size(max = 128, message = "角色名称{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "角色名称{required}")
    private String name;

    /**
     * 角色标识
     */
    @ApiModelProperty(value = "角色标识", allowableValues = "", position = 3)
    @Size(max = 128, message = "角色标识{size}")
    private String intro;


    @ApiModelProperty(value = "角色状态status", allowableValues = "", required = true, position = 4)
    @NotBlank(message = "角色状态status{required}")
    private String status;


    @ApiModelProperty(value = "菜单id", allowableValues = "", position = 5)
    private List<String> menus;

}