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
            GameManagerController.isGameRunning = false;
            controller.showLoginPage(stage);
        });

        // Add "Sign Out" button to toolbar
        signOutButton.setFont(new Font("Georgia", 20));
        signOutButton.setPrefWidth(150);
        this.getItems().add(signOutButton);

        // Create "Main Menu" button
        mainMenuButton = new Button("Main menu");
        mainMenuButton.setOnAction(event -> {
            GameManagerController.isGameRunning = false;
            controller.showMainMenu(stage);
        });

        // Add "Main Menu" button to toolbar
        mainMenuButton.setFont(new Font("Georgia", 20));
        mainMenuButton.setPrefWidth(150);
        this.getItems().add(mainMenuButton);

        this.setPrefHeight(30);

    }
}
