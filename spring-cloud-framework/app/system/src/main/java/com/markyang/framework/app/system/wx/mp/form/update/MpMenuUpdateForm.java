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
 * 微信自定义菜单(MpMenu)更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:10:42
 */
@ApiModel(value = "微信自定义菜单数据表单", description = "微信自定义菜单表单类")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MpMenuUpdateForm extends AbstractUpdateBaseForm {

    /**
    * 父菜单ID 
    */    
    @ApiModelProperty(value = "父菜单ID ", allowableValues = "", position = 1, notes = "")
    @Size(max = 128, message = "父菜单ID {size}")
    private String parentId;    
    
    /**
    * 菜单类型
    */    
    @ApiModelProperty(value = "菜单类型", allowableValues = "", position = 2, notes = "")
    private String type;    
    
    /**
    * 菜单名
    */    
    @ApiModelProperty(value = "菜单名", allowableValues = "", position = 3, notes = "")
    @Size(max = 20, message = "菜单名{size}")
    private String name;    
    
    /**
    *  view、miniprogram保存链接
    */    
    @ApiModelProperty(value = " view、miniprogram保存链接", allowableValues = "", position = 4, notes = "")
    @Size(max = 500, message = " view、miniprogram保存链接{size}")
    private String url;    
    
    /**
    * 小程序的appid 
    */    
    @ApiModelProperty(value = "小程序的appid ", allowableValues = "", position = 5, notes = "")
    @Size(max = 32, message = "小程序的appid {size}")
    private String maAppId;    
    
    /**
    * 小程序页面路径
    */    
    @ApiModelProperty(value = "小程序页面路径", allowableValues = "", position = 6, notes = "")
    @Size(max = 100, message = "小程序页面路径{size}")
    private String maPagePath;    
    
    /**
    * 回复消息类型（text：文本；image：图片）
    */    
    @ApiModelProperty(value = "回复消息类型（text：文本；image：图片）", allowableValues = "", position = 7, notes = "")
    private String replyType;    
    
    /**
    * Text:保存文字
    */    
    @ApiModelProperty(value = "Text:保存文字", allowableValues = "", position = 8, notes = "")
    private String replyContent;    
    
    /**
    * imge、voice、news、video：mediaID
    */    
    @ApiModelProperty(value = "imge、voice、news、video：mediaID", allowableValues = "", position = 9, notes = "")
    @Size(max = 100, message = "imge、voice、news、video：mediaID{size}")
    private String replyMediaId;    
    
    /**
    * 素材名、视频和音乐的标题
    */    
    @ApiModelProperty(value = "素材名、视频和音乐的标题", allowableValues = "", position = 10, notes = "")
    @Size(max = 100, message = "素材名、视频和音乐的标题{size}")
    private String replyName;    
    
    /**
    * 视频和音乐的描述
    */    
    @ApiModelProperty(value = "视频和音乐的描述", allowableValues = "", position = 11, notes = "")
    @Size(max = 200, message = "视频和音乐的描述{size}")
    private String replyDesc;    
    
    /**
    * 链接
    */    
    @ApiModelProperty(value = "链接", allowableValues = "", position = 12, notes = "")
    @Size(max = 500, message = "链接{size}")
    private String replyUrl;    
    
    /**
    * 高质量链接
    */    
    @ApiModelProperty(value = "高质量链接", allowableValues = "", position = 13, notes = "")
    @Size(max = 500, message = "高质量链接{size}")
    private String replyVedioHqUrl;    
    
    /**
    * 缩略图的媒体id 
    */    
    @ApiModelProperty(value = "缩略图的媒体id ", allowableValues = "", position = 14, notes = "")
    @Size(max = 64, message = "缩略图的媒体id {size}")
    private String replyThumbMediaId;    
    
    /**
    * 缩略图url 
    */    
    @ApiModelProperty(value = "缩略图url ", allowableValues = "", position = 15, notes = "")
    @Size(max = 500, message = "缩略图url {size}")
    private String replyThumbUrl;    
    
    /**
    * 图文消息的内容
    */    
    @ApiModelProperty(value = "图文消息的内容", allowableValues = "", position = 16, notes = "")
    private Object content;    
    
}