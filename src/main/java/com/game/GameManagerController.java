package com.game;

import com.game.ui.BlackjackUI;
import com.game.ui.SnakeUI;

import com.game.service.LoginManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameManagerController {
    private Button login;
    private Button createAccount;
    private TextField usernameField;
    private PasswordField passwordField;
    private Stage stage;
    private LoginManager loginManager = new LoginManager();

    public GameManagerController(Button login, Button createAccount, TextField usernameField,
            PasswordField passwordField, Stage stage) {
        this.login = login;
        this.createAccount = createAccount;
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.stage = stage;

        setupEventHandlers();
    }

    /**
     * Helper method to set up event handlers
     */
    private void setupEventHandlers() {
        login.setOnAction(event -> handleLogin());
        createAccount.setOnAction(event -> handleCreateAccount());
    }

    /**
     * Method to handle login event
     */
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (loginManager.login(username, password)) {
            showMainMenu(stage);
        } else {
            showAlert("Login Failed", "Incorrect username or password");
        }
    }

    /**
     * Method to handle create account event
     */
    private void handleCreateAccount() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (loginManager.createAccount(username, password)) {
            showAlert("Account Created", "Account has been created successfully.");
        } else {
            showAlert("Account Creation Failed", "Username already exists.");
        }
    }

    /**
     * Method to display main menu
     * 
     * @param stage Primary stage
     */
    private void showMainMenu(Stage stage) {
        // Main menu - AnchorPane for resizing
        AnchorPane mainMenu = new AnchorPane();

        // Add element to AnchorPane
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        // Elements to add to VBox
        Label menuTitle = new Label("Main Menu");
        menuTitle.setFont(new Font("Georgia", 30));
        menuTitle.setPadding(new Insets(0, 0, 100, 0)); // Top, Right, Bottom, Left

        // Set font size dynamically based on stage width
        menuTitle.styleProperty().bind(stage.widthProperty().divide(15).asString("-fx-font-size: %.0fpx;"));

        // Elements to add to Grid Pane
        Button playBlackjackButton = new Button("Play Blackjack");
        playBlackjackButton.setFont(new Font("Georgia", 20));

        Button playSnakeButton = new Button("Play Snake");
        playSnakeButton.setFont(new Font("Georgia", 20));

        Button backButton = new Button("Go back");
        backButton.setFont(new Font("Georgia", 20));

        // Set button size dynamically based on stage width
        playBlackjackButton.prefWidthProperty().bind(stage.widthProperty().multiply(0.3)); // 30% of the stage width
        playSnakeButton.prefWidthProperty().bind(stage.widthProperty().multiply(0.3)); // 30% of the stage width
        backButton.prefWidthProperty().bind(stage.widthProperty().multiply(0.2)); // 20% of the stage width

        // Bind button height dynamically based on stage height
        playBlackjackButton.prefHeightProperty().bind(stage.heightProperty().multiply(0.1)); // 10% of the stage height
        playSnakeButton.prefHeightProperty().bind(stage.heightProperty().multiply(0.1)); // 10% of the stage height
        backButton.prefHeightProperty().bind(stage.heightProperty().multiply(0.08)); // 8% of the stage height

        // Initialize Grid Pane
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(playBlackjackButton, 1, 2);
        gridPane.add(playSnakeButton, 1, 4);
        gridPane.add(backButton, 1, 6);

        // Put Button in Grid Pane to HBox
        HBox hBox = new HBox();

        // TODO: Add top 5 high scores here
        hBox.getChildren().addAll(gridPane);

        vbox.getChildren().addAll(menuTitle, hBox);

        // Add vbox to AnchorPane
        mainMenu.getChildren().add(vbox);

        // Anchor the VBox to all sides of the AnchorPane to center it
        AnchorPane.setTopAnchor(vbox, 20.0); // Top margin
        AnchorPane.setLeftAnchor(vbox, 20.0); // Left margin
        AnchorPane.setRightAnchor(vbox, 20.0); // Right margin
        AnchorPane.setBottomAnchor(vbox, 20.0); // Bottom margin

        stage.setScene(new Scene(mainMenu, 600, 400));

        // Event handler for "Go back" button
        backButton.setOnAction(event -> {
            showLoginPage(stage); // Revert to login page
        });

        // Event handler for "Play Blackjack" button
        playBlackjackButton.setOnAction(event -> {
            BlackjackUI blackjackGame = new BlackjackUI();
            blackjackGame.start(stage);
        });

        // Event handler fpr "Play Snake" button
        playSnakeButton.setOnAction(event -> {
            SnakeUI snakeGame = new SnakeUI();
            snakeGame.start(stage);
        });
    }

    /**
     * Helper method to display alert for failed login
     * 
     * @param title   Alert title
     * @param message Alert message
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Helper method to show login page
     * 
     * @param stage Primary stage
     */
    private void showLoginPage(Stage stage) {
        GameManager gameManager = new GameManager();
        gameManager.start(stage);
    }
}
