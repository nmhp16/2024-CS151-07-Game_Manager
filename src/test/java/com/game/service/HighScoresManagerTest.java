package com.game.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;

import com.game.model.HighScore;

public class HighScoresManagerTest {
    private HighScoresManager manager;
    private final String HIGH_SCORE_FILE = "high_scores.txt";
    private final String USER_ACCOUNT_FILE = "user_accounts.txt";

    @BeforeEach
    public void setUp() throws IOException {
        // Ensure fresh setup
        cleanUpFiles();
        manager = new HighScoresManager();
    }

    @AfterEach
    public void tearDown() throws IOException {
        cleanUpFiles();
    }

    private void cleanUpFiles() throws IOException {
        new File(HIGH_SCORE_FILE).delete();
        new File(USER_ACCOUNT_FILE).delete();
    }

    @Test
    public void testInitializeDefaultUser_createsDefaultAccountIfNotExist() {
        assertTrue(new File(USER_ACCOUNT_FILE).exists(), "Default user account file should be created");
        assertTrue(manager.getTopScores("Blackjack", 5).stream().anyMatch(s -> s.getUsername().equals("default")),
                "Default high score for 'Blackjack' should be created for 'default' user");
        assertTrue(manager.getTopScores("Snake", 5).stream().anyMatch(s -> s.getUsername().equals("default")),
                "Default high score for 'Snake' should be created for 'default' user");
    }

    @Test
    public void testLoadHighScores_loadsFromFileCorrectly() throws IOException {
        // Manually create a high score file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE))) {
            writer.write("testUser,1200,Blackjack\n");
            writer.write("testUser,1100,Snake\n");
        }

        manager.loadHighScores();

        List<HighScore> blackjackScores = manager.getTopScores("Blackjack", 5);
        assertTrue(blackjackScores.stream().anyMatch(s -> s.getUsername().equals("testUser") && s.getScore() == 1200),
                "Should load 'testUser' high score for 'Blackjack'");

        List<HighScore> snakeScores = manager.getTopScores("Snake", 5);
        assertTrue(snakeScores.stream().anyMatch(s -> s.getUsername().equals("testUser") && s.getScore() == 1100),
                "Should load 'testUser' high score for 'Snake'");
    }

    @Test
    public void testSaveHighScores_savesCorrectly() throws IOException {
        HighScore newScore = new HighScore("newUser", 1300, "Blackjack");
        manager.addHighScores("newUser", newScore);

        try (BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORE_FILE))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("newUser") && line.contains("1300") && line.contains("Blackjack")) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, "Saved high score for 'newUser' in file");
        }
    }

    @Test
    public void testAddHighScores_maintainsTopFiveScores() {
        String username = "topUser";
        for (int i = 1; i <= 7; i++) {
            manager.addHighScores(username, new HighScore(username, 1000 + i * 100, "Blackjack"));
        }

        List<HighScore> scores = manager.getTopScores("Blackjack", 5);
        assertEquals(5, scores.size(), "Should only keep top 5 scores");

        // Check that scores are ordered and only top 5 highest are kept
        assertTrue(scores.get(0).getScore() >= scores.get(4).getScore(), "Scores should be in descending order");
        assertEquals(1700, scores.get(0).getScore(), "Top score should be 1700");
        assertEquals(1300, scores.get(4).getScore(), "Lowest kept score should be 1300");
    }

    @Test
    public void testGetTopScores_returnsCorrectLimit() {
        manager.addHighScores("user1", new HighScore("user1", 1000, "Blackjack"));
        manager.addHighScores("user2", new HighScore("user2", 1200, "Blackjack"));
        manager.addHighScores("user3", new HighScore("user3", 1100, "Blackjack"));

        List<HighScore> scores = manager.getTopScores("Blackjack", 2);
        assertEquals(2, scores.size(), "Should return only top 2 scores");
        assertEquals(1200, scores.get(0).getScore(), "Highest score should be 1200");
    }

    @Test
    public void testCreateDefaultScores_addsDefaultsIfNoneExists() {
        Map<String, String> accounts = new HashMap<>();
        accounts.put("user1", "password1");
        manager.createDefaultScores(accounts);

        List<HighScore> scores = manager.getTopScores("Blackjack", 5);
        assertTrue(scores.stream().anyMatch(s -> s.getUsername().equals("user1") && s.getScore() == 1000),
                "Default score should be added for user1");
    }

    @Test
    public void testAddHighScores_updatesExistingScoreSet() {
        manager.addHighScores("user", new HighScore("user", 800, "Snake"));
        manager.addHighScores("user", new HighScore("user", 900, "Snake"));

        List<HighScore> scores = manager.getTopScores("Snake", 5);
        assertEquals(3, scores.size(), "User should have 3 scores for Snake"); // From default + 2 new
        assertTrue(scores.stream().anyMatch(s -> s.getScore() == 900), "Score 900 should be present");
    }
}
