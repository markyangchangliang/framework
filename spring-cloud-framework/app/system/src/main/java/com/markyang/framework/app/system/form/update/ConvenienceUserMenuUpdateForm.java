package com.markyang.framework.app.system.form.update;

import com.markyang.framework.service.core.form.AbstractUpdateBaseForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;


/**
 * 用户便捷菜单(ConvenienceUserMenu)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-05-08 17:53:05
 */
@ApiModel(value = "用户便捷菜单数据表单", description = "用户便捷菜单表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class ConvenienceUserMenuUpdateForm extends AbstractUpdateBaseForm {

    /**
    * 用户菜单
    */    
    @ApiModelProperty(value = "用户菜单", allowableValues = "", required = true, position = 1, notes = "")
    @NotNull(message = "用户菜单{required}")
    private String userId;
    
    /**
    * 菜单ID
    */    
    @ApiModelProperty(value = "菜单ID", allowableValues = "", required = true, position = 2, notes = "")
    @NotNull(message = "菜单ID{required}")
    private String menuId;
    
    /**
    * 排序
    */    
    @ApiModelProperty(value = "排序", allowableValues = "", position = 3, notes = "")
	private Integer seq;
    
}