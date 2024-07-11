package com.craftDemo.Leaderboard.service;

import com.craftDemo.Leaderboard.entity.Player;
import com.craftDemo.Leaderboard.strategy.GetTopKStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    @Autowired
    @Qualifier("minHeapStrategy")
    private GetTopKStrategy getTopKStrategy;


    @KafkaListener(topics = "playersScores", groupId = "my-group")
    public void consume(String message) {
        String[] parts = message.split(", ");
        String playerName = parts[0].split(": ")[1];
        int playerScore = Integer.parseInt(parts[1].split(": ")[1]);
        Player player = new Player(playerName,playerScore);
        log.info("Received player Name: {} player Score: {}",playerName,playerScore);
        getTopKStrategy.addPlayer(player);
    }


}