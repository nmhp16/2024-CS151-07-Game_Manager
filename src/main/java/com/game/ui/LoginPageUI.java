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
import javafx.scene.paint.Color;
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

        // Add icon for username and password
        Label usernameIcon = new Label("👤");
        usernameIcon.setFont(new Font(24));
        usernameIcon.setTextFill(Color.WHITE);

        Label passwordIcon = new Label("🔒");
        passwordIcon.setFont(new Font(24));
        passwordIcon.setTextFill(Color.WHITE);

        // Create account element
        Label username = new Label("Username");
        username.setFont(new Font("Georgia", 20));
        username.setTextFill(Color.WHITE);
        usernameField.setPrefWidth(200);

        Label password = new Label("Password");
        password.setFont(new Font("Georgia", 20));
        password.setTextFill(Color.WHITE);
        passwordField.setPrefWidth(200);

        // Styling
        String buttonStyle = "-fx-background-color: #4ca1af; -fx-text-fill: white; -fx-background-radius: 10px;";
        String fieldStyle = "-fx-background-color: white; -fx-border-color: lightgrey; -fx-border-radius: 10px; -fx-background-radius: 10px;";
        usernameField.setStyle(fieldStyle);
        passwordField.setStyle(fieldStyle);

        // Login Button
        loginButton.setPrefWidth(200);
        loginButton.setFont(new Font("Georgia", 20));
        loginButton.setStyle(buttonStyle);
        loginButton.setPrefSize(200, 40);

        // Hover effect
        loginButton.setOnMouseEntered(
                event -> loginButton
                        .setStyle("-fx-background-color: #3b8a9a; -fx-text-fill: white; -fx-background-radius: 10px;"));
        loginButton.setOnMouseExited(
                event -> loginButton.setStyle(buttonStyle));

        // Hover effect when focused
        loginButton.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                loginButton.setStyle(
                        "-fx-background-color: #3b8a9a; -fx-text-fill: white; -fx-background-radius: 10px;");
            } else {
                loginButton
                        .setStyle(buttonStyle);
            }
        });

        // Create Account Button
        createAccountButton.setPrefWidth(200);
        createAccountButton.setFont(new Font("Georgia", 20));
        createAccountButton.setStyle(buttonStyle);
        createAccountButton.setPrefSize(200, 40);

        createAccountButton.setOnMouseEntered(
                event -> createAccountButton
                        .setStyle("-fx-background-color: #3b8a9a; -fx-text-fill: white; -fx-background-radius: 10px;"));
        createAccountButton.setOnMouseExited(
                event -> createAccountButton.setStyle(buttonStyle));

        // Hover effect when focused
        createAccountButton.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                createAccountButton.setStyle(
                        "-fx-background-color: #3b8a9a; -fx-text-fill: white; -fx-background-radius: 10px;");
            } else {
                createAccountButton
                        .setStyle(buttonStyle);
            }
        });

        // Put account element into Grid Pane in format (field, column, row)
        gridPane.add(username, 1, 1);
        gridPane.add(usernameIcon, 0, 2);
        gridPane.add(usernameField, 1, 2);
        gridPane.add(password, 1, 3);
        gridPane.add(passwordIcon, 0, 4);
        gridPane.add(passwordField, 1, 4);
        gridPane.add(loginButton, 1, 6);
        gridPane.add(createAccountButton, 1, 7);

        // Create title for login page
        Label title = new Label("Game Manager Login");
        title.setFont(new Font("Georgia", 30));
        title.setTextFill(Color.WHITE);

        // Add element to Border Pane login page
        VBox vbox = new VBox(title, gridPane);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        String backgroundStyle = "-fx-background-color: linear-gradient(to bottom right, #2c3e50, #5bc0de);";
        loginPage.setStyle(backgroundStyle);
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
