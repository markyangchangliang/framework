package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 用户角色(UserRole)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-28 10:42:38
 */
@ApiModel(value = "用户角色实体类", description = "用户角色实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_user_role`")
public class UserRole extends AbstractBaseEntity {

    private static final long serialVersionUID = 720136424776897529L;

    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号,bigint", allowableValues = "", required = true, position = 1)
    private String userId;
    /**
     * 角色编号
     */
    @ApiModelProperty(value = "角色编号,bigint", allowableValues = "", required = true, position = 2)
    private String roleId;

}