package com.game.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HighScoresManager {
    private List<HighScore> highScores;

    public HighScoresManager() {
        highScores = new ArrayList<>();
        loadHighScores();
    }

    /**
     * Method to load high score from file
     */
    public void loadHighScores() {
        File file = new File("high_scores.txt");
        File userAccountFile = new File("user_accounts.txt");

        // If file does not exist, generate file with default score = 1000
        if (!userAccountFile.exists() || userAccountFile.length() == 0) {
            if (!file.exists() || file.length() == 0) {
                createDefaultScores();
                return;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("high_scores.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0];
                int score = Integer.parseInt(parts[1]);
                String gameName = parts[2];
                highScores.add(new HighScore(username, score, gameName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to create default score = 1000 for account
     * 
     * @param accounts Map of username and password
     */
    public void createDefaultScores(Map<String, String> accounts) {

        File userAccountFile = new File("user_accounts.txt");

        // Check if user_accounts.txt empty before adding
        if (userAccountFile.exists() && !accounts.isEmpty()) {
            // Iterate over each account
            for (String username : accounts.keySet()) {
                // Check if the username has scores already
                boolean usernameExists = false;

                for (HighScore highScore : highScores) {
                    if (highScore.getUsername().equals(username)) {
                        usernameExists = true;
                        break;
                    }
                }

                if (!usernameExists) {
                    highScores.add(new HighScore(username, 1000, "Blackjack"));
                    highScores.add(new HighScore(username, 1000, "Snake"));
                }
            }
        }
        // Write updated high scores to file
        saveHighScores();
    }

    /**
     * Helper method to create default account and password to assign scores
     */
    private void createDefaultScores() {
        highScores.add(new HighScore("default", 1000, "Blackjack"));
        highScores.add(new HighScore("default", 1000, "Snake"));
        saveHighScores();

        try (PrintWriter writer = new PrintWriter(new FileWriter("user_accounts.txt"))) {
            writer.println("default" + "," + "password");

        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    /**
     * Helper method to write and update high score file
     */
    public void saveHighScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("high_scores.txt"))) {
            for (HighScore highScore : highScores) {
                writer.write(highScore.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
