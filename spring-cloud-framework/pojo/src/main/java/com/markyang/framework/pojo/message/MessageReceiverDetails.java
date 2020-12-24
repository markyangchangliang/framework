package com.markyang.framework.pojo.message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 消息接收方详情
 *
 * @author yangchangliang
 * @version 1
 */
@Data
@AllArgsConstructor(staticName = "of")
public class MessageReceiverDetails {

    /**
     * 消息接收类型
     */
    private MessageReceiverTypeEnum type;
    /**
     * 数据对象
     */
    private Object data;
}
