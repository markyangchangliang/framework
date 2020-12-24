package com.markyang.framework.pojo.socket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yangchangliang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocketMessage {

    private int code;

    private String message;

    private Object data;

    public static SocketMessage ok(String message, Object data) {
        return new SocketMessage(0, message, data);
    }

    public static SocketMessage ok(String message) {
        return new SocketMessage(0, message, null);
    }

    public static SocketMessage err(String message, Object data) {
        return new SocketMessage(-1, message, data);
    }

    public static SocketMessage err(String message) {
        return new SocketMessage(-1, message, null);
    }
}
