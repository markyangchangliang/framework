package com.markyang.framework.app.base.converter.bind;

import com.google.common.collect.Sets;
import com.markyang.framework.util.DateTimeUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalConverter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

/**
 * 本地日期时间通用转换器
 *
 * @author yangchangliang
 */
@Component
public class LocalDateTimeGenericConverter implements GenericConverter {

    /**
     * Return the source and target types that this converter can convert between.
     * <p>Each entry is a convertible source-to-target type pair.
     * <p>For {@link ConditionalConverter conditional converters} this method may return
     * {@code null} to indicate all source-to-target pairs should be considered.
     */
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Sets.newHashSet(
            new ConvertiblePair(String.class, LocalDateTime.class),
            new ConvertiblePair(String.class, LocalDate.class),
            new ConvertiblePair(String.class, LocalTime.class)
        );
    }

    /**
     * Convert the source object to the targetType described by the {@code TypeDescriptor}.
     *
     * @param source     the source object to convert (may be {@code null})
     * @param sourceType the type descriptor of the field we are converting from
     * @param targetType the type descriptor of the field we are converting to
     * @return the converted object
     */
    @Override
    public Object convert(Object source, @NotNull TypeDescriptor sourceType, @NotNull TypeDescriptor targetType) {
        if (Objects.isNull(source) || StringUtils.isBlank((String) source)) {
            return null;
        }
        Class<?> target = targetType.getObjectType();
        if (ClassUtils.isAssignable(target, LocalDateTime.class)) {
            return DateTimeUtils.parseLocalDateTime((String) source);
        } else if (ClassUtils.isAssignable(target, LocalDate.class)) {
            return DateTimeUtils.parseLocalDate((String) source);
        } else if (ClassUtils.isAssignable(target, LocalTime.class)) {
            return DateTimeUtils.parseLocalTime((String) source);
        } else {
            return null;
        }
    }

}
