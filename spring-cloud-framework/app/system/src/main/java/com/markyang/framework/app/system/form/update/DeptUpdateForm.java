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

import javax.validation.constraints.*;


/**
 * 部门管理(Dept)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:30:07
 */
@ApiModel(value = "部门管理数据表单", description = "部门管理表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class DeptUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 父编号
     */
    @ApiModelProperty(value = "父编号", allowableValues = "", required = true, position = 1)
    @NotNull(message = "父编号{required}")
    private String parentId;

    /**
     * 机构编号
     */
    @ApiModelProperty(value = "机构编号", allowableValues = "", required = true, position = 2)
    @NotNull(message = "机构编号{required}")
    private String orgId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", allowableValues = "", required = true, position = 3)
    @Size(max = 256, message = "名称{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "名称{required}")
    private String name;

    /**
     * 拼音
     */
    @ApiModelProperty(value = "拼音", allowableValues = "", position = 4)
    @Size(max = 128, message = "拼音{size}")
    private String pinyin;

    /**
     * 科室类型
     */
    @ApiModelProperty(value = "科室类型", allowableValues = "", position = 5)
    private String type;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", allowableValues = "", position = 6)
    @Size(max = 32, message = "类型{size}")
    private String deptType;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人", allowableValues = "", position = 7)
    @Size(max = 128, message = "联系人{size}")
    private String linkman;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话", allowableValues = "", position = 8)
    @Size(max = 128, message = "联系电话{size}")
    private String phone;

    /**
     * 传真
     */
    @ApiModelProperty(value = "传真", allowableValues = "", position = 9)
    @Size(max = 128, message = "传真{size}")
    private String fax;

    /**
     * 联系地址
     */
    @ApiModelProperty(value = "联系地址", allowableValues = "", position = 10)
    @Size(max = 256, message = "联系地址{size}")
    private String address;

    /**
     * 邮编
     */
    @ApiModelProperty(value = "邮编", allowableValues = "", position = 11)
    @Size(max = 16, message = "邮编{size}")
    private String postCode;

    /**
     * 电子邮箱
     */
    @ApiModelProperty(value = "电子邮箱", allowableValues = "", position = 12)
    @Size(max = 128, message = "电子邮箱{size}")
    private String email;

    /**
     * 机构内部编号
     */
    @ApiModelProperty(value = "机构内部编号", allowableValues = "", position = 13)
    @Size(max = 32, message = "机构内部编号{size}")
    private String orgDeptId;

    /**
     * 机构内部父编号
     */
    @ApiModelProperty(value = "机构内部父编号", allowableValues = "", position = 14)
    @Size(max = 32, message = "机构内部父编号{size}")
    private String orgParentDeptId;

    /**
     * 企业微信部门编号
     */
    @ApiModelProperty(value = "企业微信部门编号", allowableValues = "", position = 15)
    private Integer qywxDeptId;

    /**
     * 企业微信部门父编号
     */
    @ApiModelProperty(value = "企业微信部门父编号", allowableValues = "", position = 16)
    private Integer qywxParentDeptId;

    /**
     * 企业微信部门序号
     */
    @ApiModelProperty(value = "企业微信部门序号", allowableValues = "", position = 17)
    @Size(max = 32, message = "企业微信部门序号{size}")
    private String qywxOrder;

    /**
     * 企业微信同步状态
     */
    @ApiModelProperty(value = "企业微信同步状态", allowableValues = "", position = 18)
    private String qywxTransStatus;

    /**
     * 企业微信同步消息
     */
    @ApiModelProperty(value = "企业微信同步消息", allowableValues = "", position = 19)
    @Size(max = 512, message = "企业微信同步消息{size}")
    private String qywxTransMsg;

    /**
     * his内部编号
     */
    @ApiModelProperty(value = "his内部编号", allowableValues = "", position = 20)
    @Size(max = 32, message = "his内部编号{size}")
    private String hisDeptId;

    /**
     * 部门属性
     */
    @ApiModelProperty(value = "部门属性", allowableValues = "", position = 21)
    private String attribute;

}