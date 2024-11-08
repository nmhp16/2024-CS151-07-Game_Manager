package com.game;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class GameManager extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Login or Create Account Options
        BorderPane loginPage = new BorderPane();

        // Center login page
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10); // Gap between columns
        gridPane.setVgap(10); // Gap between rows

        // Create account element
        Label username = new Label("Username");
        TextField usernameField = new TextField();
        username.setFont(new Font("Georgia", 20));
        usernameField.setPrefWidth(200);

        Label password = new Label("Password");
        PasswordField passwordField = new PasswordField();
        password.setFont(new Font("Georgia", 20));
        passwordField.setPrefWidth(200);

        Button login = new Button("Login");
        login.setPrefWidth(200);
        login.setFont(new Font("Georgia", 20));

        Button createAccount = new Button("Create Account");
        createAccount.setPrefWidth(200);
        createAccount.setFont(new Font("Georgia", 20));

        // Put account element into Grid Pane in format (field, column, row)
        gridPane.add(username, 1, 1);
        gridPane.add(usernameField, 1, 2);
        gridPane.add(password, 1, 3);
        gridPane.add(passwordField, 1, 4);
        gridPane.add(login, 1, 6);
        gridPane.add(createAccount, 1, 7);

        // Create title for login page
        Label title = new Label("Game Manager Login");
        title.setFont(new Font("Georgia", 30));

        // Add element to Border Pane login page
        VBox vbox = new VBox(title, gridPane);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        loginPage.setCenter(vbox);

        // Game Manager Controller to handle on action event
        GameManagerController gameManagerController = new GameManagerController(login, createAccount, usernameField,
                passwordField, primaryStage);

        primaryStage.setTitle("Game Manager");
        primaryStage.setScene(new Scene(loginPage, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}