package com.markyang.framework.pojo.entity.system.wx;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.common.support.TreeNode;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 微信自定义菜单(MpMenu)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:06:50
 */
@ApiModel(value = "微信自定义菜单实体类", description = "微信自定义菜单实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`wx_mp_menu`")
public class MpMenu extends AbstractBaseEntity implements TreeNode {

    private static final long serialVersionUID = -43664137896984047L;
    
    /**
    * 父菜单ID 
    */
    @ApiModelProperty(value = "父菜单ID ,varchar(128)", position = 1)
    private String parentId;
    /**
    * 菜单类型
    */
    @ApiModelProperty(value = "菜单类型,char(20)", position = 2)
    private String type;
    /**
    * 菜单名
    */
    @ApiModelProperty(value = "菜单名,varchar(20)", position = 3)
    private String name;
    /**
    *  view、miniprogram保存链接
    */
    @ApiModelProperty(value = " view、miniprogram保存链接,varchar(500)", position = 4)
    private String url;
    /**
    * 小程序的appid 
    */
    @ApiModelProperty(value = "小程序的appid ,varchar(32)", position = 5)
    private String maAppId;
    /**
    * 小程序页面路径
    */
    @ApiModelProperty(value = "小程序页面路径,varchar(100)", position = 6)
    private String maPagePath;
    /**
    * 回复消息类型（text：文本；image：图片）
    */
    @ApiModelProperty(value = "回复消息类型（text：文本；image：图片）,char(10)", position = 7)
    private String replyType;
    /**
    * Text:保存文字
    */
    @ApiModelProperty(value = "Text:保存文字,text", position = 8)
    private String replyContent;
    /**
    * imge、voice、news、video：mediaID
    */
    @ApiModelProperty(value = "imge、voice、news、video：mediaID,varchar(100)", position = 9)
    private String replyMediaId;
    /**
    * 素材名、视频和音乐的标题
    */
    @ApiModelProperty(value = "素材名、视频和音乐的标题,varchar(100)", position = 10)
    private String replyName;
    /**
    * 视频和音乐的描述
    */
    @ApiModelProperty(value = "视频和音乐的描述,varchar(200)", position = 11)
    private String replyDesc;
    /**
    * 链接
    */
    @ApiModelProperty(value = "链接,varchar(500)", position = 12)
    private String replyUrl;
    /**
    * 高质量链接
    */
    @ApiModelProperty(value = "高质量链接,varchar(500)", position = 13)
    private String replyVedioHqUrl;
    /**
    * 缩略图的媒体id 
    */
    @ApiModelProperty(value = "缩略图的媒体id ,varchar(64)", position = 14)
    private String replyThumbMediaId;
    /**
    * 缩略图url 
    */
    @ApiModelProperty(value = "缩略图url ,varchar(500)", position = 15)
    private String replyThumbUrl;
    /**
    * 图文消息的内容
    */
    @ApiModelProperty(value = "图文消息的内容,mediumtext", position = 16)
    private String content;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<MpMenu> children;
    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<MpMenu> child;
    /**
     * 设置子节点
     *
     * @param children 孩子节点
     */
    @Override
    @SuppressWarnings("unchecked")
    public void setChildren(List<? extends TreeNode> children) {
        this.children = (List<MpMenu>) children;
    }

    /**
     * 获取子节点
     *
     * @return 子节点
     */
    @Override
    public List<? extends TreeNode> getChildren() {
        return this.children;
    }
}