package com.game.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordHasher {

    /**
     * Method to hash password using SHA-256
     * 
     * @param password User password
     * @return Hashed password
     */
    public static String hashPassword(String password) {
        try {
            // Create MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Hashing
            byte[] hashedBytes = digest.digest(password.getBytes());

            // Encode hashed bytes in Base64
            return Base64.getEncoder().encodeToString(hashedBytes);
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
        String enteredHash = hashPassword(password);
        return storedHash.equals(enteredHash);
    }
}
