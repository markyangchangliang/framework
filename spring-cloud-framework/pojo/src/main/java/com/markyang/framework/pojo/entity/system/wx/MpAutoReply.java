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
 * 微信自动回复(MpAutoReply)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:06:50
 */
@ApiModel(value = "微信自动回复实体类", description = "微信自动回复实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`wx_mp_auto_reply`")
public class MpAutoReply extends AbstractBaseEntity {

    private static final long serialVersionUID = 578704293110632339L;
    
    /**
    * 备注
    */
    @ApiModelProperty(value = "备注,varchar(200)",position = 1)
    private String remark;
    /**
    * 类型（1、关注时回复；2、消息回复；3、关键词回复）
    */
    @ApiModelProperty(value = "类型（1、关注时回复；2、消息回复；3、关键词回复）,char(2)",position = 2,required = true)
    private String type;
    /**
    * 关键字
    */
    @ApiModelProperty(value = "关键字,varchar(64)", position = 3,required = true)
    private String requestKeyword;
    /**
    * 请求消息类型（text：文本；image：图片；voice：语音；video：视频；shortvideo：小视频；location：地理位置）
    */
    @ApiModelProperty(value = "请求消息类型（text：文本；image：图片；voice：语音；video：视频；shortvideo：小视频；location：地理位置）,char(10)", position = 4,required = true)
    @DictField
    private String requestType;
    /**
     * 请求消息类型（text：文本；image：图片；voice：语音；video：视频；shortvideo：小视频；location：地理位置）
     */
    @ApiModelProperty(value = "请求消息类型（text：文本；image：图片；voice：语音；video：视频；shortvideo：小视频；location：地理位置）,char(10)", position = 4,required = true)
    @TableField(exist = false)
    private String requestTypeName;
    /**
    * 回复消息类型（text：文本；image：图片；voice：语音；video：视频；music：音乐；news：图文）
    */
    @ApiModelProperty(value = "回复消息类型（text：文本；image：图片；voice：语音；video：视频；music：音乐；news：图文）,char(10)", position = 5)
    @DictField
    private String replyType;
    /**
     * 消息类型（text：文本；image：图片等）
     */
    @ApiModelProperty(value = "消息类型（text：文本；image：图片等）,char(20)", position = 8)
    @TableField(exist = false)
    private String replyTypeName;
    /**
    * 回复类型文本匹配类型（1、全匹配，2、半匹配）
    */
    @ApiModelProperty(value = "回复类型文本匹配类型（1、全匹配，2、半匹配）,char(10)", position = 6,required = true)
    @DictField
    private String replyMatchedType;
    /**
     * 回复类型文本匹配类型（1、全匹配，2、半匹配）
     */
    @ApiModelProperty(value = "回复类型文本匹配类型（1、全匹配，2、半匹配）,char(10)", position = 6,required = true)
    @TableField(exist = false)
    private String replyMatchedTypeName;
    /**
    * 回复类型文本保存文字
    */
    @ApiModelProperty(value = "回复类型文本保存文字,text", position = 7)
    private String replyContent;
    /**
    * 回复类型imge、voice、news、video的mediaID或音乐缩略图的媒体id 
    */
    @ApiModelProperty(value = "回复类型imge、voice、news、video的mediaID或音乐缩略图的媒体id ,varchar(64)", position = 8)
    private String replyMediaId;
    /**
    * 回复的素材名、视频和音乐的标题
    */
    @ApiModelProperty(value = "回复的素材名、视频和音乐的标题,varchar(100)", position = 9)
    private String replyName;
    /**
    * 视频和音乐的描述
    */
    @ApiModelProperty(value = "视频和音乐的描述,varchar(200)", position = 10)
    private String replyDesc;
    /**
    * 链接
    */
    @ApiModelProperty(value = "链接,varchar(500)", position = 11)
    private String replyUrl;
    /**
    * 高质量链接
    */
    @ApiModelProperty(value = "高质量链接,varchar(500)", position = 12)
    private String replyVedioHqUrl;
    /**
    * 缩略图的媒体id
    */
    @ApiModelProperty(value = "缩略图的媒体id,varchar(64)", position = 13)
    private String replyThumbMediaId;
    /**
    * 缩略图url 
    */
    @ApiModelProperty(value = "缩略图url ,varchar(500)", position = 14)
    private String replyThumbUrl;
    /**
    * 图文消息的内容
    */
    @ApiModelProperty(value = "图文消息的内容,mediumtext", position = 15)
    private String content;

}