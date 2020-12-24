package com.markyang.framework.app.system.wx.handler;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.markyang.framework.app.system.wx.enumeration.WechatStatusEnum;
import com.markyang.framework.app.system.wx.mp.service.MpUserService;
import com.markyang.framework.pojo.entity.system.wx.MpUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 取消关注处理器
 *
 * @author yangchangliang
 */
@Slf4j
@Component
@AllArgsConstructor
public class UnsubscribeHandler extends AbstractHandler {
    private final MpUserService mpUserService;
    private final MsgHandler msgHandler;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) throws WxErrorException {
        List<MpUser> mpUsers = this.mpUserService.list(Wrappers.<MpUser>lambdaQuery().eq(MpUser::getOpenId, wxMessage.getFromUser()));
        if (!mpUsers.isEmpty()) {
            MpUser mpUser = mpUsers.get(0);
            mpUser.setSubscribe(WechatStatusEnum.UnSubscribe.getValue());
            mpUser.setCancelSubscribeTime(LocalDateTime.now());
            mpUserService.update(mpUser);
            //消息记录
            msgHandler.getOutMessage(wxMessage, null, wxMpService.getUserService().userInfo(wxMessage.getFromUser()));
        }
        log.info("取消关注用户 OPENID: " + wxMessage.getFromUser());
        return null;
    }

}
