package com.markyang.framework.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 对象操作工具类
 *
 * @author yangchangliang
 * @version 1
 */
@Slf4j
public final class BeanOperationUtils {

    /**
     * 从一个Map构造一个Bean对象
     * @param beanClass bean 类
     * @param map map对象
     * @param <T> bean 泛型
     * @return bean 实例
     */
    public static <T> T fromMap(Class<T> beanClass, Map<String, Object> map) {
        return ReflectionOperationUtils.newInstance(beanClass)
            .map(instance -> {
                try {
                    BeanUtils.populate(instance, map);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("为Bean对象{}填充属性发生异常：", ClassUtils.getShortName(beanClass), e);
                }
                return instance;
            }).orElseThrow(() -> new IllegalStateException("构造" + ClassUtils.getShortName(beanClass) + "实例失败"));
    }

    /**
     * 从一个Bean构造另一个Bean对象
     * @param beanClass bean 类
     * @param bean bean对象
     * @param <T> bean 泛型
     * @return bean 实例
     */
    public static <T> T fromBean(Class<T> beanClass, Object bean) {
        return ReflectionOperationUtils.newInstance(beanClass)
            .map(instance -> {
                try {
                    BeanUtils.copyProperties(instance, bean);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("为Bean对象{}填充属性发生异常：", ClassUtils.getShortName(beanClass), e);
                }
                return instance;
            }).orElseThrow(() -> new IllegalStateException("构造" + ClassUtils.getShortName(beanClass) + "实例失败"));
    }
}
