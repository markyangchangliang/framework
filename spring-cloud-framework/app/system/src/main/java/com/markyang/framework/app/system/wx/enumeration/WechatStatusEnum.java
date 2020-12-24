package com.markyang.framework.app.system.wx.enumeration;

import com.markyang.framework.pojo.common.support.FrameworkEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 微信枚举
 * @author yangchangliang
 */

@AllArgsConstructor
@Getter
public enum WechatStatusEnum implements FrameworkEnum {
    /**
     * 消息自动回复类型（1、关注时回复；2、消息回复；3、关键词回复）
     */
    Autoreply1("1"),
    Autoreply2("2"),
    Autoreply3("3"),
    /**
     * 匹配类型类型（1。完全匹配    2.半匹配）
     */
    Matched1("1"),
    Matched2("2"),
    /**
     * 已读
     */
    Read("1"),
    /**
     * 未读
     */
    DisRead("0"),
    /**
     * 已关注
     */
    Subscribe("true"),
    /**
     * 为关注
     */
    UnSubscribe("false"),
    /**
     * 应用类型 1:小程序
     */
    Type("1"),
    /**
     * 中文语言类型
     */
    Lang ("zh_CN"),
    /**
     * 消息分类（1、用户发给公众号；2、公众号发给用户；）
     */
     FromUser("1"),
     ToUser( "2"),
    /**
     * 应用类型 2:公众号
     */
    TypeApp("2"),
    /**
     * 公众号名称
     */
    AppName("知行锐景");

    private String value;

    /**
     * 获取枚举值
     *
     * @return 枚举值
     */
    @Override
    public String getValue() {
        return this.value;
    }
}
