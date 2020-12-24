package com.markyang.framework.service.message;

import com.markyang.framework.service.message.exception.MessageException;
import com.markyang.framework.pojo.message.MessageDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 默认的消息服务实现类
 *
 * @author yangchangliang
 * @version 1
 */
@ConditionalOnProperty(prefix = "framework.message", name = "mapping-store-type")
@Component
@Data
@AllArgsConstructor
public class DefaultMessageServiceImpl implements MessageService {
    private final MessageSendingChannelResolver messageSendingChannelResolver;
    /**
     * 发送模板消息
     *
     * @param messageDetails 消息详情
     */
    @Override
    public void sendTemplateMessage(MessageDetails messageDetails) {
        // 判空检查
        int mark = 0;
        if (StringUtils.isNotBlank(messageDetails.getToPatients())) {
            mark += 1;
        }
        if (StringUtils.isNotBlank(messageDetails.getToFamilyMembers())) {
            mark += 1;
        }
        if (StringUtils.isNotBlank(messageDetails.getToWorkers())) {
            mark += 1;
        }
        if (mark > 1) {
            throw new MessageException("一次消息发送只能指定一种（患者、家庭成员或职员）接收者，不要太贪哦：" + messageDetails);
        }
        this.messageSendingChannelResolver.resolve(messageDetails.getOrgId(), messageDetails.getBusinessKey())
            .parallelStream()
            .forEach(messageSendingChannel -> messageSendingChannel.send(messageDetails));
    }
}
