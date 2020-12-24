package com.markyang.framework.service.core.controller.support;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.markyang.framework.pojo.common.support.PageDefault;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.util.ConversionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

/**
 * 分页参数解析
 *
 * @author yangchangliang
 * @version 1
 */
@Component
public class PageArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * Whether the given {@linkplain MethodParameter method parameter} is
     * supported by this resolver.
     *
     * @param parameter the method parameter to check
     * @return {@code true} if this resolver supports the supplied parameter;
     * {@code false} otherwise
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return IPage.class.isAssignableFrom(parameter.getParameterType());
    }

    /**
     * Resolves a method parameter into an argument value from a given request.
     * A {@link ModelAndViewContainer} provides access to the model for the
     * request. A {@link WebDataBinderFactory} provides a way to create
     * a {@link WebDataBinder} instance when needed for data binding and
     * type conversion purposes.
     *
     * @param parameter     the method parameter to resolve. This parameter must
     *                      have previously been passed to {@link #supportsParameter} which must
     *                      have returned {@code true}.
     * @param mavContainer  the ModelAndViewContainer for the current request
     * @param webRequest    the current request
     * @param binderFactory a factory for creating {@link WebDataBinder} instances
     * @return the resolved argument value, or {@code null} if not resolvable
     * @throws Exception in case of errors with the preparation of argument values
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 获取页码
        String pageNumber = webRequest.getParameter("page");
        // 获取分页大小
        String pageSize = webRequest.getParameter("size");
        // 获取排序
        String sortString = webRequest.getParameter("sort");
        // 处理默认
        long iPageNumber, iPageSize;
        if (parameter.hasParameterAnnotation(PageDefault.class)) {
            PageDefault annotation = parameter.getParameterAnnotation(PageDefault.class);
            Assert.notNull(annotation, "@PageDefault注解不能为空");
            iPageNumber = StringUtils.isBlank(pageNumber) ? annotation.page() : Long.parseLong(pageNumber);
            iPageSize = StringUtils.isBlank(pageSize) ? annotation.size() : Long.parseLong(pageSize);
            if (StringUtils.isBlank(sortString)) {
                sortString = annotation.sort();
            }
        } else {
            iPageNumber = StringUtils.isBlank(pageNumber) ? 1 : Long.parseLong(pageNumber);
            iPageSize = StringUtils.isBlank(pageSize) ? 20 : Long.parseLong(pageSize);
        }
        Page<?> page = new Page<>(iPageNumber, iPageSize);
        if (StringUtils.isNotBlank(sortString)) {
            String[] sorts = StringUtils.split(sortString, "|");
            if (ArrayUtils.isEmpty(sorts)) {
                return page;
            }
            // 有排序需要处理
            List<OrderItem> orderItems = Lists.newArrayList();
            for (String sort : sorts) {
                String[] tokens = StringUtils.split(sort, FrameworkConstants.COMMA_SEPARATOR);
                if (ArrayUtils.isNotEmpty(tokens)) {
                    String name = ConversionUtils.camelToDash(tokens[0]);
                    if (tokens.length == 1) {
                        // 只有属性，默认升序
                        orderItems.add(OrderItem.asc(name));
                    } else if (tokens.length == 2) {
                        orderItems.add(StringUtils.equalsIgnoreCase("asc", tokens[1]) ? OrderItem.asc(name) : (StringUtils.equalsIgnoreCase("desc", tokens[1]) ? OrderItem.desc(name) : null));
                    }
                }
            }
            page.addOrder(orderItems);
        }
        return page;
    }
}
