package com.markyang.framework.gateway.service;

import com.markyang.framework.pojo.auth.AuthenticatedUser;
import com.markyang.framework.pojo.auth.AuthorityUriKey;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 权限校验
 * @author yangchangliang
 */
@Service
@Slf4j
public class AccessDecisionService {

    /**
     * 路径匹配器
     */
    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    /**
     * 用户权限校验
     * @param authenticatedUser 认证用户
     * @param request 请求对象
     * @return bool
     */
    public boolean decide(AuthenticatedUser authenticatedUser, HttpServletRequest request) {
        String requestUri = request.getMethod() + " : " + request.getRequestURI();
        log.info(AnsiOutput.toString(AnsiColor.MAGENTA, "用户[{}]请求：{}"), authenticatedUser.getName(), requestUri);
        Map<AuthorityUriKey, Boolean> uriPermissionStatusMap = RedisUtils.get(FrameworkConstants.USER_AUTHORITY_URI_METHOD_CACHE_KEY + authenticatedUser.getUserId());
        Assert.notNull(uriPermissionStatusMap, "URI权限映射Map为空，请检查登录逻辑是否出错");
        AuthorityUriKey uriKey = AuthorityUriKey.of(request.getMethod(), request.getRequestURI());
        if (uriPermissionStatusMap.containsKey(uriKey)) {
            return uriPermissionStatusMap.get(uriKey);
        }
        List<String[]> antAuthorityGroups = RedisUtils.get(FrameworkConstants.USER_AUTHORITIES_CACHE_KEY + authenticatedUser.getUserId());
        Assert.notNull(antAuthorityGroups, "URI权限缓存为空，请检查登录逻辑是否出错");
        boolean hasPermission = antAuthorityGroups.parallelStream().anyMatch(tokens -> {
            if (Objects.isNull(tokens)) {
                return false;
            }
            if (tokens.length == 1) {
                // 无视请求方法
                return ANT_PATH_MATCHER.match(tokens[0], request.getRequestURI());
            }
            if (tokens.length == 2) {
                // 有请求方法限制
                return request.getMethod().equalsIgnoreCase(tokens[1]) && ANT_PATH_MATCHER.match(tokens[0], request.getRequestURI());
            }
            return false;
        });
        if (hasPermission) {
            log.info(AnsiOutput.toString(AnsiColor.BRIGHT_GREEN, "[{}]有权限访问：{}"), authenticatedUser.getName(), requestUri);
        } else {
            log.warn(AnsiOutput.toString(AnsiColor.RED, "[{}]无权限访问：{}"), authenticatedUser.getName(), requestUri);
            //return false;
        }
        return true;
    }
}
