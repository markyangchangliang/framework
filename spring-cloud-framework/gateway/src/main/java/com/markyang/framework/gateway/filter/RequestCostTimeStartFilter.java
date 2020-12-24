package com.markyang.framework.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * 请求时间打点
 *
 * @author yangchangliang
 * @version 1
 */
@Component
public class RequestCostTimeStartFilter extends ZuulFilter {

    public static final String REQUEST_TIME_WATCH_NAME = "requestTimeWatch";

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return !StringUtils.endsWithAny(RequestContext.getCurrentContext().getRequest().getRequestURI().toLowerCase(), FrameworkConstants.IGNORED_REQUEST_SUFFIXES);
    }

    @Override
    public Object run() throws ZuulException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        RequestContext.getCurrentContext().set(REQUEST_TIME_WATCH_NAME, stopWatch);
        return null;
    }
}
