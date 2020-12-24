package com.markyang.framework.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求时间打印
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@Slf4j
public class RequestCostTimeEndFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return !StringUtils.endsWithAny(RequestContext.getCurrentContext().getRequest().getRequestURI().toLowerCase(), FrameworkConstants.IGNORED_REQUEST_SUFFIXES);
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        Object o = context.get(RequestCostTimeStartFilter.REQUEST_TIME_WATCH_NAME);
        if (o instanceof StopWatch) {
            StopWatch stopWatch = (StopWatch) o;
            stopWatch.stop();
            HttpServletRequest request = context.getRequest();
            log.info(AnsiOutput.toString(AnsiColor.YELLOW, "请求[", AnsiColor.BRIGHT_BLUE, request.getMethod(), " : ", request.getRequestURI(), AnsiColor.YELLOW, "]耗时：", AnsiColor.BRIGHT_GREEN, stopWatch.getTotalTimeMillis(), " ms"));
        }
        return null;
    }
}
