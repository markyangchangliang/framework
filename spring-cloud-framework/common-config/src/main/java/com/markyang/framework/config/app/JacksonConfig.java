package com.markyang.framework.config.app;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.markyang.framework.util.DateTimeUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Jackson序列化配置
 *
 * @author yangchangliang
 * @version 1
 */
@Configuration
public class JacksonConfig {

    @Bean
    Jackson2ObjectMapperBuilderCustomizer frameworkJacksonCustomizer() {
        return builder -> {
            // 配置反序列化
            builder.deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeUtils.DATE_FORMATTER));
            builder.deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeUtils.TIME_FORMATTER));
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeUtils.DATE_TIME_FORMATTER));

            // 配置序列化
            builder.serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeUtils.DATE_FORMATTER));
            builder.serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeUtils.TIME_FORMATTER));
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeUtils.DATE_TIME_FORMATTER));
        };
    }
}
