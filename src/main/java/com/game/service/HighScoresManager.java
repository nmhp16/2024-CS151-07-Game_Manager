package com.game.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HighScoresManager {
    private Map<String, Set<HighScore>> highScores; // Map of usernames and set of high scores

    /**
     * Default Constructor
     */
    public HighScoresManager() {
        highScores = new HashMap<>();
        initializeDefaultUser();
        loadHighScores();
    }

    /**
     * Helper method to initialize default user
     */
    private void initializeDefaultUser() {
        File userAccountFile = new File("user_accounts.txt");

        if (!userAccountFile.exists() || userAccountFile.length() == 0) {
            createDefaultScores();
        }
    }

    /**
     * Method to load high score from file
     */
    public void loadHighScores() {
        highScores.clear(); // Clear existing high scores

        try (BufferedReader reader = new BufferedReader(new FileReader("high_scores.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0];
                int score = Integer.parseInt(parts[1]);
                String gameName = parts[2];

                Set<HighScore> userScores = highScores.putIfAbsent(username, new HashSet<>());

                // Link userScores Set to highScores Map's Set
                if (userScores == null) {
                    userScores = highScores.get(username);
                }

                HighScore newScore = new HighScore(username, score, gameName);
                userScores.add(newScore);

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

        for (String username : accounts.keySet()) {
            Set<HighScore> userScores = highScores.putIfAbsent(username, new HashSet<>());

            // Retrieve existing scores or create new one
            if (highScores.containsKey(username)) {
                userScores = highScores.get(username);
            } else {
                userScores = new HashSet<>();
                highScores.put(username, userScores);
            }

            // Check if "Blackjack" scores exist
            boolean hasBlackjackSCore = false;
            for (HighScore score : userScores) {
                if (score.getGamename().equals("Blackjack")) {
                    hasBlackjackSCore = true;
                    break;
                }
            }

            // Add default score for "Blackjack"
            if (!hasBlackjackSCore) {
                userScores.add(new HighScore(username, 1000, "Blackjack"));
            }

            // Check if a "Snake" score already exists
            boolean hasSnakeScore = false;
            for (HighScore score : userScores) {
                if (score.getGamename().equals("Snake")) {
                    hasSnakeScore = true;
                    break;
                }
            }
            // Add default "Snake" score if not present
            if (!hasSnakeScore) {
                userScores.add(new HighScore(username, 1000, "Snake"));
            }

        }

        // Write updated high scores to file
        saveHighScores();
    }

    /**
     * Helper method to create default account and password to assign scores
     */
    private void createDefaultScores() {
        Set<HighScore> defaultScores = new HashSet<>();

        defaultScores.add(new HighScore("default", 1000, "Blackjack"));
        defaultScores.add(new HighScore("default", 1000, "Snake"));

        highScores.put("default", defaultScores);
        saveHighScores();

        String hashedPassword = PasswordHasher.hashPassword("password");

        try (PrintWriter writer = new PrintWriter(new FileWriter("user_accounts.txt"))) {
            writer.println("default," + hashedPassword);

        } catch (IOException e) {
            System.out.println("Error saving default accounts: " + e.getMessage());
        }
    }

    /**
     * Helper method to write and update high score file
     */
    public void saveHighScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("high_scores.txt"))) {
            for (Map.Entry<String, Set<HighScore>> entry : highScores.entrySet()) {
                for (HighScore highScore : entry.getValue()) {
                    writer.write(highScore.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
