package com.markyang.framework.app.system.wx.handler;

import com.markyang.framework.app.system.wx.mp.form.search.MpAutoReplySearchForm;
import com.markyang.framework.app.system.wx.mp.service.MpAutoReplyService;
import com.markyang.framework.app.system.wx.mp.service.MpMessageService;
import com.markyang.framework.app.system.wx.enumeration.WechatStatusEnum;
import com.markyang.framework.pojo.entity.system.wx.MpAutoReply;
import com.markyang.framework.pojo.entity.system.wx.MpMessage;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 消息自动回复处理
 * @author yangchangliang
 */
@Component
@AllArgsConstructor
public class MsgHandler extends AbstractHandler {
  private final MpAutoReplyService mpAutoReplyService;
  private final MpMessageService mpMessageService;
  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> map,
                                  WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
    //组装回复消息
    WxMpUser wxMpUser = wxMpService.getUserService().userInfo(wxMessage.getFromUser());
    if (!wxMessage.getMsgType().equals(WxConsts.XmlMsgType.EVENT)) {
      WxMpXmlOutMessage rs;
      //1、先处理是否有文本关键字回复
      if (WxConsts.KefuMsgType.TEXT.equals(wxMessage.getMsgType())) {
        //先全匹配
        List<MpAutoReply> searchedList = mpAutoReplyService.getSearchedList(MpAutoReplySearchForm.builder()
            .requestKeyword(wxMessage.getContent())
            .type(WechatStatusEnum.Autoreply3.getValue())
            .replyMatchedType(WechatStatusEnum.Matched1.getValue())
            .build());
        if (searchedList.size() > 0) {
          rs = this.getOutMessage(wxMessage, searchedList, wxMpUser);
          if (rs != null) {
            return rs;
          }
        }
        //再半匹配
        List<MpAutoReply> searchedList1 = mpAutoReplyService.getSearchedList(MpAutoReplySearchForm.builder()
            .requestKeyword(wxMessage.getContent())
            .type(WechatStatusEnum.Autoreply3.getValue())
            .replyMatchedType(WechatStatusEnum.Matched2.getValue())
            .build());
        if (searchedList1.size() > 0) {
          rs = this.getOutMessage(wxMessage, searchedList1, wxMpUser);
          if (rs != null) {
            return rs;
          }
        }
      }
      //处理消息回复
      List<MpAutoReply> searchedList = mpAutoReplyService.getSearchedList(MpAutoReplySearchForm.builder()
          .type(WechatStatusEnum.Autoreply2.getValue())
          .requestType(wxMessage.getMsgType())
          .build());
      rs = this.getOutMessage(wxMessage, searchedList, wxMpUser);
      return rs;
    }
    return null;
  }

  /**
   * 组装回复消息，并记录消息
   */
  public  WxMpXmlOutMessage getOutMessage(WxMpXmlMessage wxMessage, List<MpAutoReply> listWxAutoReply, WxMpUser wxMpUser) {
    WxMpXmlOutMessage wxMpXmlOutMessage = null;
    if (listWxAutoReply != null && listWxAutoReply.size() > 0) {
      MpAutoReply wxAutoReply = listWxAutoReply.get(0);
      //构建实体，做入库准备
      MpMessage wxMsg =  MpMessage.builder().nickname(wxMpUser.getNickname()).avatarUrl(wxMpUser.getHeadImgUrl())
          .openId(wxMpUser.getOpenId()).event(wxMessage.getEvent()).repContent(wxMessage.getContent())
          .type(WechatStatusEnum.ToUser.getValue()).replyType(wxAutoReply.getReplyType())
          .name(wxAutoReply.getReplyName()).locationLatitude(wxMessage.getLatitude())
          .locationLongitude(wxMessage.getLongitude()).remark(wxMessage.getRemarkAmount())
          .description(wxAutoReply.getReplyDesc()).url(wxAutoReply.getReplyUrl())
          .mediaId(wxAutoReply.getReplyMediaId()).content(wxAutoReply.getContent()).appName(WechatStatusEnum.AppName.getValue())
          .build();
      switch (wxAutoReply.getReplyType()) {
        case WxConsts.KefuMsgType.NEWS:
          wxMpXmlOutMessage = buildNewsMessage(wxMessage, wxAutoReply);
          break;
        case WxConsts.KefuMsgType.IMAGE:
          wxMpXmlOutMessage = buildImageMessage(wxMessage, wxAutoReply);
          break;
        case WxConsts.KefuMsgType.VOICE:
          wxMpXmlOutMessage = buildVoiceMessage(wxMessage, wxAutoReply);
          break;
        case WxConsts.KefuMsgType.VIDEO:
          wxMpXmlOutMessage = buildVideoMessage(wxMessage, wxAutoReply);
          break;
        case WxConsts.KefuMsgType.MUSIC:
          wxMpXmlOutMessage = buildMusicMessage(wxMessage, wxMsg, wxAutoReply);
          break;
        default:
          wxMpXmlOutMessage = buildTextMessage(wxMessage, wxAutoReply);
          break;
      }
      //记录接收消息到本地
      mpMessageService.add(wxMsg);
    }
    return wxMpXmlOutMessage;
  }

  /**
   * 构建news消息
   */
  public  WxMpXmlOutMessage buildNewsMessage(WxMpXmlMessage wxMessage, MpAutoReply wxAutoReply) {
    WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
    item.setDescription(wxAutoReply.getReplyDesc());
    item.setPicUrl(wxAutoReply.getReplyUrl());
    item.setTitle(wxAutoReply.getReplyName());
    item.setUrl(wxAutoReply.getReplyUrl());
    return WxMpXmlOutMessage.NEWS()
        .fromUser(wxMessage.getToUser())
        .toUser(wxMessage.getFromUser())
        .addArticle(item)
        .build();
  }

  /**
   * 构建image消息
   */
  public WxMpXmlOutMessage buildImageMessage(WxMpXmlMessage wxMessage, MpAutoReply wxAutoReply) {
    return WxMpXmlOutMessage.IMAGE()
        .mediaId(wxAutoReply.getReplyMediaId())
        .fromUser(wxMessage.getToUser())
        .toUser(wxMessage.getFromUser())
        .build();
  }


  /**
   * 构建vioce消息
   */
  public WxMpXmlOutMessage buildVoiceMessage(WxMpXmlMessage wxMessage, MpAutoReply wxAutoReply) {
    return WxMpXmlOutMessage.VOICE()
        .mediaId(wxAutoReply.getReplyMediaId())
        .fromUser(wxMessage.getToUser())
        .toUser(wxMessage.getFromUser())
        .build();
  }

  /**
   * 构建video消息
   */
  public WxMpXmlOutMessage buildVideoMessage(WxMpXmlMessage wxMessage, MpAutoReply wxAutoReply) {
    return WxMpXmlOutMessage.VIDEO()
        .mediaId(wxAutoReply.getReplyMediaId())
        .fromUser(wxMessage.getToUser())
        .toUser(wxMessage.getFromUser())
        .title(wxAutoReply.getReplyName())
        .description(wxAutoReply.getReplyDesc())
        .build();
  }

  /**
   * 构建music消息
   */
  public WxMpXmlOutMessage buildMusicMessage(WxMpXmlMessage wxMessage, MpMessage wxMsg, MpAutoReply wxAutoReply) {
    wxMsg.setThumbMediaId(wxAutoReply.getReplyThumbMediaId());
    wxMsg.setThumbUrl(wxAutoReply.getReplyThumbUrl());
    return WxMpXmlOutMessage.MUSIC()
        .fromUser(wxMessage.getToUser())
        .toUser(wxMessage.getFromUser())
        .title(wxAutoReply.getReplyName())
        .description(wxAutoReply.getReplyDesc())
        .hqMusicUrl(wxAutoReply.getReplyVedioHqUrl())
        .musicUrl(wxAutoReply.getReplyUrl())
        .thumbMediaId(wxAutoReply.getReplyThumbMediaId())
        .build();
  }

  /**
   * 构建text消息
   */
  public WxMpXmlOutMessage buildTextMessage(WxMpXmlMessage wxMessage, MpAutoReply wxAutoReply) {
    return  WxMpXmlOutMessage.TEXT()
        .content(wxAutoReply.getReplyContent())
        .fromUser(wxMessage.getToUser())
        .toUser(wxMessage.getFromUser())
        .build();
  }

}
