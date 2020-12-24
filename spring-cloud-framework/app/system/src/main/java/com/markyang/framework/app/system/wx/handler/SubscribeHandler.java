package com.markyang.framework.app.system.wx.handler;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.markyang.framework.app.system.wx.mp.service.MpAutoReplyService;
import com.markyang.framework.app.system.wx.mp.service.MpUserService;
import com.markyang.framework.app.system.wx.enumeration.WechatStatusEnum;
import com.markyang.framework.pojo.entity.system.wx.MpAutoReply;
import com.markyang.framework.pojo.entity.system.wx.MpUser;
import com.markyang.framework.util.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

/**
 * 用户关注公众号处理器
 * @author yangchangliang
 */
@Slf4j
@Component
@AllArgsConstructor
public class SubscribeHandler extends AbstractHandler {

  private final MpAutoReplyService mpAutoReplyService;
  private final MpUserService mpUserService;
  private final MsgHandler msgHandler;

  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                  Map<String, Object> context, WxMpService weixinService,
                                  WxSessionManager sessionManager) throws WxErrorException {
    log.info("新关注用户 OPENID: " + wxMessage.getFromUser());
    // 获取微信用户基本信息
    WxMpUser userWxInfo = weixinService.getUserService()
        .userInfo(wxMessage.getFromUser(), null);
    if (userWxInfo != null) {
      List<MpUser> mpUsers = mpUserService.list(Wrappers.<MpUser>lambdaQuery().eq(MpUser::getOpenId, userWxInfo.getOpenId()));
      MpUser user = this.setWxUserValue(userWxInfo);
      if (mpUsers.isEmpty()) {
        //第一次关注
        user.setSubscribeNum(1);
        mpUserService.add(user);
      } else {
        //曾经关注过
        user.setId(mpUsers.get(0).getId());
        user.setSubscribeNum(mpUsers.get(0).getSubscribeNum() + 1);
        mpUserService.update(user);
      }
      //发送关注消息
      List<MpAutoReply> searchedList = mpAutoReplyService.list(Wrappers.<MpAutoReply>lambdaQuery().eq(MpAutoReply::getType, WechatStatusEnum.Autoreply1.getValue()));
      return msgHandler.getOutMessage(wxMessage, searchedList, userWxInfo);
    }
    return null;
  }

  /**
   * 组装用户信息
   */
    public MpUser setWxUserValue(WxMpUser userWxInfo) {

      return MpUser.builder().appType(WechatStatusEnum.TypeApp.getValue())
          .subscribe(WechatStatusEnum.Subscribe.getValue()).subscribeScene(userWxInfo.getSubscribeScene())
          .subscribeTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(userWxInfo.getSubscribeTime()), ZoneId.systemDefault()))
          .openId(userWxInfo.getOpenId()).nickname(userWxInfo.getNickname()).gender(String.valueOf(userWxInfo.getSex()))
          .city(userWxInfo.getCity()).country(userWxInfo.getCountry()).province(userWxInfo.getProvince())
          .language(userWxInfo.getLanguage()).remark(userWxInfo.getRemark()).avatarUrl(userWxInfo.getHeadImgUrl())
          .unionId(userWxInfo.getUnionId()).groupId(JsonUtils.toJson(userWxInfo.getGroupId())).qrScene(userWxInfo.getQrScene())
          .build();
    }
  }

