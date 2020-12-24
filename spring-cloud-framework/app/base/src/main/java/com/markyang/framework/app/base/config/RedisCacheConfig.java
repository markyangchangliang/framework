package com.markyang.framework.app.base.config;

import com.markyang.framework.pojo.constant.CacheConstants;
import com.markyang.framework.util.BuilderUtils;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

/**
 * 缓存key生成器配置
 *
 * @author yangchangliang
 * @version 1
 */
@Configuration
public class RedisCacheConfig {

    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        Map<String, RedisCacheConfiguration> redisCaches = this.configRedisCaches();
        return RedisCacheManager
            .builder(redisConnectionFactory)
            .cacheDefaults(this.configTtl(Duration.ofDays(1)))
            .initialCacheNames(redisCaches.keySet())
            .withInitialCacheConfigurations(redisCaches)
            .build();
    }

    /**
     * 配置多个缓存配置
     * @return 配置
     */
    private Map<String, RedisCacheConfiguration> configRedisCaches() {
        return BuilderUtils.<String, RedisCacheConfiguration>newHashMapBuilder()
            .put("query", this.configTtl(Duration.ofDays(1)))
            .build();
    }

    /**
     * 配置缓存时长
     * @param duration 时长
     * @return 配置
     */
    private RedisCacheConfiguration configTtl(Duration duration) {
        return RedisCacheConfiguration.defaultCacheConfig()
            // 配置默认的缓存名字
            .computePrefixWith(cacheName -> CacheConstants.CACHE_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR + cacheName + CacheConstants.CACHE_KEY_SEPARATOR)
            // 字符串Key
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer(StandardCharsets.UTF_8)))
            // JSON值
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()))
            // 设置时间
            .entryTtl(duration);
    }
}
