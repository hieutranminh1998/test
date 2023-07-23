package com.example.health.configuration.cache;

import com.example.health.ulti.CacheUtil;
import com.google.common.cache.CacheBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CachingConfig extends CachingConfigurerSupport {

    @Bean
    @Override
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager() {
//            @Override
//            protected Cache createConcurrentMapCache(final String name) {
//                return new ConcurrentMapCache(name, CacheBuilder.newBuilder().expireAfterWrite(60*60, TimeUnit.SECONDS)
//                        .build().asMap(), true);
//            }
        };
        cacheManager.setCacheNames(
                Arrays.asList(
                        CacheUtil.CACHE_NAME.STEP,
                        CacheUtil.CACHE_NAME.CUSTOMER,
                        CacheUtil.CACHE_NAME.TOTAL_STEP_IN_WEEK,
                        CacheUtil.CACHE_NAME.TOTAL_STEP_IN_MONTH));
        return cacheManager;
    }
}
