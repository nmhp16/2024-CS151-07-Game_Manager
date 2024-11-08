package com.game.service;

public class HighScore {
    private String username;
    private int score;
    private String gameName;

    public HighScore(String username, int score, String gameName) {
        this.username = username;
        this.score = score;
        this.gameName = gameName;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public String getGamename() {
        return gameName;
    }

    @Override
    public String toString() {
        return username + "," + score + "," + gameName;
    }
}
