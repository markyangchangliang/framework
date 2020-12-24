package com.markyang.framework.config.serializer;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.markyang.framework.util.DateTimeUtils;
import org.springframework.boot.jackson.JsonComponent;

/**
 * LocalDateTime序列化器
 *
 * @author yangchangliang
 * @version 1
 */
@JsonComponent
public class FrameworkLocalDateTimeSerializer extends LocalDateTimeSerializer {
    public FrameworkLocalDateTimeSerializer() {
        super(DateTimeUtils.DATE_TIME_FORMATTER);
    }
}
