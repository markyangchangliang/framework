package com.markyang.framework.pojo.dto.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 表列表传输对象
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class DictDto implements Serializable {

    private static final long serialVersionUID = 4536762286755410817L;

    @ApiModelProperty(value = "字段名称", position = 1)
    private String field;
    @ApiModelProperty(value = "字典含义", position = 2)
    private String name;
    @ApiModelProperty(value = "字典值", position = 3)
    private String value;
    @ApiModelProperty(value = "序号", position = 4)
    private Integer seq;
}
