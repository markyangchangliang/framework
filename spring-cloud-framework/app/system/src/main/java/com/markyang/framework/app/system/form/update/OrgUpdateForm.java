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
import java.math.BigDecimal;


/**
 * 机构管理(Org)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:30:07
 */
@ApiModel(value = "机构管理数据表单", description = "机构管理表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class OrgUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 父编号
     */
    @ApiModelProperty(value = "父编号", allowableValues = "", required = true, position = 1)
    @NotNull(message = "父编号{required}")
    private String parentId;

    /**
     * 类别
     */
    @ApiModelProperty(value = "类别", allowableValues = "", required = true, position = 2)
    @Size(max = 16, message = "类别{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "类别{required}")
    private String type;

    /**
     * 代码
     */
    @ApiModelProperty(value = "代码", allowableValues = "", required = true, position = 3)
    @Size(max = 128, message = "代码{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "代码{required}")
    private String code;

    /**
     * 机构密钥
     */
    @ApiModelProperty(value = "机构密钥", allowableValues = "", position = 4)
    @Size(max = 128, message = "机构密钥{size}")
    private String secret;

    /**
     * 机构等级
     */
    @ApiModelProperty(value = "机构等级", allowableValues = "", position = 5)
    private String orgLevel;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", allowableValues = "", required = true, position = 6)
    @Size(max = 128, message = "名称{size}")
    @NotBlank(groups = FrameworkGroups.Add.class, message = "名称{required}")
    private String name;

    /**
     * 简称
     */
    @ApiModelProperty(value = "简称", allowableValues = "", position = 7)
    @Size(max = 128, message = "简称{size}")
    private String abbrName;

    /**
     * 拼音
     */
    @ApiModelProperty(value = "拼音", allowableValues = "", position = 8)
    @Size(max = 64, message = "拼音{size}")
    private String pinyin;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址", allowableValues = "", position = 9)
    @Size(max = 256, message = "地址{size}")
    private String address;

    /**
     * 邮编
     */
    @ApiModelProperty(value = "邮编", allowableValues = "", position = 10)
    @Size(max = 16, message = "邮编{size}")
    private String postCode;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话", allowableValues = "", position = 11)
    @Size(max = 128, message = "联系电话{size}")
    private String phone;

    /**
     * 传真
     */
    @ApiModelProperty(value = "传真", allowableValues = "", position = 12)
    @Size(max = 128, message = "传真{size}")
    private String fax;

    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮件", allowableValues = "", position = 13)
    @Size(max = 128, message = "电子邮件{size}")
    private String email;

    /**
     * logo
     */
    @ApiModelProperty(value = "logo", allowableValues = "", position = 14)
    @Size(max = 128, message = "logo{size}")
    private String logo;

    /**
     * 企业微信部门编号
     */
    @ApiModelProperty(value = "企业微信部门编号", allowableValues = "", position = 15)
    private Integer qywxDeptId;

    /**
     * 企业微信同步状态
     */
    @ApiModelProperty(value = "企业微信同步状态", allowableValues = "", position = 16)
    private String qywxTransStatus;

    /**
     * 企业微信同步消息
     */
    @ApiModelProperty(value = "企业微信同步消息", allowableValues = "", position = 17)
    @Size(max = 512, message = "企业微信同步消息{size}")
    private String qywxTransMsg;

    /**
     * 数据交换接口地址
     */
    @ApiModelProperty(value = "数据交换接口地址", allowableValues = "", position = 18)
    @Size(max = 256, message = "数据交换接口地址{size}")
    private String exchangerUrl;

    /**
     * 数据交换接口密钥
     */
    @ApiModelProperty(value = "数据交换接口密钥", allowableValues = "", position = 19)
    @Size(max = 128, message = "数据交换接口密钥{size}")
    private String exchangerSecret;

    /**
     * 和his交互时使用的hospitalId
     */
    @ApiModelProperty(value = "和his交互时使用的hospitalId", allowableValues = "", position = 20)
    @Size(max = 128, message = "和his交互时使用的hospitalId{size}")
    private String hisId;

    /**
     * 不同机构开单使用的部门、账号和密码，中间用~~ 隔开
     */
    @ApiModelProperty(value = "不同机构开单使用的部门、账号和密码，中间用~~ 隔开", allowableValues = "", position = 21)
    @Size(max = 256, message = "不同机构开单使用的部门、账号和密码，中间用~~ 隔开{size}")
    private String hisWorker;

    /**
     * 网上接诊
     */
    @ApiModelProperty(value = "网上接诊", allowableValues = "", required = true, position = 22)
    @NotNull(message = "网上接诊{required}")
    private String online;

    /**
     * 商户编号
     */
    @ApiModelProperty(value = "商户编号", allowableValues = "", position = 23)
    @Size(max = 64, message = "商户编号{size}")
    private String submchId;

    /**
     * 是否支持合单支付
     */
    @ApiModelProperty(value = "是否支持合单支付", allowableValues = "", required = true, position = 24)
    private String combineOrder;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度", position = 27)
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度", position = 28)
    private BigDecimal latitude;

    /**
     * 在线问诊提示
     */
    @ApiModelProperty(value = "在线问诊提示", position = 29)
    @Size(max = 1024, message = "在线问诊提示{1024}")
    private String onlineDiagnoseTip;
}