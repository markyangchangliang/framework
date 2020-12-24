package com.markyang.framework.service.message;

import com.google.common.collect.Lists;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.message.*;
import com.markyang.framework.util.ReflectionOperationUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 模板消息发送基类
 *
 * @author yangchangliang
 * @version 1
 */
public abstract class AbstractTemplateMessageSendingChannel implements MessageSendingChannel {


    /**
     * 消息发送逻辑
     *
     * @param messageDetails 模板消息详情
     */
    @Override
    public boolean send(MessageDetails messageDetails) {
        TemplateMessageConfiguration templateMessageConfiguration = this.getTemplateMessageConfiguration(messageDetails.getOrgId(), messageDetails.getBusinessKey());
        Object data = messageDetails.getData();
        // 转换模板参数
        List<TemplateMessageParameter> templateMessageParameters = templateMessageConfiguration.getTemplateMessageParameterNameMappings().parallelStream()
            .map(templateMessageParameterNameMapping -> TemplateMessageParameter.builder()
                .parameterName(templateMessageParameterNameMapping.getTemplateParameterName())
                .parameterIndex(templateMessageParameterNameMapping.getTemplateParameterIndex())
                .parameterValue(this.resolveParameterValue(templateMessageParameterNameMapping.getParameterName(), data))
                .build())
            .sorted(Comparator.comparingInt(TemplateMessageParameter::getParameterIndex))
            .collect(Collectors.toList());
        // 拿到参数后就可以发送信息了
        return this.sendMessage(templateMessageConfiguration.getTemplateMessageId(), templateMessageParameters, templateMessageConfiguration.getTemplateMessageContent(), messageDetails, templateMessageConfiguration.getChannelConfiguration());
    }

    /**
     * 解析参数值
     * @param parameterName 参数名称
     * @param data 数据对象
     * @return 参数值
     */
    private String resolveParameterValue(String parameterName, Object data) {
        if (data instanceof Map) {
            return ConvertUtils.convert(((Map<?, ?>) data).get(parameterName));
        }
        return ConvertUtils.convert(ReflectionOperationUtils.getFieldValue(parameterName, data).orElse(null));
    }

    /**
     * 解析消息接收者标识符
     * @param messageDetails 消息详情
     * @return 标识符列表
     */
    protected List<String> resolveMessageReceivers(MessageDetails messageDetails) {
        List<MessageReceiverDetails> messageReceiverDetails = this.resolveMessageReceiverDetails(messageDetails);
        List<String> receivers = Lists.newArrayList();
        for (MessageReceiverDetails messageReceiverDetail : messageReceiverDetails) {
            String receiver = this.resolveMessageReceiver(messageReceiverDetail);
            if (StringUtils.isNotBlank(receiver)) {
                receivers.add(receiver);
            }
        }
        return receivers;
    }

    /**
     * 解析消息接收者标识信息
     * @param messageReceiverDetails 消息接受者详情
     * @return 标识信息
     */
    protected abstract String resolveMessageReceiver(MessageReceiverDetails messageReceiverDetails);

    /**
     * 解析消息接受者信息
     * @param messageDetails 消息详情对象
     * @return 消息接受者详情列表
     */
    protected List<MessageReceiverDetails> resolveMessageReceiverDetails(MessageDetails messageDetails) {
        if (StringUtils.isNotBlank(messageDetails.getToPatients())) {
            // 患者
            return this.resolveReceivers(messageDetails.getToPatients(), MessageReceiverTypeEnum.PATIENT);
        } else if (StringUtils.isNotBlank(messageDetails.getToFamilyMembers())) {
            // 家庭成员
            return this.resolveReceivers(messageDetails.getToFamilyMembers(), MessageReceiverTypeEnum.FAMILY_MEMBER);
        } else if (StringUtils.isNotBlank(messageDetails.getToWorkers())) {
            // 职员
            return this.resolveReceivers(messageDetails.getToWorkers(), MessageReceiverTypeEnum.WORKER);
        } else {
            return this.resolveReceivers(messageDetails.getAdditionalInformation());
        }
    }

    /**
     * 解析消息接收者信息
     * @param receiverIds 接收者IDS字符串
     * @param receiverType 接收类型
     * @return 消息接收者信息列表
     */
    private List<MessageReceiverDetails> resolveReceivers(String receiverIds, MessageReceiverTypeEnum receiverType) {
        String[] tokens = StringUtils.split(receiverIds, FrameworkConstants.COMMA_SEPARATOR);
        if (ArrayUtils.isEmpty(tokens)) {
            return Collections.emptyList();
        }
        return Arrays.stream(tokens)
            .parallel()
            .map(token -> MessageReceiverDetails.of(receiverType, this.resolveReceiverData(token, receiverType)))
            .collect(Collectors.toList());
    }

    /**
     * 解析额外信息
     * @param additionalInformation 额外信息
     * @return 消息接收者列表
     */
    protected abstract List<MessageReceiverDetails> resolveReceivers(Map<String, String> additionalInformation);

    /**
     * 解析接收者数据
     * @param receiverId 接收者ID
     * @param receiverType 接收类型
     * @return 数据对象
     */
    private Object resolveReceiverData(String receiverId, MessageReceiverTypeEnum receiverType) {
        return null;
    }

    /**
     * 获取模板消息配置
     * @param orgId 机构ID
     * @param businessKey 业务key
     * @return 配置
     */
    protected abstract TemplateMessageConfiguration getTemplateMessageConfiguration(String orgId, String businessKey);

    /**
     * 发送消息
     * @param templateMessageId 模板消息ID
     * @param templateMessageParameters 模板参数
     * @param templateMessageContent 模板消息内容
     * @param messageDetails 消息详情
     * @param channelConfiguration 通道配置
     * @return bool
     */
    protected abstract boolean sendMessage(String templateMessageId, List<TemplateMessageParameter> templateMessageParameters, String templateMessageContent, MessageDetails messageDetails, Map<String, String> channelConfiguration);
}
