package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import com.markyang.framework.pojo.entity.support.DictField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 机构管理(Org)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:26:45
 */
@ApiModel(value = "机构管理实体类", description = "机构管理实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_org`")
public class Org extends AbstractBaseEntity {

    private static final long serialVersionUID = 319815122317871456L;

    /**
     * 父编号
     */
    @ApiModelProperty(value = "父编号,bigint(20)", required = true, position = 1)
    private String parentId;

    @TableField(exist = false)
    private String parentName;
    /**
     * 类别
     */
    @ApiModelProperty(value = "类别,varchar(16)", required = true, position = 2)
    @DictField
    private String type;

    /**
     * 类别名称
     */
    @ApiModelProperty(value = "类别名称,varchar", position = 3)
    @TableField(exist = false)
    private String typeName;

    /**
     * 代码
     */
    @ApiModelProperty(value = "代码,varchar(128)", required = true, position = 4)
    private String code;
    /**
     * 机构密钥
     */
    @ApiModelProperty(value = "机构密钥,varchar(128)", position = 5)
    private String secret;
    /**
     * 机构等级
     */
    @ApiModelProperty(value = "机构等级,char(2)", position = 6)
    private String orgLevel;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称,varchar(128)", required = true, position = 7)
    private String name;
    /**
     * 简称
     */
    @ApiModelProperty(value = "简称,varchar(128)", position = 7)
    private String abbrName;
    /**
     * 拼音
     */
    @ApiModelProperty(value = "拼音,varchar(64)", position = 8)
    private String pinyin;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址,varchar(256)", position = 9)
    private String address;
    /**
     * 邮编
     */
    @ApiModelProperty(value = "邮编,varchar(16)", position = 10)
    private String postCode;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话,varchar(128)", position = 11)
    private String phone;
    /**
     * 传真
     */
    @ApiModelProperty(value = "传真,varchar(128)", position = 12)
    private String fax;
    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮件,varchar(128)", position = 13)
    private String email;
    /**
     * logo
     */
    @ApiModelProperty(value = "logo,varchar(128)", position = 14)
    private String logo;
    /**
     * 企业微信部门编号
     */
    @ApiModelProperty(value = "企业微信部门编号,int(11)", position = 15)
    private Integer wxCpDeptId;
    /**
     * 企业微信同步状态
     */
    @ApiModelProperty(value = "企业微信同步状态,char(1)", position = 16)
    private String wxCpSyncStatus;
    /**
     * 企业微信同步消息
     */
    @ApiModelProperty(value = "企业微信同步消息,varchar(512)", position = 17)
    private String wxCpSyncMsg;
    /**
     * 数据交换接口地址
     */
    @ApiModelProperty(value = "数据交换接口地址,varchar(256)", position = 18)
    private String exchangeUrl;
    /**
     * 数据交换接口密钥
     */
    @ApiModelProperty(value = "数据交换接口密钥,varchar(128)", position = 19)
    private String exchangeSecret;
    /**
     * 和his交互时使用的hospitalId
     */
    @ApiModelProperty(value = "和his交互时使用的hospitalId,varchar(128)", position = 20)
    private String hisId;
    /**
     * 不同机构开单使用的部门、账号和密码，中间用~~ 隔开
     */
    @ApiModelProperty(value = "不同机构开单使用的部门、账号和密码，中间用~~ 隔开,varchar(256)", position = 21)
    private String hisWorker;

    /**
     * 网上接诊
     */
    @ApiModelProperty(value = "网上接诊,char(1)", required = true, position = 22)
    @DictField
    private String online;

    /**
     * 网上接诊名称
     */
    @ApiModelProperty(value = "网上接诊名称,varchar", position = 23)
    @TableField(exist = false)
    private String onlineName;

    /**
     * 机构配置
     */
    @ApiModelProperty(value = "机构配置,varchar(64)", position = 24)
    private String config;
    /**
     * 是否支持合单支付
     */
    @ApiModelProperty(value = "是否支持合单支付,char(1)", position = 25)
    private String combineOrder;

    /**
     * 是否支持合单支付名称
     */
    @ApiModelProperty(value = "是否支持合单支付,varchar", position = 26)
    @TableField(exist = false)
    private String combineOrderName;

}