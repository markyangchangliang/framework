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


/**
 * 区域信息(Area)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:30:06
 */
@ApiModel(value = "区域信息数据表单", description = "区域信息表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class AreaUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号", allowableValues = "", required = true, position = 1)
    @Size(max = 32, message = "编号{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "编号{required}")
    private String code;

    /**
     * 父编号
     */
    @ApiModelProperty(value = "父编号", allowableValues = "", required = true, position = 2)
    @Size(max = 32, message = "父编号{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "父编号{required}")
    private String parentCode;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", allowableValues = "", required = true, position = 3)
    @Size(max = 256, message = "名称{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "名称{required}")
    private String name;

    /**
     * 拼音
     */
    @ApiModelProperty(value = "拼音", allowableValues = "", position = 4)
    @Size(max = 256, message = "拼音{size}")
    private String pinyin;

    /**
     * 级别
     */
    @ApiModelProperty(value = "级别", allowableValues = "", required = true, position = 5)
    @Size(max = 8, message = "级别{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "级别{required}")
    private String level;

    /**
     * 全名
     */
    @ApiModelProperty(value = "全名", allowableValues = "", required = true, position = 6)
    @Size(max = 512, message = "全名{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "全名{required}")
    private String fullName;

}