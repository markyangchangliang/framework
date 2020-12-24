package com.markyang.framework.pojo.dto.system;

import lombok.Data;

import java.io.Serializable;

/**
 * 部门信息Dto
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/4/13 10:42 上午 星期一
 */
@Data
public class DeptInfoDto implements Serializable {

    private static final long serialVersionUID = -6963150370300684410L;
    /**
     * 部门ID
     */
    private String id;

    /**
     * 部门名称
     */
    private String name;
}
