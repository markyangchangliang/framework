package com.markyang.framework.app.system.wx.mp.form.update;

import com.markyang.framework.service.core.form.AbstractUpdateBaseForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Size;


 /**
 * 微信消息(MpMessage)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:10:42
 */
@ApiModel(value = "微信消息数据表单", description = "微信消息表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MpMessageUpdateForm extends AbstractUpdateBaseForm {

    /**
    * 备注
    */    
    @ApiModelProperty(value = "备注", allowableValues = "", position = 1, notes = "")
    @Size(max = 128, message = "备注{size}")
    private String remark;    
    
    /**
    * 公众号名称
    */    
    @ApiModelProperty(value = "公众号名称", allowableValues = "", position = 2, notes = "")
    @Size(max = 500, message = "公众号名称{size}")
    private String appName;    
    
    /**
    * 公众号logo
    */    
    @ApiModelProperty(value = "公众号logo", allowableValues = "", position = 3, notes = "")
    @Size(max = 500, message = "公众号logo{size}")
    private String appLogo;    
    
    /**
    * 微信用户ID
    */    
    @ApiModelProperty(value = "微信用户ID", allowableValues = "", position = 4, notes = "")
    @Size(max = 32, message = "微信用户ID{size}")
    private String openId;    
    
    /**
    * 昵称
    */    
    @ApiModelProperty(value = "昵称", allowableValues = "", position = 5, notes = "")
    @Size(max = 200, message = "昵称{size}")
    private String nickname;    
    
    /**
    * 头像
    */    
    @ApiModelProperty(value = "头像", allowableValues = "", position = 6, notes = "")
    @Size(max = 1000, message = "头像{size}")
    private String avatarUrl;    
    
    /**
    * 消息分类（1、用户发给公众号；2、公众号发给用户；）
    */    
    @ApiModelProperty(value = "消息分类（1、用户发给公众号；2、公众号发给用户；）", allowableValues = "", position = 7, notes = "")
    private String type;    
    
    /**
    * 消息类型（text：文本；image：图片等）
    */    
    @ApiModelProperty(value = "消息类型（text：文本；image：图片等）", allowableValues = "", position = 8, notes = "")
    private String replyType;    
    
    /**
    * 事件类型（subscribe：关注；unsubscribe：取关；CLICK、VIEW：菜单事件）
    */    
    @ApiModelProperty(value = "事件类型（subscribe：关注；unsubscribe：取关；CLICK、VIEW：菜单事件）", allowableValues = "", position = 9, notes = "")
    private String event;    
    
    /**
    * 回复类型文本保存文字、地理位置信息
    */    
    @ApiModelProperty(value = "回复类型文本保存文字、地理位置信息", allowableValues = "", position = 10, notes = "")
    private String repContent;    
    
    /**
    * 回复类型imge、voice、news、video的mediaID或音乐缩略图的媒体id
    */    
    @ApiModelProperty(value = "回复类型imge、voice、news、video的mediaID或音乐缩略图的媒体id", allowableValues = "", position = 11, notes = "")
    @Size(max = 64, message = "回复类型imge、voice、news、video的mediaID或音乐缩略图的媒体id{size}")
    private String mediaId;    
    
    /**
    * 回复的素材名、视频和音乐的标题
    */    
    @ApiModelProperty(value = "回复的素材名、视频和音乐的标题", allowableValues = "", position = 12, notes = "")
    @Size(max = 100, message = "回复的素材名、视频和音乐的标题{size}")
    private String name;    
    
    /**
    * 视频和音乐的描述
    */    
    @ApiModelProperty(value = "视频和音乐的描述", allowableValues = "", position = 13, notes = "")
    @Size(max = 200, message = "视频和音乐的描述{size}")
    private String description;    
    
    /**
    * 链接
    */    
    @ApiModelProperty(value = "链接", allowableValues = "", position = 14, notes = "")
    @Size(max = 500, message = "链接{size}")
    private String url;    
    
    /**
    * 高质量链接
    */    
    @ApiModelProperty(value = "高质量链接", allowableValues = "", position = 15, notes = "")
    @Size(max = 500, message = "高质量链接{size}")
    private String vedioHqUrl;    
    
    /**
    * 图文消息的内容
    */    
    @ApiModelProperty(value = "图文消息的内容", allowableValues = "", position = 16, notes = "")
    private Object content;    
    
    /**
    * 缩略图的媒体id
    */    
    @ApiModelProperty(value = "缩略图的媒体id", allowableValues = "", position = 17, notes = "")
    @Size(max = 128, message = "缩略图的媒体id{size}")
    private String thumbMediaId;    
    
    /**
    * 缩略图url 
    */    
    @ApiModelProperty(value = "缩略图url ", allowableValues = "", position = 18, notes = "")
    @Size(max = 500, message = "缩略图url {size}")
    private String thumbUrl;    
    
    /**
    * 地理位置纬度
    */    
    @ApiModelProperty(value = "地理位置纬度", allowableValues = "", position = 19, notes = "")
    private Double locationLatitude;    
    
    /**
    * 地理位置经度
    */    
    @ApiModelProperty(value = "地理位置经度", allowableValues = "", position = 20, notes = "")
    private Double locationLongitude;    
    
    /**
    * 地图缩放大小
    */    
    @ApiModelProperty(value = "地图缩放大小", allowableValues = "", position = 21, notes = "")
    private Double mapScale;    
    
    /**
    * 已读标记
    */    
    @ApiModelProperty(value = "已读标记", allowableValues = "", position = 22, notes = "")
    private String readFlag;    
    
}