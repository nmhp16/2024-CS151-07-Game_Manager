package com.game;

import com.game.ui.LoginPageUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class GameManager extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Game Manager Controller to handle on action event
        GameManagerController gameManagerController = new GameManagerController(primaryStage);

        // Create and set up login page
        LoginPageUI loginPage = new LoginPageUI(gameManagerController);

        primaryStage.setTitle("Game Manager");
        primaryStage.setScene(new Scene(loginPage.getLoginPage(), 600, 400));
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}