package com.markyang.framework.pojo.dto.system;

import lombok.Data;

/**
 * 机构用户Dto
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/4/10 2:30 下午 星期五
 */
@Data
public class OrgUserDto {

    /**
     * 用户编号
     */
    private String id;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 部门名称
     */
    private String deptName;
}
