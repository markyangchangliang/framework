package com.markyang.framework.auth.token;

import com.markyang.framework.util.TypeCastUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * 微信小程序认证
 * @author markyang
 */
@Component
@Slf4j
@AllArgsConstructor
public class WxMiniAppAuthenticationProvider implements AuthenticationProvider {

    /**
     * Performs authentication with the same contract as
     * {@link AuthenticationManager#authenticate(Authentication)}
     * .
     *
     * @param authentication the authentication request object.
     * @return a fully authenticated object including credentials. May return
     * <code>null</code> if the <code>AuthenticationProvider</code> is unable to support
     * authentication of the passed <code>Authentication</code> object. In such a case,
     * the next <code>AuthenticationProvider</code> that supports the presented
     * <code>Authentication</code> class will be tried.
     * @throws AuthenticationException if authentication fails.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WxMiniAppCodePrincipal principal = TypeCastUtils.cast(authentication.getPrincipal());
        //AuthenticatedPatient authenticatedPatient = this.patientDetailsService.loadPatientByWxMiniAppCode(principal.getAppId(), principal.getCode());
        // 判断用户是否启用
        /*if (!userDetails.isEnabled()) {
            log.warn("用户[{}]被停用了", userDetails.getUsername());
            throw new DisabledException("您的账号已被停用，无法登录");
        }*/
        WxMiniAppAuthenticationToken authenticationResult = new WxMiniAppAuthenticationToken(null, Collections.emptyList());
        authenticationResult.setDetails(authentication.getDetails());
        return authenticationResult;
    }

    /**
     * Returns <code>true</code> if this <Code>AuthenticationProvider</code> supports the
     * indicated <Code>Authentication</code> object.
     * <p>
     * Returning <code>true</code> does not guarantee an
     * <code>AuthenticationProvider</code> will be able to authenticate the presented
     * instance of the <code>Authentication</code> class. It simply indicates it can
     * support closer evaluation of it. An <code>AuthenticationProvider</code> can still
     * return <code>null</code> from the {@link #authenticate(Authentication)} method to
     * indicate another <code>AuthenticationProvider</code> should be tried.
     * </p>
     * <p>
     * Selection of an <code>AuthenticationProvider</code> capable of performing
     * authentication is conducted at runtime the <code>ProviderManager</code>.
     * </p>
     *
     * @param authentication 认证Token
     * @return <code>true</code> if the implementation can more closely evaluate the
     * <code>Authentication</code> class presented
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return WxMiniAppAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    public static class WxMiniAppCodePrincipal {

        /**
         * 小程序应用ID
         */
        private String appId;
        /**
         * 小程序Code
         */
        private String code;
    }
}
