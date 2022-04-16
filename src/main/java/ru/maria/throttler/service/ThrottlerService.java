package ru.maria.throttler.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;
import ru.maria.throttler.config.ThrottlerProperties;
import ru.maria.throttler.data.ThrottlerAddress;

import java.time.Duration;

/**
 * Service for check if address is throttling
 */
@Service
public class ThrottlerService {


    /**
     * Cache of last addresses
     */
    private LoadingCache<String, ThrottlerAddress> cache;

    public ThrottlerService(ThrottlerProperties throttlerProperties) {
        CacheLoader<String, ThrottlerAddress> loader = new CacheLoader<>() {
            @Override
            public ThrottlerAddress load(String address) {
                return new ThrottlerAddress(address, throttlerProperties.getLimit());
            }
        };
        cache = CacheBuilder.newBuilder().expireAfterAccess(Duration.ofMillis(throttlerProperties.getTime())).build(loader);
    }

    public boolean check(String address) {
        ThrottlerAddress throttlerAddress = cache.getUnchecked(address);
        return throttlerAddress.check();
    }
}
