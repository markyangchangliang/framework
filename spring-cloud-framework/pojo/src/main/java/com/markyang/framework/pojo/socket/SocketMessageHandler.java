package com.markyang.framework.pojo.socket;

import javax.websocket.Session;

/**
 * socket处理
 * @author yangchangliang
 */
public interface SocketMessageHandler {
    /**
     * 信息处理
     * @param message 消息
     * @param session session
     */
    void handle(SocketMessage message, Session session);

    /**
     * 是否支持
     * @param topic topic类型
     * @param session session
     * @return boolean
     */
    boolean isSupport(String topic, Session session);
}
