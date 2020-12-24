package com.markyang.framework.service.message.sms;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.dto.system.UserWorkerInfoDto;
import com.markyang.framework.pojo.entity.message.MessageChannel;
import com.markyang.framework.pojo.entity.message.MessageTemplate;
import com.markyang.framework.pojo.message.MessageDetails;
import com.markyang.framework.pojo.message.MessageReceiverDetails;
import com.markyang.framework.pojo.message.MessageReceiverTypeEnum;
import com.markyang.framework.pojo.message.TemplateMessageParameter;
import com.markyang.framework.pojo.rule.support.FactBean;
import com.markyang.framework.service.message.AbstractEntityBasedTemplateMessageSendingChannel;
import com.markyang.framework.service.message.exception.MessageException;
import com.markyang.framework.service.message.service.MessageChannelService;
import com.markyang.framework.service.message.service.MessageTemplateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 腾讯云短信发送通道实现
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@FactBean
@Slf4j
@AllArgsConstructor
public class TencentSmsTemplateMessageSendingChannel extends AbstractEntityBasedTemplateMessageSendingChannel {

    public static final String MESSAGE_CHANNEL_NAME = "tencent_sms";
    private final MessageChannelService messageChannelService;
    private final MessageTemplateService messageTemplateService;
    private final SmsClient smsClient;

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
        switch (messageReceiverDetails.getType()) {
            case WORKER:
                return ((UserWorkerInfoDto) data).getMobilePhone();
            case ADDITIONAL:
                return (String) data;
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
        String phones = additionalInformation.get("phones");
        String[] tokens = StringUtils.split(phones, FrameworkConstants.COMMA_SEPARATOR);
        if (ArrayUtils.isEmpty(tokens)) {
            return Collections.emptyList();
        }
        return Arrays.stream(tokens).parallel().map(token -> MessageReceiverDetails.of(MessageReceiverTypeEnum.ADDITIONAL, token)).collect(Collectors.toList());
    }

    /**
     * 发送消息
     *
     * @param templateMessageId         模板消息ID
     * @param templateMessageParameters 模板参数
     * @param templateMessageContent    模板消息内容
     * @param messageDetails            消息详情
     * @param channelConfiguration      通道配置
     * @return bool
     */
    @Override
    protected boolean sendMessage(String templateMessageId, List<TemplateMessageParameter> templateMessageParameters, String templateMessageContent, MessageDetails messageDetails, Map<String, String> channelConfiguration) {
        // 实例化发送请求
        SendSmsRequest request = new SendSmsRequest();
        request.setSmsSdkAppid(channelConfiguration.get("appId"));
        request.setSign(channelConfiguration.get("sign"));
        request.setTemplateID(templateMessageId);
        String[] params = new String[templateMessageParameters.size()];
        for (int i = 0; i < templateMessageParameters.size(); i++) {
            params[i] = templateMessageParameters.get(i).getParameterValue();
        }
        request.setTemplateParamSet(params);
        String[] phones = this.resolveMessageReceivers(messageDetails)
            .parallelStream()
            .map(phone -> StringUtils.startsWith(phone, "+") ? phone : ("+86" + phone))
            .toArray(String[]::new);
        request.setPhoneNumberSet(phones);
        try {
            // 发送短信
            SendSmsResponse response = this.smsClient.SendSms(request);
            SendStatus[] sendStatusSet = response.getSendStatusSet();
            if (ArrayUtils.isNotEmpty(sendStatusSet)) {
                SendStatus sendStatus = sendStatusSet[0];
                log.info("短信发送完成，接收者：{}，状态：{}，计费条数：{}", sendStatus.getPhoneNumber(), sendStatus.getMessage(), sendStatus.getFee());
                return sendStatus.getFee() > 0;
            }
            return false;
        } catch (TencentCloudSDKException e) {
            log.error("短信发送失败：{}", e.getLocalizedMessage());
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
