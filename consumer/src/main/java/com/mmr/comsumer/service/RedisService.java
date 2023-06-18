package com.mmr.comsumer.service;

import com.mmr.comsumer.domain.model.UserMdl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(String key, List<UserMdl> userMdlList) {
        try {
            redisTemplate.opsForList().rightPush(key, userMdlList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object get(String key) {
        try {
            return redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
