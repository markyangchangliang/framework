package com.markyang.framework.auth.filter;

import com.markyang.framework.auth.token.WxMiniAppAuthenticationProvider;
import com.markyang.framework.auth.token.WxMiniAppAuthenticationToken;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信小程序认证过滤器
 * @author markyang
 */
public class WxMiniAppAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    // ~ Static fields/initializers
    // =====================================================================================

    public static final String SPRING_SECURITY_FORM_APP_ID_KEY = "app_id";
    public static final String SPRING_SECURITY_FORM_CODE_KEY = "code";

    // ~ Constructors
    // ===================================================================================================

    public WxMiniAppAuthenticationFilter(String processingUrl) {
        super(new AntPathRequestMatcher(processingUrl, "POST"));
    }

    // ~ Methods
    // ========================================================================================================

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        boolean postOnly = true;
        if (postOnly && !"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException(
                "Authentication method not supported: " + request.getMethod());
        }

        String appId = this.obtainAppId(request);
        String code = this.obtainCode(request);

        if (appId == null) {
            appId = "";
        }
        if (code == null) {
            code = "";
        }

        appId = appId.trim();
        code = code.trim();

        WxMiniAppAuthenticationToken authRequest = new WxMiniAppAuthenticationToken(WxMiniAppAuthenticationProvider.WxMiniAppCodePrincipal.of(appId, code));

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * Enables subclasses to override the composition of the username, such as by
     * including additional values and a separator.
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the username that will be presented in the <code>Authentication</code>
     * request token to the <code>AuthenticationManager</code>
     */
    @Nullable
    protected String obtainAppId(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_APP_ID_KEY);
    }

    @Nullable
    protected String obtainCode(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_CODE_KEY);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     *
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     * set
     */
    protected void setDetails(HttpServletRequest request,
                              WxMiniAppAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
