package com.markyang.framework.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 用户找不到异常
 *
 * @author markyang
 * @version 1
 * @date 2020/3/30 11:08 下午 星期一
 */
public class UserNotFoundException extends AuthenticationException {
    /**
     * Constructs an {@code AuthenticationException} with the specified message and root
     * cause.
     *
     * @param msg the detail message
     * @param t   the root cause
     */
    public UserNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    /**
     * Constructs an {@code AuthenticationException} with the specified message and no
     * root cause.
     *
     * @param msg the detail message
     */
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
