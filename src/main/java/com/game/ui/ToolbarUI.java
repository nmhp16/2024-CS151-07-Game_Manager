package com.game.ui;

import com.game.GameManagerController;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class ToolbarUI extends HBox {
    private Button mainMenuButton;
    private Button signOutButton;

    public ToolbarUI(GameManagerController controller) {
        // Toolbar HBox setting
        this.setSpacing(8);

        // Create "Sign Out" button
        signOutButton = new Button("Sign Out");
        signOutButton.setOnAction(event -> controller.showLoginPage(controller.getStage()));

        // Add "Sign Out" button to toolbar
        signOutButton.setFont(new Font("Georgia", 20));
        this.getChildren().add(signOutButton);

        // Create "Main Menu" button
        mainMenuButton = new Button("Main menu");
        mainMenuButton.setOnAction(event -> controller.showMainMenu(controller.getStage()));

        // Add "Main Menu" button to toolbar
        mainMenuButton.setFont(new Font("Georgia", 20));
        this.getChildren().add(mainMenuButton);

    }
}
