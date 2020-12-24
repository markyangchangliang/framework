package com.markyang.framework.pojo.entity.system.wx;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import com.markyang.framework.pojo.entity.support.DictField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

 /**
 * 微信用户(MpUser)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:06:50
 */
@ApiModel(value = "微信用户实体类", description = "微信用户实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`wx_mp_user`")
public class MpUser extends AbstractBaseEntity {

    private static final long serialVersionUID = -93070591115383536L;
    
    /**
    * 用户备注
    */
    @ApiModelProperty(value = "用户备注,varchar(100)", position = 1)
    private String remark;
    /**
    * 应用类型
    */
    @ApiModelProperty(value = "应用类型,char(2)", position = 2)
    private String appType;
    /**
    * 是否订阅
    */
    @ApiModelProperty(value = "是否订阅,char(2)", position = 3)
    @DictField
    private String subscribe;
     /**
      * 是否订阅中文
      */
     @ApiModelProperty(value = "是否订阅,char(2)", position = 3)
     @TableField(exist = false)
     private String subscribeName;
    /**
    * 用户关注渠道来源 
    */
    @ApiModelProperty(value = "用户关注渠道来源 ,varchar(50)", position = 4)
    @DictField
    private String subscribeScene;
     /**
      * 用户关注渠道来源名称
      */
     @ApiModelProperty(value = "用户关注渠道来源名称 ,varchar(50)", position = 4)
     @TableField(exist = false)
     private String subscribeSceneName;
    /**
    * 关注时间 
    */
    @ApiModelProperty(value = "关注时间 ,datetime", position = 5)
    private LocalDateTime subscribeTime;
    /**
    * 关注次数 
    */
    @ApiModelProperty(value = "关注次数 ,int", position = 6)
    private Integer subscribeNum;
    /**
    * 取消关注时间 
    */
    @ApiModelProperty(value = "取消关注时间 ,datetime", position = 7)
    private LocalDateTime cancelSubscribeTime;
    /**
    * 用户标识 
    */
    @ApiModelProperty(value = "用户标识 ,varchar(64)", position = 8)
    private String openId;
    /**
    * 昵称 
    */
    @ApiModelProperty(value = "昵称 ,varchar(200)", position = 9)
    private String nickname;
    /**
    * 性别 
    */
    @ApiModelProperty(value = "性别 ,char(2)", position = 10)
    @DictField
    private String gender;
     /**
      * 性别 名称
      */
     @ApiModelProperty(value = "性别 ,char(2)", position = 10)
     @TableField(exist = false)
     private String genderName;
    /**
    * 所在城市 
    */
    @ApiModelProperty(value = "所在城市 ,varchar(64)", position = 11)
    private String city;
    /**
    * 所在国家 
    */
    @ApiModelProperty(value = "所在国家 ,varchar(64)", position = 12)
    private String country;
    /**
    * 所在省份 
    */
    @ApiModelProperty(value = "所在省份 ,varchar(64)", position = 13)
    private String province;
    /**
    * 电话
    */
    @ApiModelProperty(value = "电话,varchar(13)", position = 14)
    private String phone;
    /**
    * 用户语言
    */
    @ApiModelProperty(value = "用户语言,varchar(64)", position = 15)
    private String language;
    /**
    * 头像
    */
    @ApiModelProperty(value = "头像,varchar(1000)", position = 16)
    private String avatarUrl;
    /**
    *  union_id
    */
    @ApiModelProperty(value = " union_id,varchar(64)", position = 17)
    private String unionId;
    /**
    * 用户组
    */
    @ApiModelProperty(value = "用户组,varchar(64)", position = 18)
    private String groupId;
    /**
    * 标签列表
    */
    @ApiModelProperty(value = "标签列表,varchar(64)", position = 19)
    private String tagIdList;
    /**
    * 二维码扫码场景
    */
    @ApiModelProperty(value = "二维码扫码场景,varchar(64)", position = 20)
    @DictField
    private String qrScene;
     /**
      * 二维码扫码场景
      */
     @ApiModelProperty(value = "二维码扫码场景,varchar(64)", position = 20)
     @TableField(exist = false)
     private String qrSceneName;
    /**
    * 地理位置纬度
    */
    @ApiModelProperty(value = "地理位置纬度,varchar(64)", position = 21)
    private String latitude;
    /**
    * 地理位置经度
    */
    @ApiModelProperty(value = "地理位置经度,float", position = 22)
    private Double longitude;
    /**
    * 地理位置精度
    */
    @ApiModelProperty(value = "地理位置精度,float", position = 23)
    private Double accuracy;
    /**
    * 会话密钥
    */
    @ApiModelProperty(value = "会话密钥,varchar(200)", position = 24)
    private String sessionKey;

}