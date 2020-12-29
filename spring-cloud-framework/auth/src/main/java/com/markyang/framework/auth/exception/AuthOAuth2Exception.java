package com.markyang.framework.auth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.markyang.framework.auth.serializer.AuthOAuth2ExceptionSerializer;
import com.markyang.framework.pojo.web.ResultVo;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * OAuth自定义异常
 *
 * @author markyang
 * @version 1
 * @date 2020/3/25 12:49 上午 星期三
 */
@Getter
@JsonSerialize(using = AuthOAuth2ExceptionSerializer.class)
public class AuthOAuth2Exception extends OAuth2Exception {
    private ResultVo resultVo;

    public AuthOAuth2Exception(ResultVo resultVo) {
        super(resultVo.getMessage());
        this.resultVo = resultVo;
    }
}
