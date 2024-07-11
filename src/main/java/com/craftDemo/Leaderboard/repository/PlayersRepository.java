package com.craftDemo.Leaderboard.repository;

import com.craftDemo.Leaderboard.entity.Player;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;


@Repository
public interface PlayersRepository extends JpaRepository<Player, Long> {

    @Query(value = "SELECT * FROM player ORDER BY score DESC LIMIT :topK", nativeQuery = true)
    List<Player> findTopKPlayers(@Param("topK") int topK);
}
