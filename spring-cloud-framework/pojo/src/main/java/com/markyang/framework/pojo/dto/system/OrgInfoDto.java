package com.markyang.framework.pojo.dto.system;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 机构信息Dto
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/4/13 9:10 上午 星期一
 */
@Data
public class OrgInfoDto implements Serializable {

    private static final long serialVersionUID = 8098719426855340637L;
    /**
     * 机构编号
     */
    private String id;
    /**
     * 机构名称
     */
    private String name;

    /**
     * 机构类型
     */
    private String type;

    /**
     * 机构代码
     */
    private String code;

    /**
     * his机构编号
     */
    private String hisId;

    /**
     * his科室编号
     */
    private String hisDeptId;

    /**
     * 机构密钥
     */
    private String secret;

    /**
     * His 账号信息
     */
    private String hisWorker;
    /**
     * His 账号密码
     */
    private String hisWorkerPwd;

    /**
     * 交换程序地址
     */
    private String exchangeUrl;

    /**
     * 交换机密匙
     */
    private String exchangeSecret;

    /**
     * 机构配置
     */
    private String config;

    /**
     * 配置对象
     */
    private OrgConfigDto configDto;

    /**
     * LOGO
     */
    private String logo;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;
}
