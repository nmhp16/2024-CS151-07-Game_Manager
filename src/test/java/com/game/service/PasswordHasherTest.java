package com.game.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PasswordHasherTest {
    private static final String PASSWORD = "password";

    @Test
    public void testHashPassword() {
        String hashedPassword = PasswordHasher.hashPassword(PASSWORD);
        assertFalse(hashedPassword.contains(PASSWORD));
    }

    @Test
    public void testVerifyPassword() {
        String hashedPassword = PasswordHasher.hashPassword(PASSWORD);
        assertTrue(PasswordHasher.verifyPassword(hashedPassword, PASSWORD));
    }

    @Test
    public void testVerifyPasswordWithIncorrectPassword() {
        String hashedPassword = PasswordHasher.hashPassword(PASSWORD);
        assertFalse(PasswordHasher.verifyPassword(hashedPassword, "incorrectPassword"));
    }

    @Test
    public void testVerifyPasswordWithIncorrectHash() {
        assertFalse(PasswordHasher.verifyPassword("incorrectHash", PASSWORD));
    }

    @Test
    public void testVerifyPasswordWithIncorrectPasswordAndHash() {
        assertFalse(PasswordHasher.verifyPassword("incorrectHash", "incorrectPassword"));
    }

    @Test
    public void testVerifyPasswordWithNullHash() {
        assertFalse(PasswordHasher.verifyPassword(null, PASSWORD));
    }

}
