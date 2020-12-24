package com.markyang.framework.service.wx;

import cn.binarywang.wx.miniapp.api.*;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.markyang.framework.service.exception.CommonServiceException;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;

import java.util.Map;
import java.util.Objects;

/**
 * 组合微信小程序服务类
 *
 * @author yangchangliang
 * @version 1
 */
@AllArgsConstructor
public class CompositeWxMaService {

    /**
     * 小程序Service服务集合
     */
    private final Map<String, WxMaService> wxMaServiceMap;

    /**
     * 获取微信小程序服务对象
     * @param appId 小程序AppId
     * @return 服务对象
     */
    private WxMaService getWxMaService(String appId) {
        WxMaService wxMaService = this.wxMaServiceMap.get(appId);
        if (Objects.isNull(wxMaService)) {
            throw new CommonServiceException("小程序服务类[" + appId + "]不存在");
        }
        return wxMaService;
    }

    /**
     * 获取登录后的session信息.
     *
     * @param appId 应用appId
     * @param jsCode 登录时获取的 code
     */
    public WxMaJscode2SessionResult jsCode2SessionInfo(String appId, String jsCode) throws WxErrorException {
        return this.getWxMaService(appId).jsCode2SessionInfo(jsCode);
    }

    /**
     * 返回消息（客服消息和模版消息）发送接口方法实现类，以方便调用其各个接口.
     *
     * @param appId 应用appId
     * @return WxMaMsgService
     */
    public WxMaMsgService getMsgService(String appId) {
        return this.getWxMaService(appId).getMsgService();
    }

    /**
     * 返回订阅消息配置相关接口方法的实现类对象, 以方便调用其各个接口.
     *
     * @param appId 小程序ID
     * @return WxMaSubscribeService
     */
    public WxMaSubscribeService getSubscribeService(String appId) {
        return this.getWxMaService(appId).getSubscribeService();
    }

    /**
     * 返回用户相关接口方法的实现类对象，以方便调用其各个接口.
     *
     * @param appId 小程序ID
     * @return WxMaUserService
     */
    public WxMaUserService getUserService(String appId) {
        return this.getWxMaService(appId).getUserService();
    }

    /**
     * 返回二维码相关接口方法的实现类对象，以方便调用其各个接口.
     *
     * @param appId 应用ID
     * @return WxMaQrcodeService
     */
    public WxMaQrcodeService getQrcodeService(String appId) {
        return this.getWxMaService(appId).getQrcodeService();
    }
}
