package com.markyang.framework.gateway.exception;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.DefaultRateLimiterErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 限流
 * @author yangchangliang
 */
@Component
@Slf4j
public class RateLimitErrorHandler extends DefaultRateLimiterErrorHandler {

    @Override
    public void handleError(String msg, Exception e) {
        log.warn("触发限流{}：{}", msg, e.getMessage());
    }
}
