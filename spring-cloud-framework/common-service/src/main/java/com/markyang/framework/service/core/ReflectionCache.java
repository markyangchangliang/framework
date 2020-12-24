package com.markyang.framework.service.core;

import org.springframework.util.ConcurrentReferenceHashMap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

/**
 * 反射缓存
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/24 10:42 上午 星期二
 */
public final class ReflectionCache {

    /**
     * 反射字段缓存
     */
    private final static Map<Class<?>, Map<String, Field>> CLASS_FIELDS_CACHE = new ConcurrentReferenceHashMap<>(128);

    /**
     * 反射方法缓存
     */
    private final static Map<Class<?>, Map<String, Method>> CLASS_METHODS_CACHE = new ConcurrentReferenceHashMap<>(128);

    /**
     * 缓存反射字段
     * @param clazz 类
     * @param field 字段对象
     */
    public static void cacheField(Class<?> clazz, Field field) {
        if (!CLASS_FIELDS_CACHE.containsKey(clazz)) {
            CLASS_FIELDS_CACHE.put(clazz, new ConcurrentReferenceHashMap<>(64));
        }
        CLASS_FIELDS_CACHE.get(clazz).put(field.getName(), field);
    }

    /**
     * 获取缓存的反射字段
     * @param clazz 类
     * @param fieldName 字段名称
     * @return optional结果
     */
    public static Optional<Field> getCachedField(Class<?> clazz, String fieldName) {
        if (CLASS_FIELDS_CACHE.containsKey(clazz)) {
            return Optional.ofNullable(CLASS_FIELDS_CACHE.get(clazz).get(fieldName));
        }
        return Optional.empty();
    }

    /**
     * 字段缓存是否存在
     * @param clazz 类
     * @param fieldName 字段名称
     * @return bool
     */
    public static boolean isFieldCacheExists(Class<?> clazz, String fieldName) {
        return getCachedField(clazz, fieldName).isPresent();
    }

    /**
     * 缓存反射字段
     * @param clazz 类
     * @param method 方法对象
     */
    public static void cacheMethod(Class<?> clazz, Method method) {
        if (!CLASS_METHODS_CACHE.containsKey(clazz)) {
            CLASS_METHODS_CACHE.put(clazz, new ConcurrentReferenceHashMap<>(64));
        }
        CLASS_METHODS_CACHE.get(clazz).put(method.getName(), method);
    }

    /**
     * 获取缓存的反射字段
     * @param clazz 类
     * @param methodName 方法名称
     * @return optional结果
     */
    public static Optional<Method> getCachedMethod(Class<?> clazz, String methodName) {
        if (CLASS_METHODS_CACHE.containsKey(clazz)) {
            return Optional.ofNullable(CLASS_METHODS_CACHE.get(clazz).get(methodName));
        }
        return Optional.empty();
    }

    /**
     * 方法缓存是否存在
     * @param clazz 类
     * @param methodName 方法名称
     * @return bool
     */
    public static boolean isMethodCacheExists(Class<?> clazz, String methodName) {
        return getCachedMethod(clazz, methodName).isPresent();
    }
}
