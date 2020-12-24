package com.markyang.framework.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 找不到电话异常
 * @author yangchangliang
 */
public class PhoneNotFoundException extends AuthenticationException {
    // ~ Constructors
    // ===================================================================================================

    /**
     * Constructs a <code>PhoneNotFoundException</code> with the specified message.
     *
     * @param msg the detail message.
     */
    public PhoneNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Constructs a {@code PhoneNotFoundException} with the specified message and root
     * cause.
     *
     * @param msg the detail message.
     * @param t root cause
     */
    public PhoneNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
