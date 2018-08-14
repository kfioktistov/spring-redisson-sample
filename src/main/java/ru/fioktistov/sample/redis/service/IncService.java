package ru.fioktistov.sample.redis.service;

import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class IncService {

    private static final String VALUES_MAP_NAME = "VALUES";

    @Autowired
    RedissonClient redissonClient;

    public Mono<Long> inc(String id) {
        RLock lock = redissonClient.getLock(String.format("%s:%s", VALUES_MAP_NAME, id));
        Long value;
        lock.lock();
        try {
            RMap<String, Long> map = redissonClient.getMap(VALUES_MAP_NAME);
            value = map.putIfAbsent(id, 0L) + 1;
            map.put(id, value);
        } finally {
            lock.unlock();
        }
        return Mono.just(value);
    }

    public Mono<Long> get(String id) {
        RMap<String, Long> map = redissonClient.getMap(VALUES_MAP_NAME);
        Long value = map.putIfAbsent(id, 0L);
        return Mono.just(value);
    }

}
