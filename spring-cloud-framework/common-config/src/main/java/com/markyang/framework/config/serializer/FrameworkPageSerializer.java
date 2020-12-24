package com.markyang.framework.config.serializer;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.markyang.framework.pojo.web.PageVo;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * 分页对象序列化
 *
 * @author yangchangliang
 * @version 1
 */
@JsonComponent
public class FrameworkPageSerializer extends JsonSerializer<IPage<?>> {

    @Override
    public void serialize(IPage<?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(
            PageVo.of(value)
        );
    }
}
