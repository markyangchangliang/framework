package com.markyang.framework.app.base.core;

import com.google.common.collect.Maps;
import com.markyang.framework.app.base.exception.ResourceLoadException;
import com.markyang.framework.util.ClassOperationUtils;
import com.markyang.framework.util.ReflectionOperationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * SQL文件加载器
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@Slf4j
public class SqlFileLoader implements ResourceLoaderAware {
    /**
     * Set the ResourceLoader that this object runs in.
     * <p>This might be a ResourcePatternResolver, which can be checked
     * through {@code instanceof ResourcePatternResolver}. See also the
     * {@code ResourcePatternUtils.getResourcePatternResolver} method.
     * <p>Invoked after population of normal bean properties but before an init callback
     * like InitializingBean's {@code afterPropertiesSet} or a custom init-method.
     * Invoked before ApplicationContextAware's {@code setApplicationContext}.
     *
     * @param resourceLoader the ResourceLoader object to be used by this object
     * @see ResourcePatternResolver
     * @see ResourcePatternUtils#getResourcePatternResolver
     */
    @Override
    public void setResourceLoader(@NonNull ResourceLoader resourceLoader) {
        ResourcePatternResolver resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        try {
            Resource[] resources = resourcePatternResolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "sqls/**/*.sql");
            for (Resource resource : resources) {
                if (!resource.exists() || !resource.isReadable()) {
                    // 无法读取
                    continue;
                }
                Map<String, String> sqlMap = Maps.newHashMap();
                String key = "";
                String sql = "";
                List<String> lines = IOUtils.readLines(resource.getInputStream(), StandardCharsets.UTF_8);
                if (CollectionUtils.isEmpty(lines)) {
                    // 文件为空
                    continue;
                }
                String first = lines.get(0);
                if (!StringUtils.startsWith(first, "#")) {
                    throw new ResourceLoadException("SQL定义文件[" + resource.getFilename() + "]必须以#类全限定名开头");
                }
                String className = StringUtils.trimToEmpty(StringUtils.substringAfterLast(first, "#"));
                Optional<Class<?>> clazzOptional = ClassOperationUtils.loadClass(className);
                if (!clazzOptional.isPresent()) {
                    // 类不存在
                    throw new ResourceLoadException("无法加载SQL常量接口类[" + className + "]");
                }
                for (String line : lines) {
                    if (StringUtils.equals(line, first)) {
                        // 跳过第一行
                        continue;
                    }
                    if (StringUtils.startsWith(line, "##")) {
                        // 跳过注释行
                        continue;
                    }
                    String pure = StringUtils.trimToEmpty(line);
                    if (StringUtils.isBlank(pure)) {
                        continue;
                    }
                    if (StringUtils.startsWith(pure, "#")) {
                        // 注释列
                        if (StringUtils.isBlank(key) && StringUtils.isNotBlank(sql)) {
                            // 说明SQL没有定义key
                            throw new ResourceLoadException("SQL[" + sql + "]未定义Key");
                        } else if (StringUtils.isNotBlank(key)) {
                            // 说明可能有空的sql定义
                            sqlMap.put(key, sql);
                            key = StringUtils.trimToEmpty(StringUtils.substringAfterLast(pure, "#"));
                            sql = "";
                        } else {
                            key = StringUtils.trimToEmpty(StringUtils.substringAfterLast(pure, "#"));
                        }
                    } else {
                        // 拼接SQL
                        sql += " " + StringUtils.stripEnd(pure, ";");
                    }
                }
                // 添加最后一条SQL
                if (StringUtils.isBlank(key) && StringUtils.isNotBlank(sql)) {
                    // 说明SQL没有定义key
                    throw new ResourceLoadException("SQL[" + sql + "]未定义Key");
                }
                sqlMap.put(key, sql);
                this.populateSqlConstants(clazzOptional.get(), sqlMap);
            }
        } catch (IOException e) {
            log.error("SQL资源文件加载失败：{}", e.getMessage());
        }
    }

    /**
     * 填充常量类字段
     * @param clazz 类
     * @param sqlMap SQL映射
     */
    private void populateSqlConstants(Class<?> clazz, Map<String, String> sqlMap) {
        List<Field> fields = ReflectionOperationUtils.getClassAllDeclaredFieldsWithoutCache(clazz, field -> Modifier.isStatic(field.getModifiers()));
        for (Field field : fields) {
            if (sqlMap.containsKey(field.getName())) {
                // 设值
                ReflectionOperationUtils.setStaticFieldValue(field, sqlMap.get(field.getName()));
            }
        }
    }
}
