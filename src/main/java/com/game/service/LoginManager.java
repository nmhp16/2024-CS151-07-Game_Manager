package com.game.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class LoginManager {
    private HighScoresManager highScoresManager = new HighScoresManager();
    private Map<String, String> accounts = new HashMap<>();

    /**
     * Default Constructor
     */
    public LoginManager() {
        loadAccounts();
        highScoresManager.createDefaultScores(accounts);
    }

    /**
     * Helper method to load existing account and store in Map
     */
    private void loadAccounts() {
        try (BufferedReader reader = new BufferedReader(new FileReader("user_accounts.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 2) {
                    String username = parts[0];
                    String hashedPassword = parts[1]; // Store hashed password
                    accounts.put(username, hashedPassword);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }

    }

    /**
     * Method to create account
     * 
     * @param username User name
     * @param password Password
     * @return False if exist
     *         True otherwise
     */
    public boolean createAccount(String username, String password) {
        // Check if username already exists
        if (accounts.containsKey(username)) {
            return false;
        }

        // Hash password before storing it
        String hashedPassword = PasswordHasher.hashPassword(password);

        accounts.put(username, hashedPassword);
        highScoresManager.createDefaultScores(accounts);
        saveAccounts();
        return true;
    }

    /**
     * Method to login with username and password
     * 
     * @param username Username
     * @param password Password
     * @return True if exist
     *         False otherwise
     */
    public boolean login(String username, String password) {
        // Check if username exist in Map
        if (accounts.containsKey(username)) {
            // Retrieve password
            String storedHash = accounts.get(username);

            if (PasswordHasher.verifyPassword(storedHash, password)) {
                return true; // Match
            }
        }
        return false; // Doesn't exist username or password doesn't match
    }

    /**
     * Helper method to save account into txt file
     */
    private void saveAccounts() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("user_accounts.txt"))) {
            for (Map.Entry<String, String> entry : accounts.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }

        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    // Getters
    public Map<String, String> getAccounts() {
        return accounts;
    }

    // Setters
    public void addAccount(String username, String password) {
        accounts.put(username, password);
        saveAccounts();
    }
}
