package com.markyang.framework.pojo.entity.system.wx;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import com.markyang.framework.pojo.entity.support.DictField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 微信消息(MpMessage)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:06:50
 */
@ApiModel(value = "微信消息实体类", description = "微信消息实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`wx_mp_message`")
public class MpMessage extends AbstractBaseEntity {

    private static final long serialVersionUID = -80552269383338961L;
    
    /**
    * 备注
    */
    @ApiModelProperty(value = "备注,varchar(128)", position = 1)
    private String remark;
    /**
    * 公众号名称
    */
    @ApiModelProperty(value = "公众号名称,varchar(500)", position = 2)
    private String appName;
    /**
    * 公众号logo
    */
    @ApiModelProperty(value = "公众号logo,varchar(500)", position = 3)
    private String appLogo;
    /**
    * 微信用户ID
    */
    @ApiModelProperty(value = "微信用户ID,varchar(32)", position = 4)
    private String openId;
    /**
    * 昵称
    */
    @ApiModelProperty(value = "昵称,varchar(200)", position = 5)
    private String nickname;
    /**
    * 头像
    */
    @ApiModelProperty(value = "头像,varchar(1000)", position = 6)
    private String avatarUrl;
    /**
    * 消息分类（1、用户发给公众号；2、公众号发给用户；）
    */
    @ApiModelProperty(value = "消息分类（1、用户发给公众号；2、公众号发给用户；）,char(2)", position = 7)
    private String type;
    /**
    * 消息类型（text：文本；image：图片等）
    */
    @ApiModelProperty(value = "消息类型（text：文本；image：图片等）,char(20)", position = 8)
    @DictField
    private String replyType;
     /**
      * 消息类型（text：文本；image：图片等）
      */
     @ApiModelProperty(value = "消息类型（text：文本；image：图片等）,char(20)", position = 8)
     @TableField(exist = false)
     private String replyTypeName;
    /**
    * 事件类型（subscribe：关注；unsubscribe：取关；CLICK、VIEW：菜单事件）
    */
    @ApiModelProperty(value = "事件类型（subscribe：关注；unsubscribe：取关；CLICK、VIEW：菜单事件）,char(20)", position = 9)
    private String event;
    /**
    * 回复类型文本保存文字、地理位置信息
    */
    @ApiModelProperty(value = "回复类型文本保存文字、地理位置信息,text", position = 10)
    private String repContent;
    /**
    * 回复类型imge、voice、news、video的mediaID或音乐缩略图的媒体id
    */
    @ApiModelProperty(value = "回复类型imge、voice、news、video的mediaID或音乐缩略图的媒体id,varchar(64)", position = 11)
    private String mediaId;
    /**
    * 回复的素材名、视频和音乐的标题
    */
    @ApiModelProperty(value = "回复的素材名、视频和音乐的标题,varchar(100)", position = 12)
    private String name;
    /**
    * 视频和音乐的描述
    */
    @ApiModelProperty(value = "视频和音乐的描述,varchar(200)", position = 13)
    private String description;
    /**
    * 链接
    */
    @ApiModelProperty(value = "链接,varchar(500)", position = 14)
    private String url;
    /**
    * 高质量链接
    */
    @ApiModelProperty(value = "高质量链接,varchar(500)", position = 15)
    private String vedioHqUrl;
    /**
    * 图文消息的内容
    */
    @ApiModelProperty(value = "图文消息的内容,mediumtext", position = 16)
    private String content;
    /**
    * 缩略图的媒体id
    */
    @ApiModelProperty(value = "缩略图的媒体id,varchar(128)", position = 17)
    private String thumbMediaId;
    /**
    * 缩略图url 
    */
    @ApiModelProperty(value = "缩略图url ,varchar(500)", position = 18)
    private String thumbUrl;
    /**
    * 地理位置纬度
    */
    @ApiModelProperty(value = "地理位置纬度,float", position = 19)
    private Double locationLatitude;
    /**
    * 地理位置经度
    */
    @ApiModelProperty(value = "地理位置经度,float", position = 20)
    private Double locationLongitude;
    /**
    * 地图缩放大小
    */
    @ApiModelProperty(value = "地图缩放大小,float", position = 21)
    private Double mapScale;
    /**
    * 已读标记
    */
    @ApiModelProperty(value = "已读标记,char(2)", position = 22)
    private String readFlag;

}