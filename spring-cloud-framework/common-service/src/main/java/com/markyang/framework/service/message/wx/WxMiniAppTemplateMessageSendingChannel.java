package com.markyang.framework.service.message.wx;

import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.constant.WxMaConstants;
import com.markyang.framework.service.message.AbstractEntityBasedTemplateMessageSendingChannel;
import com.markyang.framework.pojo.entity.message.MessageChannel;
import com.markyang.framework.pojo.entity.message.MessageTemplate;
import com.markyang.framework.pojo.message.MessageDetails;
import com.markyang.framework.pojo.message.MessageReceiverDetails;
import com.markyang.framework.pojo.message.MessageReceiverTypeEnum;
import com.markyang.framework.pojo.message.TemplateMessageParameter;
import com.markyang.framework.pojo.rule.support.FactBean;
import com.markyang.framework.service.message.exception.MessageException;
import com.markyang.framework.service.message.service.MessageChannelService;
import com.markyang.framework.service.message.service.MessageTemplateService;
import com.markyang.framework.service.wx.CompositeWxMaService;
import com.markyang.framework.util.OrgInfoUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 微信小程序模板消息发送通道
 *
 * @author yangchangliang
 * @version 1
 */
@ConditionalOnBean(CompositeWxMaService.class)
@Component
@FactBean
@Slf4j
@AllArgsConstructor
public class WxMiniAppTemplateMessageSendingChannel extends AbstractEntityBasedTemplateMessageSendingChannel {

    public static final String MESSAGE_CHANNEL_NAME = "wx_mini_app";
    private final MessageChannelService messageChannelService;
    private final MessageTemplateService messageTemplateService;
    private final CompositeWxMaService compositeWxMaService;

    /**
     * 获取消息通道实体对象
     *
     * @param orgId 机构ID
     * @return 消息通道
     */
    @Override
    protected MessageChannel getMessageChannel(String orgId) {
        return this.messageChannelService.getMessageChannel(orgId, MESSAGE_CHANNEL_NAME)
            .orElseThrow(() -> new MessageException("未找到机构[" + orgId + "]的通道[" + MESSAGE_CHANNEL_NAME + "]配置信息"));
    }

    /**
     * 获取消息模板对象
     *
     * @param orgId       机构ID
     * @param businessKey 业务标识
     * @return 模板对象
     */
    @Override
    protected MessageTemplate getMessageTemplate(String orgId, String businessKey) {
        return this.messageTemplateService.getMessageTemplate(orgId, MESSAGE_CHANNEL_NAME, businessKey)
            .orElseThrow(() -> new MessageException("未找到机构[" + orgId + "]关于业务[" + MESSAGE_CHANNEL_NAME + ", " + businessKey + "]的模板信息"));
    }

    /**
     * 解析消息接收者标识信息
     *
     * @param messageReceiverDetails 消息接受者详情
     * @return 标识信息
     */
    @Override
    protected String resolveMessageReceiver(MessageReceiverDetails messageReceiverDetails) {
        Object data = messageReceiverDetails.getData();
        if (messageReceiverDetails.getType() == MessageReceiverTypeEnum.WORKER) {
            //return ;
        }
        return null;
    }

    /**
     * 解析额外信息
     *
     * @param additionalInformation 额外信息
     * @return 消息接收者列表
     */
    @Override
    protected List<MessageReceiverDetails> resolveReceivers(Map<String, String> additionalInformation) {
        return Collections.emptyList();
    }

    /**
     * 发送消息
     * @param templateMessageId      模板消息ID
     * @param templateMessageParameters 模板参数
     * @param templateMessageContent 模板消息内容
     * @param messageDetails 消息详情
     * @param channelConfiguration   通道配置
     */
    @Override
    protected boolean sendMessage(String templateMessageId, List<TemplateMessageParameter> templateMessageParameters, String templateMessageContent, MessageDetails messageDetails, Map<String, String> channelConfiguration) {
        WxMaSubscribeMessage message = WxMaSubscribeMessage.builder()
            .templateId(templateMessageId)
            .miniprogramState(WxMaConstants.MiniprogramState.DEVELOPER)
            .page("index")
            .data(templateMessageParameters.parallelStream().map(templateMessageParameter -> new WxMaSubscribeMessage.Data(templateMessageParameter.getParameterName(), templateMessageParameter.getParameterValue())).collect(Collectors.toList()))
            .build();
        List<String> receivers = this.resolveMessageReceivers(messageDetails);
        try {
            WxMaMsgService msgService = this.compositeWxMaService.getMsgService(OrgInfoUtils.getOrgWxMiniAppId(messageDetails.getOrgId()).orElse(""));
            for (String receiver : receivers) {
                message.setToUser(receiver);
                msgService.sendSubscribeMsg(message);
            }
            return true;
        } catch (WxErrorException e) {
            log.error("微信小程序订阅消息发送失败：{}", e.getError().getErrorMsg());
            return false;
        }
    }

    /**
     * 判断是否是某一个channel
     *
     * @param channelName channel名称
     * @return bool
     */
    @Override
    public boolean is(String channelName) {
        return StringUtils.equals(channelName, MESSAGE_CHANNEL_NAME);
    }
}
