package com.markyang.framework.util;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.util.exception.UtilException;
import com.markyang.framework.pojo.entity.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

/**
 * 实体工具类
 *
 * @author yangchangliang
 * @version 1
 */
public final class EntityUtils {

    /**
     * 表名缓存
     */
    private static final Map<Class<? extends BaseEntity>, String> ENTITY_RAW_TABLE_NAME_CACHE = new ConcurrentReferenceHashMap<>(128);
    /**
     * 字段名缓存
     */
    private static final Map<Field, String> ENTITY_RAW_FIELD_NAME_CACHE = new ConcurrentReferenceHashMap<>(256);

    /**
     * 解析实体类数据库表名
     * @param entityClass 实体类
     * @return 数据库表名
     */
    public static String resolveRawTableName(Class<? extends BaseEntity> entityClass) {
        // 有缓存就走缓存
        if (ENTITY_RAW_TABLE_NAME_CACHE.containsKey(entityClass)) {
            return ENTITY_RAW_TABLE_NAME_CACHE.get(entityClass);
        }
        TableName table = entityClass.getAnnotation(TableName.class);
        if (Objects.isNull(table)) {
            throw new UtilException("实体类[" + entityClass.getName() + "]不存在@Table注解");
        }
        String tableName = StringUtils.strip(table.value(), "`");
        ENTITY_RAW_TABLE_NAME_CACHE.put(entityClass, tableName);
        return tableName;
    }

    /**
     * 解析实体字段数据库字段名称
     * @param field 字段对象
     * @return 字段名称
     */
    public static String resolveRawFieldName(Field field) {
        // 有就走缓存
        if (ENTITY_RAW_FIELD_NAME_CACHE.containsKey(field)) {
            return ENTITY_RAW_FIELD_NAME_CACHE.get(field);
        }
        TableField column = field.getAnnotation(TableField.class);
        String fieldName = ConversionUtils.camelToDash(field.getName());
        if (Objects.nonNull(column) && StringUtils.isNotBlank(column.value())) {
            fieldName = column.value();
        }
        fieldName = StringUtils.strip(fieldName, "`");
        ENTITY_RAW_FIELD_NAME_CACHE.put(field, fieldName);
        return fieldName;
    }
}
