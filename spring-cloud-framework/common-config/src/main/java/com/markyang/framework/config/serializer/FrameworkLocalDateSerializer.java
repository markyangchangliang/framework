package com.markyang.framework.config.serializer;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.markyang.framework.util.DateTimeUtils;
import org.springframework.boot.jackson.JsonComponent;

/**
 * LocalDateTime序列化器
 *
 * @author yangchangliang
 * @version 1
 */
@JsonComponent
public class FrameworkLocalDateSerializer extends LocalDateSerializer {
    public FrameworkLocalDateSerializer() {
        super(DateTimeUtils.DATE_FORMATTER);
    }
}
