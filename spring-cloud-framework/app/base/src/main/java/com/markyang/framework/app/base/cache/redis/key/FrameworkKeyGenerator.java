package com.markyang.framework.app.base.cache.redis.key;

import com.markyang.framework.service.core.form.SearchBaseForm;
import com.markyang.framework.service.core.search.SearchCondition;
import com.markyang.framework.util.DateTimeUtils;
import com.markyang.framework.util.ReflectionOperationUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 一般查询Key生成器
 *
 * @author yangchangliang
 * @version 1
 */
@Component
public class FrameworkKeyGenerator implements KeyGenerator {
    /**
     * Generate a key for the given method and its parameters.
     *
     * @param target the target instance
     * @param method the method being called
     * @param params the method parameters (with any var-args expanded)
     * @return a generated key
     */
    @NonNull
    @Override
    public Object generate(@NonNull Object target, @NonNull Method method, @NonNull Object... params) {
        return target.getClass().getName() +
            ":" +
            method.getName() +
            ":" +
            Arrays.stream(params).parallel().filter(Objects::nonNull).map(param -> param.getClass().getSimpleName()).collect(Collectors.joining(".")) +
            ":" +
            Arrays.stream(params).parallel().filter(Objects::nonNull).map(FrameworkKeyGenerator::convertToString).filter(StringUtils::isNotBlank).collect(Collectors.joining("."));
    }

    /**
     * 将一个对象转为字符串形式
     * @param object 对象
     * @return 字符串
     */
    public static String convertToString(Object object) {
        if (object instanceof Temporal) {
            return DateTimeUtils.toStringFormat((Temporal) object);
        } else if (object instanceof Pageable) {
            // 分页对象
            Pageable pageable = (Pageable) object;
            return "page=" + pageable.getPageNumber() + ",size=" + pageable.getPageSize() + ",sort=" + pageable.getSort().stream().parallel().map(order -> order.getProperty() + "_" + order.getDirection()).collect(Collectors.joining("-"));
        } else if (object instanceof SearchBaseForm) {
            // 表单对象
            List<Field> searchFields = ReflectionOperationUtils.getAnnotatedFields(object.getClass(), SearchCondition.class);
            return searchFields.parallelStream()
                .map(field -> {
                    Optional<Object> valueOptional = ReflectionOperationUtils.getFieldValue(field, object);
                    return valueOptional.map(o -> field.getName() + "=" + o).orElse("");
                })
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining(","));
        } else {
            // 其他类型
            return ConvertUtils.convert(object);
        }
    }
}
