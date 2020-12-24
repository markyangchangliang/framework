package com.markyang.framework.pojo.entity.payment;

import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import com.markyang.framework.pojo.enumeration.payment.ClientTypeEnum;
import com.markyang.framework.pojo.enumeration.payment.PaymentStatusEnum;
import com.markyang.framework.pojo.enumeration.payment.RefundStatusEnum;
import com.markyang.framework.pojo.enumeration.payment.ServiceProviderEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 统一支付记录表(PaymentRecord)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-17 16:57:13
 */
@ApiModel(value = "统一支付记录表实体类", description = "统一支付记录表实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`pay_payment_record`")
public class PaymentRecord extends AbstractBaseEntity {

    private static final long serialVersionUID = -2708903731435528670L;

    /**
     * 支付ID
     */
    @ApiModelProperty(value = "支付ID,varchar(128)", allowableValues = "", required = true, position = 1)
    private String paymentId;
    /**
     * 服务提供商：a-微信，b-支付宝
     */
    @ApiModelProperty(value = "服务提供商：a-微信，b-支付宝,char(1)", allowableValues = "", required = true, position = 2)
    private ServiceProviderEnum serviceProvider;
    /**
     * 服务提供商ID
     */
    @ApiModelProperty(value = "服务提供商ID,varchar(128)", allowableValues = "", position = 3)
    private String spPaymentId;
    /**
     * 机构ID
     */
    @ApiModelProperty(value = "机构ID,bigint", allowableValues = "", required = true, position = 4)
    private String orgId;
    /**
     * 业务主键，根据具体情况而定
     */
    @ApiModelProperty(value = "业务标识符，根据具体情况而定,varchar(128)", allowableValues = "", position = 6)
    private String businessKey;
    /**
     * 业务主键，根据具体情况而定
     */
    @ApiModelProperty(value = "业务主键，根据具体情况而定,varchar(128)", allowableValues = "", position = 6)
    private String businessId;
    /**
     * 支付人ID
     */
    @ApiModelProperty(value = "支付人ID,varchar(128)", allowableValues = "", position = 7)
    private String payerId;
    /**
     * 支付标题
     */
    @ApiModelProperty(value = "支付标题,varchar(64)", allowableValues = "", required = true, position = 8)
    private String title;
    /**
     * 支付费用
     */
    @ApiModelProperty(value = "支付费用,decimal(8,2)", allowableValues = "", position = 9)
    private BigDecimal totalFee;
    /**
     * 客户端类型：a-APP，b-小程序，c-WEB
     */
    @ApiModelProperty(value = "客户端类型：a-小程序，b-APP，c-WEB,char(1)", allowableValues = "", required = true, position = 10)
    private ClientTypeEnum clientType;
    /**
     * 客户端IP
     */
    @ApiModelProperty(value = "客户端IP,varchar(32)", allowableValues = "", required = true, position = 11)
    private String clientIp;
    /**
     * 支付创建时间
     */
    @ApiModelProperty(value = "支付创建时间,datetime", allowableValues = "", required = true, position = 12)
    private LocalDateTime paymentCreatedTime;
    /**
     * 支付结束时间
     */
    @ApiModelProperty(value = "支付结束时间,datetime", allowableValues = "", position = 13)
    private LocalDateTime paymentEndedTime;
    /**
     * 支付状态：a-待支付
     */
    @ApiModelProperty(value = "支付状态：a-待支付,char(1)", allowableValues = "", position = 14)
    private PaymentStatusEnum paymentStatus;
    /**
     * 是否退款(bool)：0-否，1-是
     */
    @ApiModelProperty(value = "是否退款(bool类型)：0-否，1-是,tinyint", allowableValues = "", position = 15)
    private Boolean refunded;
    /**
     * 退款操作人ID
     */
    @ApiModelProperty(value = "退款操作人ID,varchar(128)", allowableValues = "", required = true, position = 1)
    private String refundedBy;
    /**
     * 退款ID
     */
    @ApiModelProperty(value = "退款ID,varchar(128)", allowableValues = "", required = true, position = 1)
    private String refundId;
    /**
     * 商户退款ID
     */
    @ApiModelProperty(value = "商户退款ID,varchar(128)", allowableValues = "", required = true, position = 1)
    private String spRefundId;
    /**
     * 退款原因
     */
    @ApiModelProperty(value = "退款原因,varchar(128)", allowableValues = "", position = 16)
    private String refundReason;
    /**
     * 退款状态：a-退款中
     */
    @ApiModelProperty(value = "退款状态：a-退款中,char(1)", allowableValues = "", position = 14)
    private RefundStatusEnum refundStatus;
    /**
     * 退款创建时间
     */
    @ApiModelProperty(value = "退款创建时间,datetime", allowableValues = "", required = true, position = 12)
    private LocalDateTime refundCreatedTime;
    /**
     * 退款结束时间
     */
    @ApiModelProperty(value = "退款结束时间,datetime", allowableValues = "", position = 13)
    private LocalDateTime refundEndedTime;

}