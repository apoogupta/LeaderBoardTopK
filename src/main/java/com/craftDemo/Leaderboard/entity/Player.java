package com.craftDemo.Leaderboard.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;


@Entity
@Data
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private static final long serialVersionUID = 4924034777670713314L;

    private String name;
    private int score;

    public Player() {
    }

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

}
