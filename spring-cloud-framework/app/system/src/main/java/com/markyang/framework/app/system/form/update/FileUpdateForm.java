package com.markyang.framework.app.system.form.update;

import com.markyang.framework.service.core.form.AbstractUpdateBaseForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;


/**
 * 文件上传(File)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:30:07
 */
@ApiModel(value = "文件上传数据表单", description = "文件上传表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class FileUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型", allowableValues = "", position = 1)
    private Integer type;

    /**
     * URL地址
     */
    @ApiModelProperty(value = "URL地址", allowableValues = "", position = 2)
    @Size(max = 256, message = "URL地址{size}")
    private String url;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", allowableValues = "", position = 3)
    private LocalDateTime createDate;

}