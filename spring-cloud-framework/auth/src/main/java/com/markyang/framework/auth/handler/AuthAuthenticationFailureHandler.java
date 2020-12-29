package com.markyang.framework.auth.handler;

import com.markyang.framework.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 *
 * @author markyang
 * @version 1
 * @date 2020/4/1 1:38 下午 星期三
 */
@Component
@Slf4j
public class AuthAuthenticationFailureHandler implements AuthenticationFailureHandler {
    /**
     * Called when an authentication attempt fails.
     *
     * @param request   the request during which the authentication attempt occurred.
     * @param response  the response.
     * @param exception the exception which was thrown to reject the authentication
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("认证失败：{}", exception.getMessage());
        ResponseUtils.responseJson(response, HttpStatus.UNAUTHORIZED.value(), "认证失败[" + exception.getMessage() + "]");
    }
}
