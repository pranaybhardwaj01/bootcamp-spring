package com.test.crud.redis;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisUtils<T> {
    private RedisTemplate<String, T> redisTemplate;
    private HashOperations<String, Object, T> hashOperation;

    RedisUtils(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        this.hashOperation = redisTemplate.opsForHash();
    }

    public void putMap(String redisKey, Object key, T data) {
        hashOperation.put(redisKey, key, data);
    }

    public T getMapAsSingleEntry(String redisKey, Object key) {
        return hashOperation.get(redisKey, key);
    }

    public Map<Object, T> getMapAsAll(String redisKey) {
        return hashOperation.entries(redisKey);
    }

    public void setExpire(String key, long timeout, TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }
}
