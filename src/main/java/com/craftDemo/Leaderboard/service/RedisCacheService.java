package com.craftDemo.Leaderboard.service;

import com.craftDemo.Leaderboard.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RedisCacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String LEADERBOARD_CACHE_KEY = "leaderboard";

    public List<Player> getTopPlayersFromCache() {
        return (List<Player>) redisTemplate.opsForValue().get(LEADERBOARD_CACHE_KEY);
    }

    public void updateLeaderboardCache(List<Player> topPlayers) {
        redisTemplate.opsForValue().set(LEADERBOARD_CACHE_KEY, topPlayers);
    }

    public void clearCache() {
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }

    public Map<String, Object> getAllDataFromCache() {
        Map<String, Object> cacheData = new HashMap<>();
        Object value = redisTemplate.opsForValue().get(LEADERBOARD_CACHE_KEY);
        cacheData.put(LEADERBOARD_CACHE_KEY, value);
        return cacheData;
    }
}
