package com.markyang.framework.pojo.dto.system;


import com.markyang.framework.pojo.entity.support.DtoDictField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部门用户DTO
 *
 * @author yangchangliang
 * @version 1.0.0
 * @date 2020/3/28 14:37 星期六
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeptUserDto {

    private String userId;

    private String deptName;

    private String workerName;

    private String username;

    private String mobilePhone;

    private String wxCpUserId;

    @DtoDictField(tableName = "sys_user")
    private String status;

    private String statusName;
}
