package com.game.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginManagerTest {
    private LoginManager manager;
    private final String USER_ACCOUNT_FILE = "user_accounts.txt";

    @BeforeEach
    public void setUp() throws IOException {
        // Ensure fresh setup
        new File(USER_ACCOUNT_FILE).delete();
        manager = new LoginManager();
    }

    @AfterEach
    public void tearDown() throws IOException {
        new File(USER_ACCOUNT_FILE).delete();
    }

    @Test
    public void testSaveAccounts_savesCorrectly() throws IOException {
        manager.addAccount("default", "password");
        assertTrue(new File(USER_ACCOUNT_FILE).exists(), "User account file should be created");
    }

    @Test
    public void testLoadAccounts_loadsFromFileCorrectly() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_ACCOUNT_FILE))) {
            writer.write("default,password\n");
        }

        manager = new LoginManager();
        assertTrue(manager.getAccounts().containsKey("default"), "Default account should be loaded");
    }

    @Test
    public void testCreateAccount_createsAccount() throws IOException {
        assertTrue(manager.createAccount("default1", "password1"), "Account should be created");
        assertTrue(manager.getAccounts().containsKey("default1"), "Account should be added to Map");
    }

    @Test
    public void testLogin_success() {
        assertTrue(manager.login("default", "password"), "Login should be successful");
    }

    @Test
    public void testLogin_failure() {
        assertFalse(manager.login("default", "wrongPassword"), "Login should fail");
    }
}
