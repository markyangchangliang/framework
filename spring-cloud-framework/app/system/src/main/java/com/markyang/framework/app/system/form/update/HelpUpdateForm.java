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
import javax.validation.constraints.Size;


/**
 * 帮助文件(Help)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:30:06
 */
@ApiModel(value = "帮助文件数据表单", description = "帮助文件表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class HelpUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 菜单编号
     */
    @ApiModelProperty(value = "菜单编号", allowableValues = "", required = true, position = 1)
    @NotNull(message = "菜单编号{required}")
    private String menuId;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容", allowableValues = "", position = 2)
    private String content;

    /**
     * 视频地址
     */
    @ApiModelProperty(value = "视频地址", allowableValues = "", position = 3)
    @Size(max = 512, message = "视频地址{size}")
    private String videoUrl;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", allowableValues = "", required = true, position = 4)
    @NotNull(message = "状态{required}")
    private String status;

}