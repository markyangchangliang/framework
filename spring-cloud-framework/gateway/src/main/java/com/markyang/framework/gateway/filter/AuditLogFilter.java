package com.markyang.framework.gateway.filter;

import com.markyang.framework.pojo.auth.AuthenticatedUser;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.util.AuthUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 审计日志记录过滤器
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@AllArgsConstructor
@Slf4j
public class AuditLogFilter extends OncePerRequestFilter {

    /**
     * AntPath匹配器
     */
    public static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    /**
     * 路由定位器
     */
    private final RouteLocator routeLocator;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (this.routeLocator.getRoutes().parallelStream().noneMatch(route -> ANT_PATH_MATCHER.match(route.getFullPath(), request.getRequestURI()))) {
            filterChain.doFilter(request, response);
            return;
        }
        String toLowerCase = request.getRequestURI().toLowerCase();
        if (StringUtils.endsWithAny(toLowerCase, FrameworkConstants.IGNORED_REQUEST_SUFFIXES)) {
            filterChain.doFilter(request, response);
            return;
        }
        AuthenticatedUser authenticatedUser = AuthUtils.getLoggedUser(false);
        String username;
        // 获取用户名
        if (Objects.nonNull(authenticatedUser)) {
            username = authenticatedUser.getUsername() + "(" + authenticatedUser.getName() + ")";
        } else {
            username = "anonymousUser";
        }
        log.info(AnsiOutput.toString(AnsiColor.BRIGHT_BLUE, "当前登录用户[{}]请求：{} : {}"), username, request.getMethod(), request.getRequestURI());
        filterChain.doFilter(request, response);
    }
}
