package com.game.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHasher {

    private static final int SALT_LENGTH = 15;

    /**
     * Method to hash password using SHA-256
     * 
     * @param password User password
     * @return Hashed password
     */
    public static String hashPassword(String password) {
        try {
            // Generate random salt
            byte[] salt = new byte[SALT_LENGTH];
            new SecureRandom().nextBytes(salt);

            // Combine the salt and password
            String saltedPassword = password + Base64.getEncoder().encodeToString(salt);

            // Create MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Hashing
            byte[] hashedBytes = digest.digest(saltedPassword.getBytes());

            // Encode hashed bytes in Base64
            String hashedPassword = Base64.getEncoder().encodeToString(hashedBytes);

            return hashedPassword + ":" + Base64.getEncoder().encodeToString(salt);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Method to verify password
     * 
     * @param storedHash Hashed password
     * @param password   Password entered
     * @return True if matches
     *         False otherwise
     */
    public static boolean verifyPassword(String storedHash, String password) {
        try {
            // Extract password hash
            String[] parts = storedHash.split(":");
            String storedPasswordHash = parts[0];

            // Get salt
            byte[] storedSalt = Base64.getDecoder().decode(parts[1]);

            // Combine salt and entered password
            String saltedPassword = password + Base64.getEncoder().encodeToString(storedSalt);

            // Create MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Hashing
            byte[] hashedBytes = digest.digest(saltedPassword.getBytes());

            // Encode hashed bytes in Base64
            String enteredHash = Base64.getEncoder().encodeToString(hashedBytes);

            // Compare entered hash to the stored hash
            return storedPasswordHash.equals(enteredHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error verifying password", e);
        }
    }
}
