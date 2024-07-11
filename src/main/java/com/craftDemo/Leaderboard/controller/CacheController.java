package com.craftDemo.Leaderboard.controller;

import com.craftDemo.Leaderboard.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private RedisCacheService redisCacheService;

    @GetMapping("/clear")
    public String clearCache() {
        redisCacheService.clearCache();
        return "Cache cleared";
    }

    @GetMapping("/data")
    public Map<String, Object> getAllCacheData() {
        return redisCacheService.getAllDataFromCache();
    }
}
