package com.craftDemo.Leaderboard.service;

import com.craftDemo.Leaderboard.entity.Player;
import com.craftDemo.Leaderboard.repository.PlayersRepository;
import com.craftDemo.Leaderboard.strategy.GetTopKStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GetPlayersService {

    @Autowired
    private RedisCacheService redisCacheService;

    @Autowired
    private PlayersRepository playersRepository;

    @Autowired
    @Qualifier("minHeapStrategy")
    private GetTopKStrategy getTopKStrategy;

    @Value("${playerCount}")
    private int TOP_K;

    public List<Player> invoke() {
        List<Player> playersList = getTopKStrategy.getPlayers();

        if(playersList != null)
        log.info("Number of Players received: {}", playersList.size());

        if (playersList == null || playersList.isEmpty()) {
            log.info("Data not found - retrieving from Cache");
            playersList = redisCacheService.getTopPlayersFromCache();

            if (playersList == null || playersList.isEmpty()) {
                log.info("Data not found - retrieving from DB");
                playersList = playersRepository.findTopKPlayers(TOP_K);
                redisCacheService.updateLeaderboardCache(playersList);
            }
        }

        return playersList;
    }
}
