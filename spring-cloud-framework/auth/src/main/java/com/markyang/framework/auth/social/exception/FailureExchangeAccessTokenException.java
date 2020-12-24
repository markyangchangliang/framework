package com.markyang.framework.auth.social.exception;

import org.springframework.social.SocialException;

/**
 * 交换访问令牌失败异常
 * @author yangchangliang
 */
public class FailureExchangeAccessTokenException extends SocialException {

    public FailureExchangeAccessTokenException(String message) {
        super(message);
    }

    public FailureExchangeAccessTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
