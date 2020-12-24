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
 * 部门管理(Dept)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:26:45
 */
@ApiModel(value = "部门管理实体类", description = "部门管理实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_dept`")
public class Dept extends AbstractBaseEntity {

    private static final long serialVersionUID = -47038665177329138L;

    /**
     * 父编号
     */
    @ApiModelProperty(value = "父编号,bigint(20)", required = true, position = 1)
    private String parentId;
    /**
     * 机构编号
     */
    @ApiModelProperty(value = "机构编号,bigint(20)", required = true, position = 2)
    private String orgId;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称,varchar(256)", required = true, position = 3)
    private String name;
    /**
     * 拼音
     */
    @ApiModelProperty(value = "拼音,varchar(128)", position = 4)
    private String pinyin;
    /**
     * 科室类型
     */
    @ApiModelProperty(value = "科室类型,char(1)", position = 5)
    @DictField
    private String type;

    /**
     * 科室类型名称
     */
    @ApiModelProperty(value = "科室类型名称,varchar", position = 6)
    @TableField(exist = false)
    private String typeName;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型,varchar(32)", position = 6)
    @DictField
    private String deptType;

    /**
     * 科类型名称
     */
    @ApiModelProperty(value = "类型,varchar", position = 7)
    @TableField(exist = false)
    private String deptTypeName;


    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人,varchar(128)", position = 8)
    private String linkman;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话,varchar(128)", position = 9)
    private String phone;
    /**
     * 传真
     */
    @ApiModelProperty(value = "传真,varchar(128)", position = 10)
    private String fax;
    /**
     * 联系地址
     */
    @ApiModelProperty(value = "联系地址,varchar(256)", position = 11)
    private String address;
    /**
     * 邮编
     */
    @ApiModelProperty(value = "邮编,varchar(16)", position = 12)
    private String postCode;
    /**
     * 电子邮箱
     */
    @ApiModelProperty(value = "电子邮箱,varchar(128)", position = 13)
    private String email;
    /**
     * 机构内部编号
     */
    @ApiModelProperty(value = "机构内部编号,varchar(32)", position = 14)
    private String orgDeptId;
    /**
     * 机构内部父编号
     */
    @ApiModelProperty(value = "机构内部父编号,varchar(32)", position = 15)
    private String orgParentDeptId;
    /**
     * 企业微信部门编号
     */
    @ApiModelProperty(value = "企业微信部门编号,int(11)", position = 16)
    private Integer qywxDeptId;
    /**
     * 企业微信部门父编号
     */
    @ApiModelProperty(value = "企业微信部门父编号,int(11)", position = 17)
    private Integer qywxParentDeptId;
    /**
     * 企业微信部门序号
     */
    @ApiModelProperty(value = "企业微信部门序号,varchar(32)", position = 18)
    private String qywxOrder;
    /**
     * 企业微信同步状态
     */
    @ApiModelProperty(value = "企业微信同步状态,char(1)", position = 19)
    private String qywxTransStatus;
    /**
     * 企业微信同步消息
     */
    @ApiModelProperty(value = "企业微信同步消息,varchar(512)", position = 20)
    private String qywxTransMsg;
    /**
     * his内部编号
     */
    @ApiModelProperty(value = "his内部编号,varchar(32)", position = 21)
    private String hisDeptId;
    /**
     * 部门属性
     */
    @ApiModelProperty(value = "部门属性,json", position = 22)
    private String attribute;

}