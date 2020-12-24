package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import com.markyang.framework.pojo.entity.support.DictField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 职员管理(Worker)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:26:45
 */
@ApiModel(value = "职员管理实体类", description = "职员管理实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_worker`")
public class Worker extends AbstractBaseEntity {

    private static final long serialVersionUID = -25666042079428603L;

    /**
     * 类别
     */
    @ApiModelProperty(value = "类别,char(2)", required = true, position = 1)
    @DictField
    private String type;
    /**
     * 类别名称
     */
    @ApiModelProperty(value = "类别名称,varchar", position = 8)
    @TableField(exist = false)
    private String typeName;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名,varchar(128)", required = true, position = 2)
    private String name;
    /**
     * 拼音
     */
    @ApiModelProperty(value = "拼音,varchar(64)", position = 3)
    private String pinyin;
    /**
     * 性别
     */
    @ApiModelProperty(value = "性别,char(1)", required = true, position = 4)
    @DictField
    private String gender;
    /**
     * 性别名称
     */
    @ApiModelProperty(value = "性别名称,varchar", position = 5)
    @TableField(exist = false)
    private String genderName;
    /**
     * 民族
     */
    @ApiModelProperty(value = "民族,char(2)", position = 6)
    @DictField
    private String nation;

    /**
     * 民族名称
     */
    @ApiModelProperty(value = "民族名称,varchar", position = 7)
    @TableField(exist = false)
    private String nationName;
    /**
     * 政治面貌
     */
    @ApiModelProperty(value = "政治面貌,char(2)", position = 8)
    @DictField
    private String politics;
    /**
     * 政治面貌名称
     */
    @ApiModelProperty(value = "政治面貌名称,char(2)", position = 8)
    @TableField(exist = false)
    private String politicsName;
    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号,varchar(32)", position = 9)
    private String certificateNo;
    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期,date", position = 10)
    private LocalDate birthday;
    /**
     * 身份证住址
     */
    @ApiModelProperty(value = "身份证住址,varchar(256)", position = 11)
    private String certificateAddress;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话,varchar(32)", position = 12)
    private String telPhone;
    /**
     * 移动电话
     */
    @ApiModelProperty(value = "移动电话,varchar(32)", position = 13)
    private String mobilePhone;
    /**
     * 通讯地址
     */
    @ApiModelProperty(value = "通讯地址,varchar(256)", position = 14)
    private String address;
    /**
     * 邮编
     */
    @ApiModelProperty(value = "邮编,varchar(16)", position = 15)
    private String postCode;
    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮件,varchar(128)", position = 16)
    private String email;
    /**
     * 职称
     */
    @ApiModelProperty(value = "职称,char(10)", position = 17)
    @DictField
    private String positional;
    /**
     * 职称名称
     */
    @ApiModelProperty(value = "职称名称,varchar", position = 18)
    @TableField(exist = false)
    private String positionalName;
    /**
     * 职务
     */
    @ApiModelProperty(value = "职务,char(10)", position = 19)
    @DictField
    private String post;
    /**
     * 职务名称
     */
    @ApiModelProperty(value = "职务名称,varchar", position = 20)
    @TableField(exist = false)
    private String postName;
    /**
     * 医生简介
     */
    @ApiModelProperty(value = "医生简介,text", position = 21)
    private String intro;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像,varchar(256)", position = 22)
    private String pic;
    /**
     * 微信
     */
    @ApiModelProperty(value = "微信,varchar(128)", position = 23)
    private String wx;
    /**
     * qq
     */
    @ApiModelProperty(value = " qq ,varchar(64)", position = 24)
    private String qq;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态,char(100)", position = 25)
    @DictField
    private String status;
    /**
     * 状态名称
     */
    @ApiModelProperty(value = "状态名称,varchar", position = 26)
    @TableField(exist = false)
    private String statusName;
    /**
     * 认证状态
     */
    @ApiModelProperty(value = "认证状态,char(1)", position = 27)
    @DictField
    private String authStatus;
    /**
     * 认证状态名称
     */
    @ApiModelProperty(value = "认证状态名称,varchar", position = 28)
    @TableField(exist = false)
    private String authStatusName;
    /**
     * 认证日期
     */
    @ApiModelProperty(value = "认证日期,datetime", position = 29)
    private LocalDateTime authDate;
    /**
     * 认证状态
     */
    @ApiModelProperty(value = "认证状态,char(1)", position = 30)
    @DictField
    private String qualifiedStatus;
    /**
     * 认证状态名称
     */
    @ApiModelProperty(value = "认证状态名称,varchar", position = 28)
    @TableField(exist = false)
    private String qualifiedStatusName;
    /**
     * 认证日期
     */
    @ApiModelProperty(value = "认证日期,datetime", position = 29)
    private LocalDateTime qualifiedDate;
    /**
     * 接诊次数
     */
    @ApiModelProperty(value = "接诊次数,int(11)", position = 30)
    private Integer diagnoseNum;
    /**
     * 接诊评分
     */
    @ApiModelProperty(value = "接诊评分,float(2,1)", required = true, position = 31)
    private BigDecimal diagnoseScore;
    /**
     * 接诊速度
     */
    @ApiModelProperty(value = "接诊速度,float(11,2)", required = true, position = 32)
    private BigDecimal diagnoseSpeed;
    /**
     * 企业微信成员userid
     */
    @ApiModelProperty(value = "企业微信成员userid ,varchar(64)", position = 33)
    private String wxCpUserId;
    /**
     * 机构成员编号
     */
    @ApiModelProperty(value = "机构成员编号,varchar(128)", position = 34)
    private String orgUserId;
    /**
     * 机构成员密码
     */
    @ApiModelProperty(value = "机构成员密码,varchar(128)", position = 34)
    private String orgUserPwd;
    /**
     * 参加工作时间
     */
    @ApiModelProperty(value = "参加工作时间,date", position = 36)
    private LocalDate workDate;
    /**
     * 入职时间
     */
    @ApiModelProperty(value = "入职时间,date", position = 37)
    private LocalDate entryDate;
    /**
     * 聘任时间
     */
    @ApiModelProperty(value = "聘任时间,varchar(32)", position = 38)
    private String engageDate;
    /**
     * 第一学历
     */
    @ApiModelProperty(value = "第一学历,varchar(64)", position = 38)
    private String firstDegree;
    /**
     * 毕业学校
     */
    @ApiModelProperty(value = "毕业学校,varchar(256)", position = 40)
    private String firstGraduateSchool;
    /**
     * 毕业时间
     */
    @ApiModelProperty(value = "毕业时间,varchar(32)", position = 41)
    private String firstGraduateDate;
    /**
     * 最高学历
     */
    @ApiModelProperty(value = "最高学历,varchar(64)", position = 42)
    private String highestDegree;
    /**
     * 毕业学校
     */
    @ApiModelProperty(value = "毕业学校,varchar(256)", position = 43)
    private String highestGraduateSchool;
    /**
     * 毕业时间
     */
    @ApiModelProperty(value = "毕业时间,varchar(256)", position = 44)
    private String highestGraduateDate;
    /**
     * 人员类别
     */
    @ApiModelProperty(value = "人员类别,varchar(32)", position = 45)
    @DictField
    private String workerCategory;
    /**
     * 人员类别名称
     */
    @ApiModelProperty(value = "人员类别名称,varchar", position = 46)
    @TableField(exist = false)
    private String workerCategoryName;
    /**
     * 岗位类别
     */
    @ApiModelProperty(value = "岗位类别,varchar(32)", position = 47)
    @DictField
    private String postCategory;
    /**
     * 岗位类别名称
     */
    @ApiModelProperty(value = "岗位类别名称,varchar", position = 48)
    @TableField(exist = false)
    private String postCategoryName;
    /**
     * 岗位等级
     */
    @ApiModelProperty(value = "岗位等级,char(4)", position = 49)
    @DictField
    private String positionGrade;
    /**
     * 岗位等级名称
     */
    @ApiModelProperty(value = "岗位等级名称,varchar", position = 50)
    @TableField(exist = false)
    private String positionGradeName;
    /**
     * 籍贯
     */
    @ApiModelProperty(value = "籍贯,varchar(128)", position = 51)
    private String nativePlace;
    /**
     * 网上接诊
     */
    @ApiModelProperty(value = "网上接诊,char(1)", required = true, position = 52)
    @DictField
    private String online;
    /**
     * 网上接诊名称
     */
    @ApiModelProperty(value = "网上接诊名称,varchar", position = 53)
    @TableField(exist = false)
    private String onlineName;
    /**
     * 是否推荐
     */
    @ApiModelProperty(value = "是否推荐,char(1)", required = true, position = 54)
    @DictField
    private String recommend;
    /**
     * 是否推荐名称
     */
    @ApiModelProperty(value = "是否推荐名称,varchar", position = 55)
    @TableField(exist = false)
    private String recommendName;
    /**
     * 机构编号
     */
    @ApiModelProperty(value = "机构编号", position = 56)
    @TableField(exist = false)
    private String orgId;
    /**
     * 机构名称
     */
    @ApiModelProperty(value = "机构名称", position = 57)
    @TableField(exist = false)
    private String orgName;
    /**
     * 部门编号
     */
    @ApiModelProperty(value = "部门编号", position = 58)
    @TableField(exist = false)
    private String deptId;
    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称", position = 59)
    @TableField(exist = false)
    private String deptName;
    /**
     * 职员诊查费
     */
    @ApiModelProperty(value = "职员诊查费", position = 60)
    @TableField(exist = false)
    private String diagnoseFee;
    /**
     * 人员照片(数据库)
     */
    @TableField(exist = false)
    @JsonIgnore
    private byte[] image;
}