package com.markyang.framework.pojo.dto.system;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息Dto
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/4/13 10:47 上午 星期一
 */
@Data
public class UserWorkerInfoDto implements Serializable {

    private static final long serialVersionUID = 6724271069249034607L;
    /**
     * 用户ID
     */
    private String id;

    /**
     * 职员ID
     */
    private String workerId;

    /**
     * 机构ID
     */
    private String orgId;

    /**
     * 职员姓名
     */
    private String name;

    /**
     * 职员手机号码
     */
    private String mobilePhone;

    /**
     * 图片
     */
    private String pic;

    /**
     * 企业微信用户ID
     */
    private String wxCpUserId;
    /**
     * 机构用户ID
     */
    private String orgUserId;
    /**
     * 机构用户密码
     */
    private String orgUserPwd;
}
