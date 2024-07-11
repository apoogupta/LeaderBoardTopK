package com.craftDemo.Leaderboard.strategy;

import com.craftDemo.Leaderboard.entity.Player;
import com.craftDemo.Leaderboard.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


public class MinHeapStrategy implements GetTopKStrategy {

    private final PriorityQueue<Player> topPlayers;
    private final RedisCacheService redisCacheService;

    @Value("${playerCount}")
    private int k;

    @Autowired
    public MinHeapStrategy(RedisCacheService redisCacheService) {
        this.topPlayers = new PriorityQueue<>(Comparator.comparing(Player::getScore));
        this.redisCacheService = redisCacheService;
    }

    @Override
    public void addPlayer(Player player) {
        topPlayers.add(player);
        if (topPlayers.size() > k) {
            removePlayer();
        }
        updateCache();
    }

    @Override
    public void removePlayer() {
        if (topPlayers.size() > k) {
            topPlayers.poll();
            updateCache();
        }
    }

    @Override
    public List<Player> getPlayers() {
        return new ArrayList<>(topPlayers);
    }

    private void updateCache() {
        redisCacheService.updateLeaderboardCache(getPlayers());
    }
}
