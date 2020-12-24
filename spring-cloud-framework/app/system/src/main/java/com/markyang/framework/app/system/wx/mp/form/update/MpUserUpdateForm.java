package com.markyang.framework.app.system.wx.mp.form.update;

import com.markyang.framework.service.core.form.AbstractUpdateBaseForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


 /**
 * 微信用户(MpUser)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:10:42
 */
@ApiModel(value = "微信用户数据表单", description = "微信用户表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MpUserUpdateForm extends AbstractUpdateBaseForm {

    /**
    * 用户备注
    */    
    @ApiModelProperty(value = "用户备注", allowableValues = "", position = 1, notes = "")
    @Size(max = 100, message = "用户备注{size}")
    private String remark;    
    
    /**
    * 应用类型
    */    
    @ApiModelProperty(value = "应用类型", allowableValues = "", position = 2, notes = "")
    private String appType;    
    
    /**
    * 是否订阅
    */    
    @ApiModelProperty(value = "是否订阅", allowableValues = "", position = 3, notes = "")
    private String subscribe;    
    
    /**
    * 用户关注渠道来源 
    */    
    @ApiModelProperty(value = "用户关注渠道来源 ", allowableValues = "", position = 4, notes = "")
    @Size(max = 50, message = "用户关注渠道来源 {size}")
    private String subscribeScene;    
    
    /**
    * 关注时间 
    */    
    @ApiModelProperty(value = "关注时间 ", allowableValues = "", position = 5, notes = "")
    private LocalDateTime subscribeTime;    
    
    /**
    * 关注次数 
    */    
    @ApiModelProperty(value = "关注次数 ", allowableValues = "", position = 6, notes = "")
	@Positive(message = "关注次数 {positive}")
    private Integer subscribeNum;    
    
    /**
    * 取消关注时间 
    */    
    @ApiModelProperty(value = "取消关注时间 ", allowableValues = "", position = 7, notes = "")
    private LocalDateTime cancelSubscribeTime;    
    
    /**
    * 用户标识 
    */    
    @ApiModelProperty(value = "用户标识 ", allowableValues = "", position = 8, notes = "")
    @Size(max = 64, message = "用户标识 {size}")
    private String openId;    
    
    /**
    * 昵称 
    */    
    @ApiModelProperty(value = "昵称 ", allowableValues = "", position = 9, notes = "")
    @Size(max = 200, message = "昵称 {size}")
    private String nickname;    
    
    /**
    * 性别 
    */    
    @ApiModelProperty(value = "性别 ", allowableValues = "", position = 10, notes = "")
    private String gender;
    
    /**
    * 所在城市 
    */    
    @ApiModelProperty(value = "所在城市 ", allowableValues = "", position = 11, notes = "")
    @Size(max = 64, message = "所在城市 {size}")
    private String city;    
    
    /**
    * 所在国家 
    */    
    @ApiModelProperty(value = "所在国家 ", allowableValues = "", position = 12, notes = "")
    @Size(max = 64, message = "所在国家 {size}")
    private String country;    
    
    /**
    * 所在省份 
    */    
    @ApiModelProperty(value = "所在省份 ", allowableValues = "", position = 13, notes = "")
    @Size(max = 64, message = "所在省份 {size}")
    private String province;    
    
    /**
    * 电话
    */    
    @ApiModelProperty(value = "电话", allowableValues = "", position = 14, notes = "")
    @Size(max = 13, message = "电话{size}")
    private String phone;    
    
    /**
    * 用户语言
    */    
    @ApiModelProperty(value = "用户语言", allowableValues = "", position = 15, notes = "")
    @Size(max = 64, message = "用户语言{size}")
    private String language;    
    
    /**
    * 头像
    */    
    @ApiModelProperty(value = "头像", allowableValues = "", position = 16, notes = "")
    @Size(max = 1000, message = "头像{size}")
    private String avatarUrl;    
    
    /**
    *  union_id
    */    
    @ApiModelProperty(value = " union_id", allowableValues = "", position = 17, notes = "")
    @Size(max = 64, message = " union_id{size}")
    private String unionId;    
    
    /**
    * 用户组
    */    
    @ApiModelProperty(value = "用户组", allowableValues = "", position = 18, notes = "")
    @Size(max = 64, message = "用户组{size}")
    private String groupId;    
    
    /**
    * 标签列表
    */    
    @ApiModelProperty(value = "标签列表", allowableValues = "", position = 19, notes = "")
    @Size(max = 64, message = "标签列表{size}")
    private String tagIdList;    
    
    /**
    * 二维码扫码场景
    */    
    @ApiModelProperty(value = "二维码扫码场景", allowableValues = "", position = 20, notes = "")
    @Size(max = 64, message = "二维码扫码场景{size}")
    private String qrScene;    
    
    /**
    * 地理位置纬度
    */    
    @ApiModelProperty(value = "地理位置纬度", allowableValues = "", position = 21, notes = "")
    @Size(max = 64, message = "地理位置纬度{size}")
    private String latitude;    
    
    /**
    * 地理位置经度
    */    
    @ApiModelProperty(value = "地理位置经度", allowableValues = "", position = 22, notes = "")
    private Double longitude;    
    
    /**
    * 地理位置精度
    */    
    @ApiModelProperty(value = "地理位置精度", allowableValues = "", position = 23, notes = "")
    private Double accuracy;    
    
    /**
    * 会话密钥
    */    
    @ApiModelProperty(value = "会话密钥", allowableValues = "", position = 24, notes = "")
    @Size(max = 200, message = "会话密钥{size}")
    private String sessionKey;    
    
}