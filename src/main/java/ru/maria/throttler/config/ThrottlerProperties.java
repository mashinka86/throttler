package ru.maria.throttler.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Parameters of throttling
 */
@Configuration
@ConfigurationProperties(prefix = "throttler")
public class ThrottlerProperties {

    private int limit;
    private long time;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time * 60 * 1000;
    }
}
