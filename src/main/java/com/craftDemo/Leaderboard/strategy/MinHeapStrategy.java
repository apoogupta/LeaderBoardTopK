package com.craftDemo.Leaderboard.strategy;

import com.craftDemo.Leaderboard.entity.Player;
import com.craftDemo.Leaderboard.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;


public class MinHeapStrategy implements GetTopKStrategy {

    private PriorityQueue<Player> topPlayers;

    @Autowired
    private RedisCacheService redisCacheService;

    @Value("${playerCount}")
    private int k;

    @Autowired
    public MinHeapStrategy() {
        this.topPlayers = new PriorityQueue<>(Comparator.comparing(Player::getScore));
    }

    @Override
    public void addPlayer(Player player) {

        //if we have our top k players and new player comes with same score , ignore that player
        Player minScorePlayer = topPlayers.peek();
        if(topPlayers.size() == k && minScorePlayer.getScore() >= player.getScore())
            return;

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
