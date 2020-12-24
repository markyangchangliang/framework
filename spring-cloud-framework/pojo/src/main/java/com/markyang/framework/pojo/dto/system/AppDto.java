package com.markyang.framework.pojo.dto.system;

import com.markyang.framework.pojo.entity.system.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.util.List;

/**
 * 表列表传输对象
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppDto implements Serializable {

    private static final long serialVersionUID = 4536762286755410817L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", position = 2)
    private String name;

    private List<Role> roles;
}
