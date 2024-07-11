package com.craftDemo.Leaderboard.service;

import com.craftDemo.Leaderboard.entity.Player;
import com.craftDemo.Leaderboard.repository.PlayersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DatabasePersistService {

    @Autowired
    private PlayersRepository playersRepository;

    @Autowired
    private RedisCacheService redisCacheService;

    @Scheduled(fixedRate = 60000) // Executes every 60 seconds
    public void persistLeaderboardData() {
        log.info("Persisting data into DB");

        // Fetch data from Redis
        List<Player> topPlayers = redisCacheService.getTopPlayersFromCache();

        // Persist to database
        if (topPlayers != null && !topPlayers.isEmpty()) {
            playersRepository.saveAll(topPlayers);
            log.info("Persisted {} players to database", topPlayers.size());
        } else {
            log.info("No data to persist to database");
        }
    }
}
