package com.markyang.framework.app.system.wx.mp.controller;

import com.markyang.framework.app.system.wx.exception.WechatException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 微信响应处理
 * @author yangchangliang
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/portal/{appid}")
public class WxPortalController {

  private final WxMpMessageRouter messageRouter;
  private final WxMpService wxService;

  @GetMapping(produces = "text/plain;charset=utf-8")
  public String authGet(@PathVariable String appid,
                        @RequestParam(name = "signature", required = false) String signature,
                        @RequestParam(name = "timestamp", required = false) String timestamp,
                        @RequestParam(name = "nonce", required = false) String nonce,
                        @RequestParam(name = "echostr", required = false) String echostr) {

    log.info("接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature,
        timestamp, nonce, echostr);
    if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
      throw new WechatException("请求参数非法，请核实!");
    }

    if (!this.wxService.switchover(appid)) {
      throw new WechatException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
    }

    if (wxService.checkSignature(timestamp, nonce, signature)) {
      return echostr;
    }

    return "非法请求";
  }

  @PostMapping(produces = "application/xml; charset=UTF-8")
  public String post(@PathVariable String appid,
                     @RequestBody String requestBody,
                     @RequestParam("signature") String signature,
                     @RequestParam("timestamp") String timestamp,
                     @RequestParam("nonce") String nonce,
                     @RequestParam("openid") String openid,
                     @RequestParam(name = "encrypt_type", required = false) String encType,
                     @RequestParam(name = "msg_signature", required = false) String msgSignature) {
    log.info("接收微信请求：[openid=[{}], [signature=[{}], encType=[{}], msgSignature=[{}],"
            + " timestamp=[{}], nonce=[{}], requestBody=[{}] ",
        openid, signature, encType, msgSignature, timestamp, nonce, requestBody);

    if (!this.wxService.switchover(appid)) {
      throw new WechatException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
    }

    if (!wxService.checkSignature(timestamp, nonce, signature)) {
      throw new WechatException("非法请求，可能属于伪造的请求！");
    }

    String out = null;
    if (encType == null) {
      // 明文传输的消息
      WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
      WxMpXmlOutMessage outMessage = this.route(inMessage);
      if (outMessage == null) {
        return "";
      }

      out = outMessage.toXml();
    } else if ("aes".equalsIgnoreCase(encType)) {
      // aes加密的消息
      WxMpConfigStorage wxMpConfigStorage = wxService.getWxMpConfigStorage();
      WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxMpConfigStorage,
          timestamp, nonce, msgSignature);
      log.debug("消息解密后内容为：{} ", inMessage.toString());
      WxMpXmlOutMessage outMessage = this.route(inMessage);
      if (outMessage == null) {
        return "";
      }

      out = outMessage.toEncryptedXml(wxService.getWxMpConfigStorage());
    }

    log.debug("组装回复信息：{}", out);
    return out;
  }

  private WxMpXmlOutMessage route(WxMpXmlMessage message) {
    try {
      return this.messageRouter.route(message);
    } catch (Exception e) {
      log.error("路由消息时出现异常！", e);
    }

    return null;
  }
}
