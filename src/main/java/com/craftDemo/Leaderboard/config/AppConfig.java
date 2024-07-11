package com.craftDemo.Leaderboard.config;

import com.craftDemo.Leaderboard.service.RedisCacheService;
import com.craftDemo.Leaderboard.strategy.CountMinSketchStrategy;
import com.craftDemo.Leaderboard.strategy.MinHeapStrategy;
import com.craftDemo.Leaderboard.strategy.GetTopKStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    @Qualifier("minHeapStrategy")
    public GetTopKStrategy minHeapStrategy() {
        return new MinHeapStrategy();
    }

    @Bean
    @Qualifier("countMinSketchStrategy")
    public GetTopKStrategy countMinSketchStrategy() {
        return new CountMinSketchStrategy();
    }

}
