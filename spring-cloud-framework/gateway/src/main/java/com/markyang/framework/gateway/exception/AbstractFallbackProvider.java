package com.markyang.framework.gateway.exception;

import com.netflix.client.ClientException;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import com.markyang.framework.pojo.web.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

/**
 * fallback处理基类
 *
 * @author yangchangliang
 * @version 1
 */
@Slf4j
public abstract class AbstractFallbackProvider implements FallbackProvider {

    /**
     * Provides a fallback response based on the cause of the failed execution.
     *
     * @param route The route the fallback is for
     * @param cause cause of the main method failure, may be <code>null</code>
     * @return the fallback response
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        log.error("访问服务[{}]出现异常：{}", route, cause.toString());
        if (cause instanceof HystrixTimeoutException) {
            return FrameworkClientHttpResponse.of(HttpStatus.GATEWAY_TIMEOUT, ResultVo.error("服务[" + route + "]网关超时"));
        }
        if (cause instanceof ClientException) {
            return FrameworkClientHttpResponse.of(HttpStatus.GATEWAY_TIMEOUT, ResultVo.error("服务[" + route + "]暂不可用"));
        }
        return FrameworkClientHttpResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, ResultVo.error("服务[" + route + "]内部异常：" + cause.getMessage()));
    }
}
