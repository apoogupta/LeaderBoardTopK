package com.craftDemo.Leaderboard.service;

import com.craftDemo.Leaderboard.entity.Player;
import com.craftDemo.Leaderboard.repository.PlayersRepository;
import com.craftDemo.Leaderboard.strategy.GetTopKStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class GetPlayersServiceTest {

    @Mock
    private RedisCacheService redisCacheService;

    @Mock
    private PlayersRepository playersRepository;

    @Mock
    @Qualifier("minHeapStrategy")
    private GetTopKStrategy getTopKStrategy;

    @InjectMocks
    private GetPlayersService getPlayersService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInvoke_NoPlayersFromAnySource() {
        // Arrange
        when(getTopKStrategy.getPlayers()).thenReturn(null);
        when(redisCacheService.getTopPlayersFromCache()).thenReturn(null);
        when(playersRepository.findTopKPlayers(anyInt())).thenReturn(null);

        // Act
        List<Player> players = getPlayersService.invoke();

        // Assert
        assertEquals(null, players);
        verify(getTopKStrategy, times(1)).getPlayers();
        verify(redisCacheService, times(1)).getTopPlayersFromCache();
        verify(playersRepository, times(1)).findTopKPlayers(anyInt());
        verify(redisCacheService, never()).updateLeaderboardCache(anyList());
    }

    @Test
    public void testInvoke_PlayersFromMainSource() {
        // Arrange
        List<Player> expectedPlayers = Arrays.asList(
                new Player("Apoorva", 100),
                new Player("Divya", 90)
        );
        when(getTopKStrategy.getPlayers()).thenReturn(expectedPlayers); // Simulate no players from strategy
        when(redisCacheService.getTopPlayersFromCache()).thenReturn(null); // Simulate cache hit

        // Act
        List<Player> players = getPlayersService.invoke();

        // Assert
        assertEquals(expectedPlayers.size(), players.size());
        assertEquals(expectedPlayers.get(0).getName(), players.get(0).getName());
        assertEquals(expectedPlayers.get(1).getName(), players.get(1).getName());
        verify(getTopKStrategy, times(1)).getPlayers();
        verify(redisCacheService, never()).getTopPlayersFromCache();
        verify(playersRepository, never()).findTopKPlayers(anyInt());
        verify(redisCacheService, never()).updateLeaderboardCache(anyList());
    }

    @Test
    public void testInvoke_PlayersFromCache() {
        // Arrange
        List<Player> expectedPlayers = Arrays.asList(
                new Player("Player1", 100),
                new Player("Player2", 90)
        );
        when(getTopKStrategy.getPlayers()).thenReturn(null); // Simulate no players from strategy
        when(redisCacheService.getTopPlayersFromCache()).thenReturn(expectedPlayers); // Simulate cache hit

        // Act
        List<Player> players = getPlayersService.invoke();

        // Assert
        assertEquals(expectedPlayers.size(), players.size());
        assertEquals(expectedPlayers.get(0).getName(), players.get(0).getName());
        assertEquals(expectedPlayers.get(1).getName(), players.get(1).getName());
        verify(getTopKStrategy, times(1)).getPlayers();
        verify(redisCacheService, times(1)).getTopPlayersFromCache();
        verify(playersRepository, never()).findTopKPlayers(anyInt());
        verify(redisCacheService, never()).updateLeaderboardCache(anyList());
    }

    @Test
    public void testInvoke_PlayersFromDB() {
        // Arrange
        List<Player> expectedPlayers = Arrays.asList(
                new Player("Player1", 100),
                new Player("Player2", 90)
        );
        when(getTopKStrategy.getPlayers()).thenReturn(null); // Simulate no players from strategy
        when(redisCacheService.getTopPlayersFromCache()).thenReturn(null); // Simulate cache hit
        when(playersRepository.findTopKPlayers(anyInt())).thenReturn(expectedPlayers);

        // Act
        List<Player> players = getPlayersService.invoke();

        // Assert
        assertEquals(expectedPlayers.size(), players.size());
        assertEquals(expectedPlayers.get(0).getName(), players.get(0).getName());
        assertEquals(expectedPlayers.get(1).getName(), players.get(1).getName());
        verify(getTopKStrategy, times(1)).getPlayers();
        verify(redisCacheService, times(1)).getTopPlayersFromCache();
        verify(playersRepository, times(1)).findTopKPlayers(anyInt());
        verify(redisCacheService, times(1)).updateLeaderboardCache(anyList());

    }

    // Add more test cases to cover other scenarios as needed

}