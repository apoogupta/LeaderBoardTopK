package com.craftDemo.Leaderboard.controller;

import com.craftDemo.Leaderboard.entity.Player;
import com.craftDemo.Leaderboard.entity.PlayerDTO;
import com.craftDemo.Leaderboard.service.GetPlayersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LeaderboardController {

    @Autowired
    private GetPlayersService getPlayersService;

    @GetMapping("/getPlayers")
    public List<PlayerDTO> getPlayers() {
        List<Player> players = getPlayersService.invoke();

        // Sort players in decreasing order of score
        players.sort(Comparator.comparingInt(Player::getScore).reversed());

        // Map to PlayerDTO and collect into a list
        return players.stream()
                .map(player -> new PlayerDTO(player.getName(), player.getScore()))
                .collect(Collectors.toList());
    }
}
