package com.markyang.framework.auth.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.markyang.framework.auth.exception.AuthOAuth2Exception;

import java.io.IOException;

/**
 * OAuth2异常序列化
 *
 * @author markyang
 * @version 1
 * @date 2020/3/25 1:16 上午 星期三
 */
public class AuthOAuth2ExceptionSerializer extends StdSerializer<AuthOAuth2Exception> {

    protected AuthOAuth2ExceptionSerializer() {
        super(AuthOAuth2Exception.class);
    }

    @Override
    public void serialize(AuthOAuth2Exception value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getResultVo());
    }
}
