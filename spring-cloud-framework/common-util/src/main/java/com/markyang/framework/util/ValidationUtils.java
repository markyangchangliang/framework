package com.markyang.framework.util;

import com.baomidou.mybatisplus.annotation.TableId;
import com.markyang.framework.util.exception.UtilException;
import com.markyang.framework.pojo.common.support.FrameworkEnum;
import com.markyang.framework.pojo.entity.BaseEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ConcurrentReferenceHashMap;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 验证工具类
 * @author yangchangliang
 */
public final class ValidationUtils {

    /**
     * 缓存表单类主键字段
     */
    private static final Map<Class<?>, Field> CLASS_ID_FIELD_CACHE = new ConcurrentReferenceHashMap<>(128);

    /**
     * 判断对象是否为零（无意义）
     * @param object 对象
     * @return bool
     */
    public static boolean isNil(Object object) {
        // 如果是bool
        if (object instanceof Boolean) {
            return BooleanUtils.isFalse((Boolean) object);
        }
        // 如果是字符序列
        if (object instanceof CharSequence) {
            return StringUtils.isBlank((CharSequence) object);
        }
        // 如果是集合
        if (object instanceof Collection) {
            return CollectionUtils.isEmpty((Collection<?>) object);
        }
        // 如果是对象
        return Objects.isNull(object);
    }

    /**
     * 判断两个值是否相等
     * @param object 对象
     * @param value 值
     * @return bool
     */
    public static boolean equals(Object object, String value) {
        if (object instanceof FrameworkEnum) {
            return Objects.equals(((FrameworkEnum) object).getValue(), value);
        } else if (object instanceof Number) {
            Double a = ((Number) object).doubleValue();
            try {
                Double b = Double.parseDouble(value);
                return Objects.equals(a, b);
            } catch (NumberFormatException e) {
                return false;
            }
        } else if (object instanceof Boolean) {
            return StringUtils.equalsIgnoreCase(((Boolean) object).toString(), value);
        }
        return Objects.equals(object, value);
    }

    /**
     * 判断是否是合法的手机号
     * @param phone 手机号码
     * @return bool
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null) {
            return true;
        }
        return phone.matches("^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|19[0-9]|14[57])[0-9]{8}$");
    }

    /**
     * 验证一个数据是否存在
     * @param supplier 实体提供器
     * @param tip 提示信息
     * @param <E> 实体类泛型
     * @param <ID> 实体类主键泛型
     * @return 实体对象
     */
    public static <E extends BaseEntity, ID> E exists(Supplier<Optional<E>> supplier, String tip) {
        Optional<E> optional = supplier.get();
        if (!optional.isPresent()) {
            throw new UtilException(tip);
        }
        return optional.get();
    }

    /**
     * 获取一个类中的主键字段
     * @param clazz 类
     * @return 主键字段
     */
    public static Optional<Field> getIdField(Class<?> clazz) {
        if (CLASS_ID_FIELD_CACHE.containsKey(clazz)) {
            return Optional.of(CLASS_ID_FIELD_CACHE.get(clazz));
        } else {
            Optional<Field> idFieldOptional = ReflectionOperationUtils.getAnnotatedField(clazz, TableId.class);
            idFieldOptional.ifPresent(field -> CLASS_ID_FIELD_CACHE.put(clazz, field));
            return idFieldOptional;
        }
    }

    /**
     * 获取ID值
     * @param target 目标对象
     * @return optional结果
     */
    public static Optional<Object> getIdValue(Object target) {
        Optional<Field> idFieldOptional = getIdField(target.getClass());
        if (!idFieldOptional.isPresent()) {
            return Optional.empty();
        }
        return ReflectionOperationUtils.getFieldValue(idFieldOptional.get(), target);
    }

}
