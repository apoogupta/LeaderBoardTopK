package com.craftDemo.Leaderboard.entity;

public class PlayerDTO {
    private String name;
    private int score;

    // Constructor
    public PlayerDTO(String name, int score) {
        this.name = name;
        this.score = score;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

