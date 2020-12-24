package com.markyang.framework.config.auth;

import com.markyang.framework.pojo.auth.AuthenticatedPatient;
import com.markyang.framework.pojo.auth.AuthenticatedUser;
import com.markyang.framework.pojo.token.AuthenticatedPatientAuthenticationToken;
import com.markyang.framework.pojo.token.AuthenticatedUserAuthenticationToken;
import com.markyang.framework.util.TypeCastUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 认证用户转换器
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@AllArgsConstructor
public class AuthenticatedAuthenticationConverter extends DefaultUserAuthenticationConverter implements InitializingBean {

    private final UserDetailsService userDetailsService;

    /**
     * Extract information about the user to be used in an access token (i.e. for resource servers).
     *
     * @param userAuthentication an authentication representing a user
     * @return a map of key values representing the unique information about the user
     */
    @Override
    public Map<String, ?> convertUserAuthentication(Authentication userAuthentication) {
        Object principal = userAuthentication.getPrincipal();
        if (principal instanceof AuthenticatedUser) {
            Map<String, Object> response = new LinkedHashMap<String, Object>();
            // 存储用户相关的信息
            AuthenticatedUser authenticatedUser = (AuthenticatedUser) principal;
            response.put("userId", authenticatedUser.getUserId());
            response.put("orgId", authenticatedUser.getOrgId());
            response.put("deptId", authenticatedUser.getDeptId());
            response.put("workerId", authenticatedUser.getWorkerId());
            response.put("name", authenticatedUser.getName());
            response.put("mobilePhone", authenticatedUser.getMobilePhone());
            response.put("username", authenticatedUser.getUsername());
            return response;
        }
        if (principal instanceof AuthenticatedPatient) {
            Map<String, Object> response = new LinkedHashMap<String, Object>();
            // 存储用户相关的信息
            AuthenticatedPatient authenticatedPatient = (AuthenticatedPatient) principal;
            response.put("userId", authenticatedPatient.getUserId());
            response.put("wxOpenId", authenticatedPatient.getWxOpenId());
            response.put("wxSessionKey", authenticatedPatient.getWxSessionKey());
            response.put("wxUnionId", authenticatedPatient.getWxUnionId());
            response.put("aliOpenId", authenticatedPatient.getAliOpenId());
            response.put("aliUnionId", authenticatedPatient.getAliUnionId());
            return response;
        }
        return super.convertUserAuthentication(userAuthentication);
    }

    /**
     * Inverse of {@link #convertUserAuthentication(Authentication)}. Extracts an Authentication from a map.
     *
     * @param map a map of user information
     * @return an Authentication representing the user or null if there is none
     */
    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(USERNAME)) {
            return super.extractAuthentication(map);
        }
        if (map.containsKey("userId")) {
            // 自定义的转换
            AuthenticatedUser authenticatedUser = AuthenticatedUser.builder()
                .userId(map.get("userId").toString())
                .orgId(map.get("orgId").toString())
                .deptId(map.get("deptId").toString())
                .workerId(map.get("workerId").toString())
                .name(TypeCastUtils.cast(map.get("name")))
                .mobilePhone(TypeCastUtils.cast(map.get("mobilePhone")))
                .username(TypeCastUtils.cast(map.get("username")))
                .build();
            return new AuthenticatedUserAuthenticationToken(authenticatedUser, Collections.emptyList());
        }
        // 用户信息转换
        AuthenticatedPatient authenticatedPatient = AuthenticatedPatient.builder()
            .userId(TypeCastUtils.cast(map.get("userId")))
            .wxOpenId(TypeCastUtils.cast(map.get("wxOpenId")))
            .wxSessionKey(TypeCastUtils.cast(map.get("wxSessionKey")))
            .wxUnionId(TypeCastUtils.cast(map.get("wxUnionId")))
            .aliOpenId(TypeCastUtils.cast(map.get("aliOpenId")))
            .aliUnionId(TypeCastUtils.cast(map.get("aliUnionId")))
            .build();
        return new AuthenticatedPatientAuthenticationToken(authenticatedPatient, Collections.emptyList());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setUserDetailsService(this.userDetailsService);
    }
}
