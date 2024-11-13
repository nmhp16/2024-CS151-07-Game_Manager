package com.game.ui;

import com.game.GameManagerController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class LoginPageUI {
    private BorderPane loginPage;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button createAccountButton;

    public LoginPageUI(GameManagerController controller) {
        // Initialize UI Components
        loginPage = new BorderPane();
        usernameField = new TextField();
        passwordField = new PasswordField();
        loginButton = new Button("Login");
        createAccountButton = new Button("Create Account");

        setupUI(controller);
    }

    // Getters
    public BorderPane getLoginPage() {
        return loginPage;
    }

    private void setupUI(GameManagerController controller) {
        // Center login page
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10); // Gap between columns
        gridPane.setVgap(10); // Gap between rows

        // Create account element
        Label username = new Label("Username");
        username.setFont(new Font("Georgia", 20));
        usernameField.setPrefWidth(200);

        Label password = new Label("Password");
        password.setFont(new Font("Georgia", 20));
        passwordField.setPrefWidth(200);

        // Login Button
        loginButton.setPrefWidth(200);
        loginButton.setFont(new Font("Georgia", 20));

        // Create Account Button
        createAccountButton.setPrefWidth(200);
        createAccountButton.setFont(new Font("Georgia", 20));

        // Put account element into Grid Pane in format (field, column, row)
        gridPane.add(username, 1, 1);
        gridPane.add(usernameField, 1, 2);
        gridPane.add(password, 1, 3);
        gridPane.add(passwordField, 1, 4);
        gridPane.add(loginButton, 1, 6);
        gridPane.add(createAccountButton, 1, 7);

        // Create title for login page
        Label title = new Label("Game Manager Login");
        title.setFont(new Font("Georgia", 30));

        // Add element to Border Pane login page
        VBox vbox = new VBox(title, gridPane);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        loginPage.setCenter(vbox);

        // Set up button actions with controller
        loginButton.setOnAction(event -> {
            GameManagerController.username = usernameField.getText(); // Set username controller
            controller.handleLogin(usernameField.getText(), passwordField.getText());
        });
        createAccountButton.setOnAction(event -> {
            controller.handleCreateAccount(usernameField.getText(), passwordField.getText());
        });
    }
}
