package com.markyang.framework.auth.handler;

import com.markyang.framework.auth.properties.AuthProperties;
import com.markyang.framework.pojo.web.ResultVo;
import com.markyang.framework.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证入口
 * @author yangchangliang
 */
@Primary
@Component
@Slf4j
public class AuthUnauthorizedHandler extends LoginUrlAuthenticationEntryPoint {

    public AuthUnauthorizedHandler(AuthProperties authProperties) {
        super(authProperties.getLoginProcessingUrl());
    }

    /**
     * Commences an authentication scheme.
     * <p>
     * <code>ExceptionTranslationFilter</code> will populate the <code>HttpSession</code>
     * attribute named
     * <code>AbstractAuthenticationProcessingFilter.SPRING_SECURITY_SAVED_REQUEST_KEY</code>
     * with the requested target URL before calling this method.
     * <p>
     * Implementations should modify the headers on the <code>ServletResponse</code> as
     * necessary to commence the authentication process.
     *
     * @param request       that resulted in an <code>AuthenticationException</code>
     * @param response      so that the user agent can begin authentication
     * @param authException that caused the invocation
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("请求未认证: {}", authException.getMessage());
        // 根据响应返回信息
        if (request.getHeader(HttpHeaders.ACCEPT).toLowerCase().contains("text/")) {
            // 执行默认的跳转
            super.commence(request, response, authException);
            return;
        }
        // 处理JSON请求
        // 设置响应内容格式
        ResponseUtils.responseJson(response, HttpStatus.UNAUTHORIZED.value(), ResultVo.error("未登录认证[" + authException.getMessage() + "]"));
    }
}
