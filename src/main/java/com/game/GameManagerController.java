package com.game;

import java.util.List;

import com.game.model.HighScore;
import com.game.service.HighScoresManager;
import com.game.service.LoginManager;
import com.game.ui.BlackjackUI;
import com.game.ui.SnakeUI;
import com.game.ui.ToolbarUI;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameManagerController {
    private Stage stage;
    private LoginManager loginManager = new LoginManager();
    private ToolbarUI toolbar;
    private HighScoresManager highScoresManager = new HighScoresManager();
    public static String username;

    public GameManagerController(Stage stage) {
        this.stage = stage;

        // Initialize toolbar
        this.toolbar = new ToolbarUI(this, stage);
    }

    // Method to get stage
    public Stage getStage() {
        return stage;
    }

    /**
     * Method to handle login event
     */
    public void handleLogin(String username, String password) {

        if (loginManager.login(username, password)) {
            showMainMenu(stage);
        } else {
            showAlert("Login Failed", "Incorrect username or password");
        }
    }

    /**
     * Method to handle create account event
     */
    public void handleCreateAccount(String username, String password) {

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Account Creation Failed", "Username or password cannot be empty.");
            return;
        } else {
            if (loginManager.createAccount(username, password)) {
                showAlert("Account Created", "Account has been created successfully.");
            } else {
                showAlert("Account Creation Failed", "Username already exists.");
            }
        }
    }

    /**
     * Method to display main menu
     * 
     * @param stage Primary stage
     */
    public void showMainMenu(Stage stage) {
        highScoresManager.loadHighScores();

        stage.setTitle("Game Manager");

        // Main menu - AnchorPane for resizing
        AnchorPane mainMenu = new AnchorPane();

        // Add toolbar to main menu
        mainMenu.getChildren().add(toolbar);

        AnchorPane.setTopAnchor(toolbar, 0.0);
        AnchorPane.setLeftAnchor(toolbar, 0.0);
        AnchorPane.setRightAnchor(toolbar, 0.0);

        // Add element to AnchorPane
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        Label menuTitle = createMenuTitle(stage);

        HBox contentBox = createMainContent(stage);

        // Add menu title and HBox to VBox
        vbox.getChildren().addAll(menuTitle, contentBox);

        // Add vbox to AnchorPane
        mainMenu.getChildren().add(vbox);

        // Anchor the VBox to all sides of the AnchorPane to center it
        AnchorPane.setTopAnchor(vbox, 20.0); // Top margin
        AnchorPane.setLeftAnchor(vbox, 20.0); // Left margin
        AnchorPane.setRightAnchor(vbox, 20.0); // Right margin
        AnchorPane.setBottomAnchor(vbox, 20.0); // Bottom margin

        stage.setScene(new Scene(mainMenu, 900, 600));
        stage.centerOnScreen();
    }

    /**
     * Creates a HBox with two VBoxes, one for Blackjack and one for Snake scores.
     * Each VBox contains a title and the top 5 scores with the score label width
     * bound to 20% of the stage width.
     * 
     * @param stage Primary stage
     * @return HBox with two VBoxes containing top 5 scores for Blackjack and Snake
     */
    private VBox createHighScoreBox(Stage stage) {
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER_LEFT);

        Label scoreListTitle = new Label("Top 5 Scores");
        scoreListTitle.setFont(new Font("Georgia", 50));

        HBox scoreList = new HBox(30); // Increased spacing between sections
        scoreList.setAlignment(Pos.TOP_CENTER);

        // VBox for Blackjack scores
        VBox blackjackBox = new VBox(10);
        blackjackBox.setAlignment(Pos.TOP_LEFT);

        Label blackjackTitle = new Label("Blackjack");
        blackjackTitle.setFont(new Font("Georgia", 30));
        blackjackBox.getChildren().add(blackjackTitle);

        List<HighScore> blackjackScores = highScoresManager.getTopScores("Blackjack", 5);
        int i = 1;
        for (HighScore score : blackjackScores) {

            Label scoreLabel = new Label(i + ". " + score.getUsername() + ": " + score.getScore());
            scoreLabel.setFont(new Font("Georgia", 20));
            scoreLabel.prefWidthProperty().bind(stage.widthProperty().multiply(0.2)); // 20% of stage width
            blackjackBox.getChildren().add(scoreLabel);

            i++;
        }

        // VBox for Snake scores
        VBox snakeBox = new VBox(10);
        snakeBox.setAlignment(Pos.TOP_LEFT);

        Label snakeTitle = new Label("Snake");
        snakeTitle.setFont(new Font("Georgia", 30));
        snakeBox.getChildren().add(snakeTitle);

        List<HighScore> snakeScores = highScoresManager.getTopScores("Snake", 5);
        i = 1;

        for (HighScore score : snakeScores) {

            Label scoreLabel = new Label(i + ". " + score.getUsername() + ": " + score.getScore());
            scoreLabel.setFont(new Font("Georgia", 20));
            scoreLabel.prefWidthProperty().bind(stage.widthProperty().multiply(0.2)); // 20% of stage width
            snakeBox.getChildren().add(scoreLabel);
            i++;
        }

        // Add both VBoxes to the HBox
        scoreList.getChildren().addAll(blackjackBox, snakeBox);

        vbox.getChildren().addAll(scoreListTitle, scoreList);

        return vbox;
    }

    /**
     * Creates a Label for the main menu title, dynamically scaling it according to
     * the size of the stage.
     * The font size is calculated based on both the width and the height of the
     * stage.
     * The padding is also dynamically updated to ensure the title is centered
     * vertically.
     * 
     * @param stage The primary stage of the application
     * @return The Label for the main menu title
     */
    private Label createMenuTitle(Stage stage) {
        // Elements to add to VBox
        Label menuTitle = new Label("Main Menu");
        menuTitle.setFont(new Font("Georgia", 30));

        // Calculate font size based on both width and height
        DoubleBinding fontSizeBinding = stage.widthProperty().multiply(0.05)
                .add(stage.heightProperty().multiply(0.1))
                .divide(2);

        // Bind the style property to this combined font size
        menuTitle.styleProperty().bind(fontSizeBinding.asString("-fx-font-family: 'Georgia'; -fx-font-size: %.0fpx;"));

        // Dynamically scale padding with screen size
        menuTitle.paddingProperty().bind(
                Bindings.createObjectBinding(() -> {
                    // Increase Bottom Padding as height increases, up to 200
                    double BottomPadding = Math.min(1 + stage.heightProperty().get() / 5, 200);

                    return new Insets(0, 0, BottomPadding, 0);
                }, stage.heightProperty()));

        return menuTitle;
    }

    /**
     * Creates an HBox containing a GridPane with options to play Blackjack or Snake
     * and a VBox containing the top 5 high scores for both games. The spacing
     * between
     * the elements is dynamically scaled based on the stage width.
     * 
     * @param stage The stage to bind the spacing to.
     * @return The HBox containing the game options and high scores.
     */
    private HBox createMainContent(Stage stage) {
        HBox hBox = new HBox();

        // Dynamically scale spacing with window width
        DoubleBinding spacingBinding = stage.widthProperty().multiply(0.2); // 20% of the width
        hBox.spacingProperty().bind(spacingBinding);

        GridPane gameOptionsBox = createGameOptionsBox(stage);

        VBox scoreList = createHighScoreBox(stage);

        // Add game options and score list to HBox
        hBox.getChildren().addAll(gameOptionsBox, scoreList);

        return hBox;
    }

    /**
     * Creates a GridPane containing buttons to play Blackjack and Snake,
     * as well as a label to identify the options.
     * 
     * @param stage The stage to bind the button sizes to.
     * @return The GridPane containing the game options.
     */
    private GridPane createGameOptionsBox(Stage stage) {
        // Elements to add to Grid Pane
        Label gameOptionsLabel = new Label("Select a Game");
        gameOptionsLabel.setFont(new Font("Georgia", 25));

        Button playBlackjackButton = new Button("Play Blackjack");
        playBlackjackButton.setFont(new Font("Georgia", 20));

        Button playSnakeButton = new Button("Play Snake");
        playSnakeButton.setFont(new Font("Georgia", 20));

        // Set button size dynamically based on stage width
        playBlackjackButton.prefWidthProperty().bind(stage.widthProperty().multiply(0.3)); // 30% of the stage width
        playSnakeButton.prefWidthProperty().bind(stage.widthProperty().multiply(0.3)); // 30% of the stage width

        // Bind button height dynamically based on stage height
        playBlackjackButton.prefHeightProperty().bind(stage.heightProperty().multiply(0.1)); // 10% of the stage height
        playSnakeButton.prefHeightProperty().bind(stage.heightProperty().multiply(0.1)); // 10% of the stage height

        // Initialize Grid Pane
        GridPane gameOptionsBox = new GridPane();
        gameOptionsBox.setAlignment(Pos.CENTER);
        gameOptionsBox.setHgap(10);
        gameOptionsBox.setVgap(10);

        gameOptionsBox.add(gameOptionsLabel, 1, 0);
        gameOptionsBox.add(playBlackjackButton, 1, 2);
        gameOptionsBox.add(playSnakeButton, 1, 4);

        setGameAction(playBlackjackButton, playSnakeButton);

        return gameOptionsBox;
    }

    /**
     * Sets the game action event handlers for the provided buttons.
     * 
     * @param playBlackjackButton Button to start the Blackjack game.
     * @param playSnakeButton     Button to display options for the Snake game.
     * 
     *                            The "Play Blackjack" button initializes and starts
     *                            a new Blackjack game.
     *                            The "Play Snake" button presents an option to play
     *                            the classic mode of Snake,
     *                            initializing and starting the game upon selection.
     */
    private void setGameAction(Button playBlackjackButton, Button playSnakeButton) {
        // Event handler for "Play Blackjack" button
        playBlackjackButton.setOnAction(event -> {
            BlackjackUI blackjackGame = new BlackjackUI(username);
            blackjackGame.start(stage);
        });

        // Event handler fpr "Play Snake" button
        playSnakeButton.setOnAction(event -> {
            VBox optionsBox = new VBox(20);
            Button playClassic = new Button("Play Classic Mode");
            playClassic.setFont(new Font("Georgia", 20));
            playClassic.setPrefWidth(300);

            // Set button size dynamically based on stage width
            playClassic.prefWidthProperty().bind(stage.widthProperty().multiply(0.3)); // 30% of the stage width

            // Bind button height dynamically based on stage height
            playClassic.prefHeightProperty().bind(stage.heightProperty().multiply(0.1)); // 10% of the stage height

            optionsBox.getChildren().addAll(playClassic);

            AnchorPane optionPane = new AnchorPane();

            optionPane.getChildren().addAll(toolbar, optionsBox);
            AnchorPane.setTopAnchor(toolbar, 0.0); // Top margin
            AnchorPane.setLeftAnchor(toolbar, 0.0); // Left margin
            AnchorPane.setRightAnchor(toolbar, 0.0); // Right margin

            AnchorPane.setTopAnchor(optionsBox, 20.0); // Top margin
            AnchorPane.setLeftAnchor(optionsBox, 20.0); // Left margin
            AnchorPane.setRightAnchor(optionsBox, 20.0); // Right margin
            AnchorPane.setBottomAnchor(optionsBox, 20.0); // Bottom margin

            optionsBox.setAlignment(Pos.CENTER);
            stage.setScene(new Scene(optionPane, 700, 400));

            playClassic.setOnAction(e -> {
                SnakeUI snakeGame = new SnakeUI(username);
                snakeGame.start(stage);
            });

        });
    }

    /**
     * Helper method to display alert for failed login
     * 
     * @param title   Alert title
     * @param message Alert message
     */
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Method to show login page
     * 
     * @param stage Primary stage
     */
    public void showLoginPage(Stage stage) {
        GameManager gameManager = new GameManager();
        gameManager.start(stage);
    }
}
