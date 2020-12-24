package com.markyang.framework.service.core.form;

import com.baomidou.mybatisplus.annotation.TableId;
import com.markyang.framework.service.core.validator.group.FrameworkGroups;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * 抽象更新表单基类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/24 5:29 下午 星期二
 */
@NoArgsConstructor
@SuperBuilder
@Data
public class AbstractUpdateBaseForm implements UpdateBaseForm {

    /**
     * 实体主键
     */
    @ApiModelProperty(name = "id", value = "ID主键", position = 0, required = true)
    @TableId
    @NotBlank(groups = FrameworkGroups.Update.class, message = "ID{required}")
    private String id;
}
