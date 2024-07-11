package com.craftDemo.Leaderboard.strategy;

import com.craftDemo.Leaderboard.entity.Player;

import java.util.List;

public interface GetTopKStrategy {

     void addPlayer (Player player);
     void removePlayer();
     List<Player> getPlayers();

}
