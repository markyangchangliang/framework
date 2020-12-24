package com.markyang.framework.auth.handler;

import com.markyang.framework.auth.exception.AuthOAuth2Exception;
import com.markyang.framework.pojo.web.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * 异常处理
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@Slf4j
public class AuthExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        // Try to extract a SpringSecurityException from the stacktrace
        log.info("OAuth2认证出现异常：{}[{}]", e.getClass().getName(), e.getMessage());
        int code;
        String message = e.getMessage();
        if (e instanceof InvalidRequestException) {
            message = "请求参数缺失";
        } else if (e instanceof UnsupportedGrantTypeException) {
            message = "不支持的Grant类型";
        } else if (e instanceof InvalidGrantException) {
            if (StringUtils.contains(e.getMessage(), "Bad credentials")) {
                message = "用户名或密码错误";
            } else {
                message = "认证出现错误[" + e.getMessage() + "]";
            }
        } else if (e instanceof NullPointerException) {
            message = "空指针异常";
        }
        if (e instanceof OAuth2Exception) {
            code = ((OAuth2Exception) e).getHttpErrorCode();
        } else {
            code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
        return new ResponseEntity<>(new AuthOAuth2Exception(ResultVo.error(message)), HttpStatus.valueOf(code));
    }

}
