package ru.maria.throttler.data;

import com.google.common.collect.EvictingQueue;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Data for save last requests of address
 */
public class ThrottlerAddress {
    private String address;
    private int limit;

    private final ReentrantLock lock;

    /**
     * Queue of last addresses
     */
    private final EvictingQueue<Long> times;

    public ThrottlerAddress(String address, int limit) {
        this.address = address;
        this.limit = limit;
        this.times = EvictingQueue.create(limit + 1);
        lock = new ReentrantLock();
    }

    public boolean check() {
        lock.lock();
        try {
            times.add(System.currentTimeMillis());
            return times.size() <= limit;
        } finally {
            lock.unlock();
        }
    }


}
