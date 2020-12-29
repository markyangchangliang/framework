package com.markyang.framework.util;

import com.markyang.framework.pojo.auth.AuthenticatedUser;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.util.exception.AuthUtilException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * 授权认证工具类
 *
 * @author yangchangliang
 * @version 1
 */
public final class AuthUtils {

    /**
     * 获取登录用户
     * @param throwExceptionIfNonExist 不存在用户认证信息时是否抛出异常
     * @return 登录用户对象
     */
    public static AuthenticatedUser getLoggedUser(boolean throwExceptionIfNonExist) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication) && authentication.isAuthenticated() && authentication.getPrincipal() instanceof AuthenticatedUser) {
            return (AuthenticatedUser) authentication.getPrincipal();
        }
        if (throwExceptionIfNonExist) {
            throw new AuthUtilException("当前请求并无已认证的用户，考虑是否该请求没有做认证拦截？");
        }
        return null;
    }


    /**
     * 获取登录用户
     * @return 登录用户对象
     */
    public static AuthenticatedUser getLoggedUser() {
        return getLoggedUser(true);
    }

    /**
     * 判断当前环境是否用户登录
     * @return bool
     */
    public static boolean isUserLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.isAuthenticated() && authentication.getPrincipal() instanceof AuthenticatedUser;
    }

    /**
     * 判断当前环境是否是认证过的
     * @return bool
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.isAuthenticated();
    }

    /**
     * 获取登录用户的用户ID
     * @return 用户ID
     */
    public static String getLoggedUserId() {
        return getLoggedUser().getUserId();
    }

    /**
     * 获取登录用户的机构ID
     * @return 机构ID
     */
    public static String getLoggedUserOrgId() {
        return getLoggedUser().getOrgId();
    }

    /**
     * 获取登录用户的部门ID
     * @return 部门ID
     */
    public static String getLoggedUserDeptId() {
        return getLoggedUser().getDeptId();
    }

    /**
     * 获取登录用户的职员ID
     * @return 职员ID
     */
    public static String getLoggedUserWorkerId() {
        return getLoggedUser().getWorkerId();
    }

    /**
     * 获取登录用户的姓名（职员姓名）
     * @return 姓名（职员姓名）
     */
    public static String getLoggedUserName() {
        return getLoggedUser().getName();
    }

    /**
     * 判断实体记录是否是超级记录（最顶层记录）
     * @param id 主键ID
     * @return bool
     */
    public static boolean isSuper(String id) {
        return StringUtils.equals(FrameworkConstants.SUPER_ID, id);
    }
}
