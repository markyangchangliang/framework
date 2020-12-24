package com.markyang.framework.pojo.entity.payment;

import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 微信账单(WxBill)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-05 11:32:40
 */
@ApiModel(value = "微信账单实体类", description = "微信账单实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`pay_wx_bill`")
public class WxBill extends AbstractBaseEntity {

    private static final long serialVersionUID = -97499700099878214L;

    /**
     * 交易时间
     */
    @ApiModelProperty(value = "交易时间,datetime", allowableValues = "", required = true, position = 1)
    private LocalDateTime tradeTime;
    /**
     * 公众账号id
     */
    @ApiModelProperty(value = "公众账号id ,varchar(32)", allowableValues = "", required = true, position = 2)
    private String appId;
    /**
     * 商户号
     */
    @ApiModelProperty(value = "商户号,varchar(32)", allowableValues = "", required = true, position = 3)
    private String mchId;
    /**
     * 特约商户号
     */
    @ApiModelProperty(value = "特约商户号,varchar(32)", allowableValues = "", required = true, position = 4)
    private String subMchId;
    /**
     * 设备号
     */
    @ApiModelProperty(value = "设备号,varchar(128)", allowableValues = "", position = 5)
    private String deviceInfo;
    /**
     * 微信订单号
     */
    @ApiModelProperty(value = "微信订单号,varchar(32)", allowableValues = "", required = true, position = 6)
    private String transactionId;
    /**
     * 商户订单号
     */
    @ApiModelProperty(value = "商户订单号,varchar(32)", allowableValues = "", required = true, position = 7)
    private String outTradeNo;
    /**
     * 用户标识
     */
    @ApiModelProperty(value = "用户标识,varchar(32)", allowableValues = "", required = true, position = 8)
    private String openId;
    /**
     * 交易类型
     */
    @ApiModelProperty(value = "交易类型,varchar(32)", allowableValues = "", required = true, position = 9)
    private String tradeType;
    /**
     * 交易状态
     */
    @ApiModelProperty(value = "交易状态,varchar(32)", allowableValues = "", required = true, position = 10)
    private String tradeStatus;
    /**
     * 付款银行
     */
    @ApiModelProperty(value = "付款银行,varchar(32)", allowableValues = "", required = true, position = 11)
    private String payBank;
    /**
     * 货币种类
     */
    @ApiModelProperty(value = "货币种类,varchar(32)", allowableValues = "", required = true, position = 12)
    private String moneyType;
    /**
     * 应结订单金额
     */
    @ApiModelProperty(value = "应结订单金额,float", allowableValues = "", required = true, position = 13)
    private Double orderPay;
    /**
     * 代金券金额
     */
    @ApiModelProperty(value = "代金券金额,float", allowableValues = "", position = 14)
    private Double voucherAmount;
    /**
     * 微信退款单号
     */
    @ApiModelProperty(value = "微信退款单号,varchar(128)", allowableValues = "", position = 15)
    private String refundNumber;
    /**
     * 商户退款单号
     */
    @ApiModelProperty(value = "商户退款单号,varchar(128)", allowableValues = "", position = 16)
    private String outRefundNo;
    /**
     * 退款金额
     */
    @ApiModelProperty(value = "退款金额,float", allowableValues = "", position = 17)
    private Double refundAmount;
    /**
     * 充值券退款金额
     */
    @ApiModelProperty(value = "充值券退款金额,float", allowableValues = "", position = 18)
    private Double refundAmountVoucher;
    /**
     * 退款类型
     */
    @ApiModelProperty(value = "退款类型,varchar(32)", allowableValues = "", position = 19)
    private String refundsType;
    /**
     * 退款状态
     */
    @ApiModelProperty(value = "退款状态,varchar(32)", allowableValues = "", position = 20)
    private String refundsStatus;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称,varchar(128)", allowableValues = "", position = 21)
    private String commodityName;
    /**
     * 商户数据包
     */
    @ApiModelProperty(value = "商户数据包,varchar(128)", allowableValues = "", position = 22)
    private String dataPacket;
    /**
     * 手续费
     */
    @ApiModelProperty(value = "手续费,float", allowableValues = "", position = 23)
    private Double serviceCharge;
    /**
     * 费率
     */
    @ApiModelProperty(value = "费率,varchar(32)", allowableValues = "", position = 24)
    private String rate;
    /**
     * 订单金额
     */
    @ApiModelProperty(value = "订单金额,float", allowableValues = "", position = 25)
    private Double orderFee;
    /**
     * 申请退款金额
     */
    @ApiModelProperty(value = "申请退款金额,float", allowableValues = "", position = 26)
    private Double applyBackFee;
    /**
     * 费率备注
     */
    @ApiModelProperty(value = "费率备注,varchar(32)", allowableValues = "", position = 27)
    private String rateRemark;

}