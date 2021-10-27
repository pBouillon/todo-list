package io.pbouillon.todolist.infrastructure.configuration.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class to configure the Redis cache
 */
@Configuration
public class RedisConfiguration {

    @Value("${cache.ttl.item}")
    private String itemCacheEntryTtl;

    @Value("${cache.ttl.items}")
    private String itemsCacheEntryTtl;

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> {
            Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();

            configurationMap.put(
                    "item",
                    RedisCacheConfiguration.defaultCacheConfig()
                            .entryTtl(
                                    Duration.ofSeconds(Integer.parseInt(itemCacheEntryTtl))));

            configurationMap.put(
                    "items",
                    RedisCacheConfiguration.defaultCacheConfig()
                            .entryTtl(
                                    Duration.ofSeconds(Integer.parseInt(itemsCacheEntryTtl))));

            builder.withInitialCacheConfigurations(configurationMap);
        };
    }

}
