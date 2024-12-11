package com.game.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.game.model.HighScore;

public class HighScoresManager {
    private Map<String, List<HighScore>> highScores; // Map of usernames and set of high scores

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

                List<HighScore> userScores = highScores.computeIfAbsent(username, k -> new ArrayList<>());

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
            List<HighScore> userScores = highScores.computeIfAbsent(username, k -> new ArrayList<>());

            // Retrieve existing scores or create new one
            if (highScores.containsKey(username)) {
                userScores = highScores.get(username);
            } else {
                userScores = new ArrayList<>();
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
                // Write updated high scores to file
                saveHighScores();
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
                // Write updated high scores to file
                saveHighScores();
            }

        }

    }

    /**
     * Helper method to create default account and password to assign scores
     */
    private void createDefaultScores() {
        List<HighScore> defaultScores = new ArrayList<>();

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
            for (Map.Entry<String, List<HighScore>> entry : highScores.entrySet()) {
                for (HighScore highScore : entry.getValue()) {
                    writer.write(highScore.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the top high scores for a specified game.
     * 
     * This method filters high scores based on the provided game name,
     * sorts them in descending order, and returns a list of the top scores
     * up to the specified limit.
     * 
     * @param gameName The name of the game for which top scores are requested.
     * @param limit    The maximum number of top scores to return.
     * @return A list of top high scores for the specified game, limited to the
     *         given number.
     */
    public List<HighScore> getTopScores(String gameName, int limit) {
        List<HighScore> allScores = new ArrayList<>();

        for (List<HighScore> scores : highScores.values()) {
            for (HighScore score : scores) {
                if (score.getGamename().equals(gameName)) {
                    allScores.add(score);
                }
            }
        }

        // Sort by score value
        Collections.sort(allScores, new Comparator<HighScore>() {
            @Override
            public int compare(HighScore s1, HighScore s2) {
                return Integer.compare(s2.getScore(), s1.getScore());
            }
        });

        // Return top 5 scores
        return allScores.subList(0, Math.min(limit, allScores.size()));
    }

    /**
     * Method to add high score to Map and store it
     * 
     * @param username  Username
     * @param highScore High score to add
     */
    public void addHighScores(String username, HighScore highScore) {
        // Retrieve user's high score
        List<HighScore> userScores = highScores.computeIfAbsent(username, k -> new ArrayList<>());

        // Add new high score to user's set of scores
        userScores.add(highScore);

        // Convert the set to a list to sort it
        List<HighScore> sortedScores = new ArrayList<>(userScores);

        // Sort the scores in descending order by score
        Collections.sort(sortedScores, new Comparator<HighScore>() {
            @Override
            public int compare(HighScore s1, HighScore s2) {
                return Integer.compare(s2.getScore(), s1.getScore());
            }
        });

        // Limits for Blackjack and Snake
        int blackJackLimit = 0;
        int snakeLimit = 0;

        // Iterate through the sorted scores and limit top 5 per game
        List<HighScore> finalScores = new ArrayList<>();

        for (HighScore score : sortedScores) {
            if (score.getGamename().equals("Blackjack")) {
                // Only add to finalScores if the Blackjack limit is less than or equal to 5
                if (blackJackLimit < 5) {
                    finalScores.add(score);
                    blackJackLimit++;
                }
            } else if (score.getGamename().equals("Snake")) {
                // Only add to finalScores if the Snake limit is less than or equal to 5
                if (snakeLimit < 5) {
                    finalScores.add(score);
                    snakeLimit++;
                }
            }
        }

        // Update the user's score list with the top scores for each game
        highScores.put(username, finalScores);

        // Save the updated high scores to file
        saveHighScores();
    }
}
