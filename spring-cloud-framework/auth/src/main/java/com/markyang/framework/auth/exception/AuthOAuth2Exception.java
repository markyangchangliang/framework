package com.markyang.framework.auth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.markyang.framework.auth.serializer.AuthOAuth2ExceptionSerializer;
import com.markyang.framework.pojo.web.ResultVo;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * OAuth自定义异常
 *
 * @author yangchangliang
 * @version 1
 */
@Getter
@JsonSerialize(using = AuthOAuth2ExceptionSerializer.class)
public class AuthOAuth2Exception extends OAuth2Exception {
    private final ResultVo<Object> resultVo;

    public AuthOAuth2Exception(ResultVo<Object> resultVo) {
        super(resultVo.getMessage());
        this.resultVo = resultVo;
    }
}
