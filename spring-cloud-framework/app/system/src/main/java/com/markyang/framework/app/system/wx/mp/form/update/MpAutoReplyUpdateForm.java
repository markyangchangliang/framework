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
 * 微信自动回复(MpAutoReply)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:10:42
 */
@ApiModel(value = "微信自动回复数据表单", description = "微信自动回复表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MpAutoReplyUpdateForm extends AbstractUpdateBaseForm {

    /**
    * 备注
    */    
    @ApiModelProperty(value = "备注", allowableValues = "", position = 1, notes = "")
    @Size(max = 200, message = "备注{size}")
    private String remark;    
    
    /**
    * 类型（1、关注时回复；2、消息回复；3、关键词回复）
    */    
    @ApiModelProperty(value = "类型（1、关注时回复；2、消息回复；3、关键词回复）", allowableValues = "", position = 2, notes = "")
    private String type;    
    
    /**
    * 关键字
    */    
    @ApiModelProperty(value = "关键字", allowableValues = "", position = 3, notes = "")
    @Size(max = 64, message = "关键字{size}")
    private String requestKeyword;    
    
    /**
    * 请求消息类型（text：文本；image：图片；voice：语音；video：视频；shortvideo：小视频；location：地理位置）
    */    
    @ApiModelProperty(value = "请求消息类型（text：文本；image：图片；voice：语音；video：视频；shortvideo：小视频；location：地理位置）", allowableValues = "", position = 4, notes = "")
    private String requestType;    
    
    /**
    * 回复消息类型（text：文本；image：图片；voice：语音；video：视频；music：音乐；news：图文）
    */    
    @ApiModelProperty(value = "回复消息类型（text：文本；image：图片；voice：语音；video：视频；music：音乐；news：图文）", allowableValues = "", position = 5, notes = "")
    private String replyType;    
    
    /**
    * 回复类型文本匹配类型（1、全匹配，2、半匹配）
    */    
    @ApiModelProperty(value = "回复类型文本匹配类型（1、全匹配，2、半匹配）", allowableValues = "", position = 6, notes = "")
    private String replyMatchedType;    
    
    /**
    * 回复类型文本保存文字
    */    
    @ApiModelProperty(value = "回复类型文本保存文字", allowableValues = "", position = 7, notes = "")
    private String replyContent;    
    
    /**
    * 回复类型imge、voice、news、video的mediaID或音乐缩略图的媒体id 
    */    
    @ApiModelProperty(value = "回复类型imge、voice、news、video的mediaID或音乐缩略图的媒体id ", allowableValues = "", position = 8, notes = "")
    @Size(max = 64, message = "回复类型imge、voice、news、video的mediaID或音乐缩略图的媒体id {size}")
    private String replyMediaId;    
    
    /**
    * 回复的素材名、视频和音乐的标题
    */    
    @ApiModelProperty(value = "回复的素材名、视频和音乐的标题", allowableValues = "", position = 9, notes = "")
    @Size(max = 100, message = "回复的素材名、视频和音乐的标题{size}")
    private String replyName;    
    
    /**
    * 视频和音乐的描述
    */    
    @ApiModelProperty(value = "视频和音乐的描述", allowableValues = "", position = 10, notes = "")
    @Size(max = 200, message = "视频和音乐的描述{size}")
    private String replyDesc;    
    
    /**
    * 链接
    */    
    @ApiModelProperty(value = "链接", allowableValues = "", position = 11, notes = "")
    @Size(max = 500, message = "链接{size}")
    private String replyUrl;    
    
    /**
    * 高质量链接
    */    
    @ApiModelProperty(value = "高质量链接", allowableValues = "", position = 12, notes = "")
    @Size(max = 500, message = "高质量链接{size}")
    private String replyVedioHqUrl;    
    
    /**
    * 缩略图的媒体id
    */    
    @ApiModelProperty(value = "缩略图的媒体id", allowableValues = "", position = 13, notes = "")
    @Size(max = 64, message = "缩略图的媒体id{size}")
    private String replyThumbMediaId;    
    
    /**
    * 缩略图url 
    */    
    @ApiModelProperty(value = "缩略图url ", allowableValues = "", position = 14, notes = "")
    @Size(max = 500, message = "缩略图url {size}")
    private String replyThumbUrl;    
    
    /**
    * 图文消息的内容
    */    
    @ApiModelProperty(value = "图文消息的内容", allowableValues = "", position = 15, notes = "")
    private Object content;    
    
}