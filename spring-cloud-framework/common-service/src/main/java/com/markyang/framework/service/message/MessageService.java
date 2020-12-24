package com.markyang.framework.service.message;

import com.markyang.framework.pojo.message.MessageDetails;

/**
 * 模板消息服务接口
 *
 * @author yangchangliang
 * @version 1
 */
public interface MessageService {

    /**
     * 发送模板消息
     * @param messageDetails 消息详情
     */
    void sendTemplateMessage(MessageDetails messageDetails);
}
