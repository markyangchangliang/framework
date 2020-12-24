package com.markyang.framework.app.system.wx.mp.service.impl;

import com.markyang.framework.app.system.wx.mp.form.search.MpMessageSearchForm;
import com.markyang.framework.app.system.wx.mp.service.MpMessageService;
import com.markyang.framework.app.system.wx.mp.repository.MpMessageRepository;
import com.markyang.framework.pojo.entity.system.wx.MpMessage;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpMassNews;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.WxMpMassVideo;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import me.chanjar.weixin.mp.bean.material.WxMpNewsArticle;
import me.chanjar.weixin.mp.bean.result.WxMpMassUploadResult;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;

/**
 * 微信消息(MpMessage)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:09:22
 */
@Service
@AllArgsConstructor
public class MpMessageServiceImpl extends AbstractSearchableServiceImpl<MpMessage, MpMessageRepository, MpMessageSearchForm> implements MpMessageService {

  private final WxMpService wxMpService;


  /**
   * 创建空的或者带有默认值的实体对象
   *
   * @return 实体对象
   */
  @Override
  public MpMessage create() {
    return MpMessage.builder().build();
  }

  /**
   * 发送消息到微信公众号
   */
  @Override
  public boolean sendMessage(MpMessage message) throws Exception {
    //按openId列表群发消息，先获取公众号已关注用户的openid
    WxMpUserList wxMpUserList = wxMpService.getUserService().userList(null);
    WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
    FileInputStream inputStream = new FileInputStream(new File(message.getUrl()));
    String extension = FilenameUtils.getExtension(message.getUrl());
    switch (message.getReplyType()){
      case WxConsts.MassMsgType.TEXT:
        massMessage.setMsgType(WxConsts.MassMsgType.TEXT);
        massMessage.setContent(message.getContent());
        break;
      case WxConsts.MassMsgType.IMAGE:
        WxMediaUploadResult uploadMediaRes = wxMpService.getMaterialService()
            .mediaUpload(WxConsts.MassMsgType.IMAGE, extension, inputStream);
        massMessage.setMsgType(WxConsts.MassMsgType.IMAGE);
        massMessage.setMediaId(uploadMediaRes.getMediaId());
        break;
      case WxConsts.MassMsgType.MPNEWS:
        // 上传图文消息的封面图片
        WxMediaUploadResult upload = wxMpService.getMaterialService()
            .mediaUpload(WxConsts.MassMsgType.MPNEWS, extension, inputStream);
        // 上传图文消息的正文图片(返回的url拼在正文的<img>标签中)
        WxMediaImgUploadResult imagedMediaRes =
            wxMpService.getMaterialService().mediaImgUpload(new File(message.getUrl()));
        WxMpMassNews news = new WxMpMassNews();
        WxMpNewsArticle article1 = new WxMpNewsArticle();
        article1.setTitle(message.getName());
        article1.setContent(message.getContent());
        article1.setThumbMediaId(upload.getMediaId());
        article1.setContentSourceUrl(imagedMediaRes.getUrl());
        news.addArticle(article1);
        WxMpMassUploadResult wxMpMassUploadResult = wxMpService.getMassMessageService().massNewsUpload(news);
        massMessage.setMsgType(WxConsts.MassMsgType.MPNEWS);
        massMessage.setMediaId(wxMpMassUploadResult.getMediaId());
        break;
      case WxConsts.MassMsgType.VOICE:
        WxMediaUploadResult uploadMe = wxMpService.getMaterialService()
            .mediaUpload(WxConsts.MassMsgType.VOICE, extension, inputStream);
        massMessage.setMsgType(WxConsts.MassMsgType.VOICE);
        massMessage.setMediaId(uploadMe.getMediaId());
        break;
      default:
        WxMediaUploadResult uploadMediaR = wxMpService.getMaterialService()
            .mediaUpload(WxConsts.MassMsgType.MPVIDEO, extension, inputStream);
        // 把视频变成可被群发的媒体
        WxMpMassVideo video = new WxMpMassVideo();
        video.setTitle(message.getName());
        video.setDescription(message.getDescription());
        video.setMediaId(uploadMediaR.getMediaId());
        WxMpMassUploadResult uploadResult = wxMpService.getMassMessageService().massVideoUpload(video);
        massMessage.setMsgType(WxConsts.MassMsgType.MPVIDEO);
        massMessage.setMediaId(uploadResult.getMediaId());
        break;
    }

    try {
      this.add(message);
      massMessage.getToUsers().addAll(wxMpUserList.getOpenids());
      wxMpService.getMassMessageService().massOpenIdsMessageSend(massMessage);
      return true;
    } catch (WxErrorException ex) {
      ex.printStackTrace();
      return false;
    }
  }

}