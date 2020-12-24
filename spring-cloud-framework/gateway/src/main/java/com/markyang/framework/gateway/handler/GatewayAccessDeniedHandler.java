package com.markyang.framework.gateway.handler;

import com.markyang.framework.pojo.web.ResultVo;
import com.markyang.framework.util.ResponseUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 网关无权限处理器
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@Slf4j
@AllArgsConstructor
public class GatewayAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Handles an access denied failure.
     *
     * @param request               that resulted in an <code>AccessDeniedException</code>
     * @param response              so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     * @throws IOException      in the event of an IOException
     * @throws ServletException in the event of a ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("请求[{}]被拒绝：403，信息：{}", request.getRequestURL().toString(), accessDeniedException.getMessage());
        // 返回信息
        ResponseUtils.responseJson(response, HttpStatus.FORBIDDEN.value(), ResultVo.error("无权限访问"));
    }
}
