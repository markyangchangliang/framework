package com.markyang.framework.app.base.cache.redis;

import com.markyang.framework.app.base.core.FrameworkExpressionEvaluator;
import com.markyang.framework.app.base.exception.ExpressionEvaluateException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.AbstractCacheResolver;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 框架缓存key解析器
 *
 * @author yangchangliang
 * @version 1
 */
public class FrameworkCacheResolver extends AbstractCacheResolver {
    private final FrameworkExpressionEvaluator expressionEvaluator;

    /**
     * Construct a new {@code AbstractCacheResolver} for the given {@link CacheManager}.
     *
     * @param cacheManager the CacheManager to use
     */
    public FrameworkCacheResolver(CacheManager cacheManager, FrameworkExpressionEvaluator expressionEvaluator) {
        super(cacheManager);
        this.expressionEvaluator = expressionEvaluator;
    }

    /**
     * Provide the name of the cache(s) to resolve against the current cache manager.
     * <p>It is acceptable to return {@code null} to indicate that no cache could
     * be resolved for this invocation.
     *
     * @param context the context of the particular invocation
     * @return the cache name(s) to resolve, or {@code null} if no cache should be resolved
     */
    @Override
    protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
        Set<String> cacheNames = context.getOperation().getCacheNames();
        // 解析SpEL表达式
        return cacheNames.parallelStream()
            .map(cacheName -> {
                if (StringUtils.startsWithAny(cacheName, "#", "T")) {
                    // 如果是SpEL表达式，则解析它
                    try {
                        return ConvertUtils.convert(
                                this.expressionEvaluator.evaluate(cacheName, context.getMethod(), context.getArgs(), context.getTarget(), context.getTarget().getClass(), Object.class)
                        );
                    } catch (Exception e) {
                        throw new ExpressionEvaluateException("表达式执行出现异常[" + e.getMessage() + "]，请考虑是否为控制器[" + context.getTarget().getClass().getName() + "]添加@CacheName注解");
                    }
                }
                // 否则原样返回
                return cacheName;
            })
            .collect(Collectors.toList());
    }
}
