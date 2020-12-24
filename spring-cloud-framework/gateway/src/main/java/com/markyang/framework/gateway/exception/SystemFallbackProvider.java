package com.markyang.framework.gateway.exception;

import org.springframework.stereotype.Component;

/**
 * 系统服务失败处理
 *
 * @author yangchangliang
 * @version 1
 * */
@Component
public class SystemFallbackProvider extends AbstractFallbackProvider {
    /**
     * The route this fallback will be used for.
     *
     * @return The route the fallback will be used for.
     */
    @Override
    public String getRoute() {
        return "system";
    }
}
