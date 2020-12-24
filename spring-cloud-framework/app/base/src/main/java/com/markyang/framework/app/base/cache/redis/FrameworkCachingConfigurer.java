package com.markyang.framework.app.base.cache.redis;

import com.markyang.framework.app.base.core.FrameworkExpressionEvaluator;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 框架缓存配置
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@AllArgsConstructor
public class FrameworkCachingConfigurer implements CachingConfigurer {

    private final KeyGenerator keyGenerator;
    private final FrameworkExpressionEvaluator expressionEvaluator;
    private final CacheManager cacheManager;

    /**
     * Return the cache manager bean to use for annotation-driven cache
     * management. A default {@link CacheResolver} will be initialized
     * behind the scenes with this cache manager. For more fine-grained
     * management of the cache resolution, consider setting the
     * {@link CacheResolver} directly.
     * <p>Implementations must explicitly declare
     * {@link Bean @Bean}, e.g.
     * See @{@link EnableCaching} for more complete examples.
     */
    @Override
    public CacheManager cacheManager() {
        return this.cacheManager;
    }

    /**
     * Return the {@link CacheResolver} bean to use to resolve regular caches for
     * annotation-driven cache management. This is an alternative and more powerful
     * option of specifying the {@link CacheManager} to use.
     * <p>If both a {@link #cacheManager()} and {@code #cacheResolver()} are set,
     * the cache manager is ignored.
     * <p>Implementations must explicitly declare
     * {@link Bean @Bean}, e.g.
     * See {@link EnableCaching} for more complete examples.
     */
    @Override
    public CacheResolver cacheResolver() {
        return new FrameworkCacheResolver(this.cacheManager, this.expressionEvaluator);
    }

    /**
     * Return the key generator bean to use for annotation-driven cache management.
     * Implementations must explicitly declare
     * {@link Bean @Bean}, e.g.
     * See @{@link EnableCaching} for more complete examples.
     */
    @Override
    public KeyGenerator keyGenerator() {
        return this.keyGenerator;
    }

    /**
     * Return the {@link CacheErrorHandler} to use to handle cache-related errors.
     * <p>By default,{@link SimpleCacheErrorHandler}
     * is used and simply throws the exception back at the client.
     * <p>Implementations must explicitly declare
     * {@link Bean @Bean}, e.g.
     * See @{@link EnableCaching} for more complete examples.
     */
    @Override
    public CacheErrorHandler errorHandler() {
        return null;
    }
}
