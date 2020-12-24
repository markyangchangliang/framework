package com.markyang.framework.app.system.form.update;

import com.markyang.framework.service.core.form.AbstractUpdateBaseForm;
import com.markyang.framework.service.core.validator.group.FrameworkGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * 职员管理(Worker)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:30:07
 */
@ApiModel(value = "职员管理数据表单", description = "职员管理表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class WorkerUpdateForm extends AbstractUpdateBaseForm {
    /**
     * 类别
     */
    @ApiModelProperty(value = "机构编号", allowableValues = "", required = true, position = 1)
    @NotNull(groups = FrameworkGroups.Add.class, message = "机构编号{required}")
    private String orgId;
    /**
     * 类别
     */
    @ApiModelProperty(value = "部门编号", allowableValues = "", required = true, position = 1)
    @NotNull(groups = FrameworkGroups.Add.class, message = "部门编号{required}")
    private String deptId;
    /**
     * 类别
     */
    @ApiModelProperty(value = "类别", allowableValues = "", required = true, position = 1)
    @NotNull(message = "类别{required}")
    private String type;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", allowableValues = "", required = true, position = 2)
    @Size(max = 128, message = "姓名{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "姓名{required}")
    private String name;

    /**
     * 拼音
     */
    @ApiModelProperty(value = "拼音", allowableValues = "", position = 3)
    @Size(max = 64, message = "拼音{size}")
    private String pinyin;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别", allowableValues = "", required = true, position = 4)
    @NotNull(message = "性别{required}")
    private String gender;

    /**
     * 民族
     */
    @ApiModelProperty(value = "民族", allowableValues = "", position = 5)
    private String nation;

    /**
     * 政治面貌
     */
    @ApiModelProperty(value = "政治面貌", allowableValues = "", position = 6)
    private String politics;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号", allowableValues = "", position = 7)
    @Size(max = 32, message = "身份证号{size}")
    private String certificateNo;

    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期", allowableValues = "", position = 8)
    private LocalDate birthday;

    /**
     * 身份证住址
     */
    @ApiModelProperty(value = "身份证住址", allowableValues = "", position = 9)
    @Size(max = 256, message = "身份证住址{size}")
    private String certificateAddress;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话", allowableValues = "", position = 10)
    @Size(max = 32, message = "联系电话{size}")
    private String phone;

    /**
     * 移动电话
     */
    @ApiModelProperty(value = "移动电话", allowableValues = "", position = 11)
    @Size(max = 32, message = "移动电话{size}")
    private String mphone;

    /**
     * 通讯地址
     */
    @ApiModelProperty(value = "通讯地址", allowableValues = "", position = 12)
    @Size(max = 256, message = "通讯地址{size}")
    private String address;

    /**
     * 邮编
     */
    @ApiModelProperty(value = "邮编", allowableValues = "", position = 13)
    @Size(max = 16, message = "邮编{size}")
    private String postCode;

    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮件", allowableValues = "", position = 14)
    @Size(max = 128, message = "电子邮件{size}")
    private String email;

    /**
     * 职称
     */
    @ApiModelProperty(value = "职称", allowableValues = "", position = 15)
    private String positional;

    /**
     * 职务
     */
    @ApiModelProperty(value = "职务", allowableValues = "", position = 16)
    private String post;

    /**
     * 医生简介
     */
    @ApiModelProperty(value = "医生简介", allowableValues = "", position = 17)
    private String intro;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", allowableValues = "", position = 18)
    @Size(max = 256, message = "头像{size}")
    private String pic;

    /**
     * 微信
     */
    @ApiModelProperty(value = "微信", allowableValues = "", position = 19)
    @Size(max = 128, message = "微信{size}")
    private String weixin;

    /**
     * qq
     */
    @ApiModelProperty(value = " qq ", allowableValues = "", position = 20)
    @Size(max = 64, message = " qq {size}")
    private String qq;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", allowableValues = "", position = 21)
    private String status;

    /**
     * 认证状态
     */
    @ApiModelProperty(value = "认证状态", allowableValues = "", position = 22)
    private String authStatus;

    /**
     * 认证日期
     */
    @ApiModelProperty(value = "认证日期", allowableValues = "", position = 23)
    private LocalDateTime authDate;

    /**
     * 认证状态
     */
    @ApiModelProperty(value = "认证状态", allowableValues = "", position = 24)
    private String qualStatus;

    /**
     * 认证日期
     */
    @ApiModelProperty(value = "认证日期", allowableValues = "", position = 25)
    private LocalDateTime qualDate;

    /**
     * 接诊次数
     */
    @ApiModelProperty(value = "接诊次数", allowableValues = "", position = 26)
    private Integer diagnoseNum;

    /**
     * 接诊评分
     */
    @ApiModelProperty(value = "接诊评分", allowableValues = "", required = true, position = 27)
    private Double diagnoseScore;

    /**
     * 接诊速度
     */
    @ApiModelProperty(value = "接诊速度", allowableValues = "", required = true, position = 28)
    private Double diagnoseSpeed;

    /**
     * 企业微信成员userid
     */
    @ApiModelProperty(value = "企业微信成员userid ", allowableValues = "", position = 29)
    @Size(max = 64, message = "企业微信成员userid {size}")
    private String qywxUserId;

    /**
     * 机构成员编号
     */
    @ApiModelProperty(value = "机构成员编号", allowableValues = "", position = 30)
    @Size(max = 128, message = "机构成员编号{size}")
    private String orgUserId;

    /**
     * 机构成员密码
     */
    @ApiModelProperty(value = "机构成员密码", allowableValues = "", position = 31)
    @Size(max = 128, message = "机构成员密码{size}")
    private String orgUserPwd;

    /**
     * 参加工作时间
     */
    @ApiModelProperty(value = "参加工作时间", allowableValues = "", position = 32)
    private LocalDate workDate;

    /**
     * 入职时间
     */
    @ApiModelProperty(value = "入职时间", allowableValues = "", position = 33)
    private LocalDate entryDate;

    /**
     * 聘任时间
     */
    @ApiModelProperty(value = "聘任时间", allowableValues = "", position = 34)
    @Size(max = 32, message = "聘任时间{size}")
    private String engageDate;

    /**
     * 第一学历
     */
    @ApiModelProperty(value = "第一学历", allowableValues = "", position = 35)
    @Size(max = 64, message = "第一学历{size}")
    private String firstDegree;

    /**
     * 毕业学校
     */
    @ApiModelProperty(value = "毕业学校", allowableValues = "", position = 36)
    @Size(max = 256, message = "毕业学校{size}")
    private String firstGraduateSchool;

    /**
     * 毕业时间
     */
    @ApiModelProperty(value = "毕业时间", allowableValues = "", position = 37)
    @Size(max = 32, message = "毕业时间{size}")
    private String firstGraduateDate;

    /**
     * 最高学历
     */
    @ApiModelProperty(value = "最高学历", allowableValues = "", position = 38)
    @Size(max = 64, message = "最高学历{size}")
    private String highestDegree;

    /**
     * 毕业学校
     */
    @ApiModelProperty(value = "毕业学校", allowableValues = "", position = 39)
    @Size(max = 256, message = "毕业学校{size}")
    private String highestGraduateSchool;

    /**
     * 毕业时间
     */
    @ApiModelProperty(value = "毕业时间", allowableValues = "", position = 40)
    @Size(max = 256, message = "毕业时间{size}")
    private String highestGraduateDate;

    /**
     * 人员类别
     */
    @ApiModelProperty(value = "人员类别", allowableValues = "", position = 41)
    @Size(max = 32, message = "人员类别{size}")
    private String workerCategory;

    /**
     * 岗位类别
     */
    @ApiModelProperty(value = "岗位类别", allowableValues = "", position = 42)
    @Size(max = 32, message = "岗位类别{size}")
    private String postCategory;

    /**
     * 岗位等级
     */
    @ApiModelProperty(value = "岗位等级", allowableValues = "", position = 43)
    private String positionGrade;

    /**
     * 籍贯
     */
    @ApiModelProperty(value = "籍贯", allowableValues = "", position = 44)
    @Size(max = 128, message = "籍贯{size}")
    private String nativePlace;

    /**
     * 网上接诊
     */
    @ApiModelProperty(value = "网上接诊", allowableValues = "", required = true, position = 45)
    @NotNull(message = "网上接诊{required}")
    private String online;

    /**
     * 是否推荐
     */
    @ApiModelProperty(value = "是否推荐", allowableValues = "", required = true, position = 46)
    @NotNull(message = "是否推荐{required}")
    private String recommend;

}