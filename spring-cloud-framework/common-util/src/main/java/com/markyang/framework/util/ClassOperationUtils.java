package com.markyang.framework.util;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Class操作工具类
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@Slf4j
public final class ClassOperationUtils {

    /**
     * 资源加载器
     */
    private static ResourcePatternResolver resourcePatternResolver;
    private static MetadataReaderFactory metadataReaderFactory;

    public ClassOperationUtils(ApplicationContext applicationContext) {
        if (Objects.isNull(resourcePatternResolver)) {
            setApplicationContext(applicationContext);
        }
    }

    /**
     * 设置ApplicationContext上下文
     * @param applicationContext 上下文
     */
    public static synchronized void setApplicationContext(ApplicationContext applicationContext) {
        if (Objects.isNull(resourcePatternResolver)) {
            resourcePatternResolver = applicationContext;
            metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
        }
    }

    /**
     * 数字类集合
     */
    private static final Set<Class<?>> PRIMITIVE_OR_WRAPPER_CLASSES = Sets.newHashSet(
        byte.class,
        short.class,
        int.class,
        long.class,
        float.class,
        double.class,
        Byte.class,
        Short.class,
        Integer.class,
        Long.class,
        Float.class,
        Double.class
    );

    /**
     * 加载Class类
     * @param className 类全路径
     * @return 类
     */
    public static Optional<Class<?>> loadClass(String className) {
        try {
            Class<?> clazz = ClassUtils.getClass(className, true);
            return Optional.of(clazz);
        } catch (ClassNotFoundException e) {
            // 找不到类
            log.error("类{}找不到", className);
            return Optional.empty();
        }
    }

    /**
     * 判断一个类是否是Object类型
     * @param clazz 类
     * @return bool
     */
    public static boolean isObjectClass(Class<?> clazz) {
        return Objects.equals(clazz, Object.class);
    }

    /**
     * 判断类是否是字符串类
     * @param clazz 类
     * @return bool
     */
    public static boolean isStringClass(Class<?> clazz) {
        return ClassUtils.isAssignable(clazz, CharSequence.class);
    }

    /**
     * 判断对象是否是字符串类型
     * @param object 对象
     * @return bool
     */
    public static boolean isString(Object object) {
        return isStringClass(object.getClass());
    }

    /**
     * 是否是可比较的类型
     * @param clazz 类
     * @return bool
     */
    public static boolean isComparableClass(Class<?> clazz) {
        return ClassUtils.isAssignable(clazz, Comparable.class);
    }

    /**
     * 判断一个Class类型是否是数字类型
     * @param clazz 类
     * @return bool
     */
    public static boolean isNumberClass(Class<?> clazz) {
        return PRIMITIVE_OR_WRAPPER_CLASSES.contains(clazz);
    }

    /**
     * 判断是否是通用java类型类
     * @param clazz 类
     * @return bool
     */
    public static boolean isCommonTypeClass(Class<?> clazz) {
        return ClassUtils.isPrimitiveOrWrapper(clazz) || isStringClass(clazz);
    }

    /**
     * 扫描某个包的所有的类
     * @param classesPackage 包路径
     * @param filter 过滤器
     * @return 类集合
     */
    public static Set<Class<?>> scanPackageClasses(String classesPackage, Predicate<MetadataReader> filter) {
        log.info("开始扫描包路径：{}", classesPackage);
        Set<Class<?>> classes = Sets.newLinkedHashSet();
        String path = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + org.springframework.util.ClassUtils.convertClassNameToResourcePath(classesPackage) + "/**/*.class";
        Resource[] resources;
        try {
            resources = resourcePatternResolver.getResources(path);
        } catch (IOException e) {
            log.warn("扫描路径出现错误：{}", e.getMessage());
            return Collections.emptySet();
        }
        for (Resource resource : resources) {
            if (!resource.exists() || !resource.isReadable()) {
                continue;
            }
            MetadataReader metadataReader;
            try {
                metadataReader = metadataReaderFactory.getMetadataReader(resource);
            } catch (IOException e) {
                log.warn("读取Class[{}]信息出现错误：{}", resource.getDescription(), e.getMessage());
                continue;
            }
            if (Objects.nonNull(filter) && !filter.test(metadataReader)) {
                continue;
            }
            // 这里处理真正的类
            loadClass(metadataReader.getClassMetadata().getClassName()).ifPresent(classes::add);
        }
        return classes;
    }
}
