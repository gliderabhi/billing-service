package com.sevis.billingservice.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

// In-process (Caffeine) caching — this service runs as a single instance with
// no shared/replicated state, so there's no need for a centralized cache here;
// each cache below is evicted explicitly by the write paths that invalidate it
// (see @CacheEvict usages), with a short TTL as a backstop only. This is a
// billing/financial service: every Bill read exposes mutable amount/status
// fields, so TTLs are kept deliberately short (20s) to bound staleness rather
// than relying solely on eviction — favor correctness (no stale paid/unpaid
// status) over hit-rate.
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(List.of(
                buildCache("billById", 20, TimeUnit.SECONDS, 2000),
                buildCache("billsAll", 20, TimeUnit.SECONDS, 10),
                buildCache("billsByUser", 20, TimeUnit.SECONDS, 1000)
        ));
        return manager;
    }

    private CaffeineCache buildCache(String name, long ttl, TimeUnit unit, int maxSize) {
        return new CaffeineCache(name, Caffeine.newBuilder()
                .expireAfterWrite(ttl, unit)
                .maximumSize(maxSize)
                .recordStats()
                .build());
    }
}
