package com.markyang.framework.pojo.message;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.Map;

/**
 * 发送的消息详情
 *
 * @author yangchangliang
 * @version 1
 */
@Data
@Builder
public class MessageDetails {

    /**
     * 机构ID
     */
    private String orgId;

    /**
     * 业务Key
     */
    private String businessKey;

    /**
     * 消息数据
     */
    private Object data;

    /**
     * 消息接收方
     */
    private String toPatients;

    /**
     * 消息接收方
     */
    private String toFamilyMembers;

    /**
     * 消息接收方（职员）
     */
    private String toWorkers;

    /**
     * 额外信息
     */
    @Singular("additionalInformation")
    private Map<String, String> additionalInformation;
}
