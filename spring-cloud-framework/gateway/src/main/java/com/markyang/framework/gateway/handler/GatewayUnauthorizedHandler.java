package com.markyang.framework.gateway.handler;

import com.markyang.framework.pojo.web.ResultVo;
import com.markyang.framework.util.ResponseUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 网关未登录处理器
 *
 * @author yangchangliang
 * @version 1
 */
@Primary
@Component
@Slf4j
@AllArgsConstructor
public class GatewayUnauthorizedHandler implements AuthenticationEntryPoint {

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
        String message = "未登录认证";
        if (StringUtils.isNotBlank(request.getHeader(HttpHeaders.AUTHORIZATION))) {
            // 有认证信息
            message = "Token验证失败[" + authException.getMessage() + "]";
        }
        log.error("请求[{}]未认证: {} {}", request.getRequestURL().toString(), authException.getClass().getName(), authException.getMessage());
        // 响应
        ResponseUtils.responseJson(response, HttpStatus.UNAUTHORIZED.value(), ResultVo.error(message));
    }
}
