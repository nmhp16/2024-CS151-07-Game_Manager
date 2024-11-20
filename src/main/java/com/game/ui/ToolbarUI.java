package com.game.ui;

import com.game.GameManagerController;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ToolbarUI extends ToolBar {
    private Button mainMenuButton;
    private Button signOutButton;

    public ToolbarUI(GameManagerController controller, Stage stage) {

        // Create "Sign Out" button
        signOutButton = new Button("Sign Out");
        signOutButton.setOnAction(event -> {
            if (BlackjackUI.isBlackjackRunning) {
                BlackjackUI.isBlackjackRunning = false;
                controller.showAlert("Game not saved", "Game score not recorded!");
            }
            if (SnakeUI.isSnakeRunning) {
                SnakeUI.isSnakeRunning = false;
                controller.showAlert("Game not saved", "Game score not recorded!");
            }
            controller.showLoginPage(stage);
        });

        // Add "Sign Out" button to toolbar
        signOutButton.setFont(new Font("Georgia", 20));
        signOutButton.setPrefWidth(150);
        this.getItems().add(signOutButton);

        // Create "Main Menu" button
        mainMenuButton = new Button("Main menu");
        mainMenuButton.setOnAction(event -> {
            if (BlackjackUI.isBlackjackRunning) {
                BlackjackUI.isBlackjackRunning = false;

                controller.showAlert("Game not saved", "Game score not recorded!");
            }
            if (SnakeUI.isSnakeRunning) {
                SnakeUI.isSnakeRunning = false;
                controller.showAlert("Game not saved", "Game score not recorded!");
            }

            controller.showMainMenu(stage);
        });

        // Add "Main Menu" button to toolbar
        mainMenuButton.setFont(new Font("Georgia", 20));
        mainMenuButton.setPrefWidth(150);

        // If snake is running don't focus toolbar
        if (SnakeUI.isSnakeRunning) {
            // Ensure toolbar and its children are not focus traversable
            this.setFocusTraversable(false);
            signOutButton.setFocusTraversable(false);
            mainMenuButton.setFocusTraversable(false);
        }
        // Set item for toolbar
        this.getItems().add(mainMenuButton);
        this.setPrefHeight(30);

    }
}
