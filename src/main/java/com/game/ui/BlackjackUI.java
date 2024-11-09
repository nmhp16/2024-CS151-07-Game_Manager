package com.game.ui;

import com.game.GameManagerController;
import com.game.model.Blackjack.BlackjackGame;
import com.game.model.Blackjack.Card;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

// TODO: Complete Blackjack game UI
public class BlackjackUI extends Application {
    private TextArea loadStateField;
    private Button startButton, loadButton;
    private BlackjackGame game;
    private GameManagerController gameManagerController;
    private ToolbarUI toolbar;
    private String username;

    /**
     * Overloaded Constructor
     * 
     * @param username Username
     */
    public BlackjackUI(String username) {
        this.username = username;
    }

    @Override
    public void start(Stage primaryStage) {
        gameManagerController = new GameManagerController(primaryStage);
        game = new BlackjackGame(username);
        toolbar = new ToolbarUI(gameManagerController, primaryStage);

        // Main root
        AnchorPane blackjackGame = new AnchorPane();

        // VBox Setting
        VBox gameBox = new VBox(10);
        gameBox.setAlignment(Pos.CENTER);
        gameBox.setPadding(new Insets(10));

        // Anchor the VBox to all sides of the AnchorPane to center it
        AnchorPane.setTopAnchor(gameBox, 20.0); // Top margin
        AnchorPane.setLeftAnchor(gameBox, 20.0); // Left margin
        AnchorPane.setRightAnchor(gameBox, 20.0); // Right margin
        AnchorPane.setBottomAnchor(gameBox, 20.0); // Bottom margin

        // Create game title
        Label gameTitle = new Label("Blackjack Game");
        gameTitle.setFont(new Font("Georgia", 30));

        startButton = new Button("Start new game");
        startButton.setFont(new Font("Georgia", 20));
        startButton.setPrefWidth(300);

        loadButton = new Button("Load saved game");
        loadButton.setFont(new Font("Georgia", 20));
        loadButton.setPrefWidth(300);

        // Add event handlers for start and load button
        startButton.setOnAction(event -> startNewGame(primaryStage));
        loadButton.setOnAction(event -> showLoadScene(primaryStage));

        // Add elements to gameBox VBox
        gameBox.getChildren().addAll(gameTitle, startButton, loadButton);

        blackjackGame.getChildren().addAll(toolbar, gameBox);

        primaryStage.setTitle("Blackjack Game");
        primaryStage.setScene(new Scene(blackjackGame, 600, 400));
        primaryStage.show();

    }

    /**
     * Helper method to start blackjack game
     * 
     * @param stage Current primary stage
     */
    private void startNewGame(Stage stage) {
        game.startNewRound();
        updateUI(stage);
    }

    /**
     * Method to update UI with current game state
     * 
     * @param stage Current primary stage
     */
    private void updateUI(Stage stage) {
        // Create the labels
        Label turnLabel = new Label("Current Turn: " + game.getCurrentPlayer().getName());
        turnLabel.setFont(new Font("Georgia", 20));

        // Create balance labels
        Label userBalanceLabel = new Label("Your Balance: $" + game.getHumanPlayer().getBalance());
        userBalanceLabel.setFont(new Font("Georgia", 20));

        Label player1BalanceLabel = new Label("Player 1's Balance: $" + game.getPlayer1().getBalance());
        player1BalanceLabel.setFont(new Font("Georgia", 20));

        Label player2BalanceLabel = new Label("Player 2's Balance: $" + game.getPlayer2().getBalance());
        player2BalanceLabel.setFont(new Font("Georgia", 20));

        Label dealerBalanceLabel = new Label("Dealer Balance: $" + game.getDealer().getBalance());
        dealerBalanceLabel.setFont(new Font("Georgia", 20));

        // Create a VBox to hold the labels
        VBox balanceBox = new VBox(10);
        balanceBox.setAlignment(Pos.CENTER);
        balanceBox.getChildren().addAll(turnLabel, userBalanceLabel, player1BalanceLabel, player2BalanceLabel,
                dealerBalanceLabel);

        // Display cards
        updateCardsDisplay(balanceBox);

        // Set up the main layout
        AnchorPane blackjackGame = new AnchorPane();
        AnchorPane.setTopAnchor(balanceBox, 20.0);
        AnchorPane.setLeftAnchor(balanceBox, 20.0);
        AnchorPane.setRightAnchor(balanceBox, 20.0);
        AnchorPane.setBottomAnchor(balanceBox, 20.0);

        // Add balanceBox to the layout
        blackjackGame.getChildren().add(balanceBox);

        // Set the new scene and show it
        Scene newScene = new Scene(blackjackGame, 600, 400);
        stage.setScene(newScene);
        stage.show();
    }

    private void updateCardsDisplay(VBox balanceBox) {
        // Create labels or images for the cards in the player's hand
        HBox playerCardsBox = new HBox(10);
        playerCardsBox.setAlignment(Pos.CENTER);

        for (Card card : game.getCurrentPlayer().getHand()) {
            // Displaying card name as an example
            Label cardLabel = new Label(card.toString());
            cardLabel.setFont(new Font("Georgia", 20));
            playerCardsBox.getChildren().add(cardLabel);
        }

        // Create labels or images for the cards in the dealer's hand
        HBox dealerCardsBox = new HBox(10);
        dealerCardsBox.setAlignment(Pos.CENTER);

        for (Card card : game.getDealer().getHand()) {
            Label cardLabel = new Label(card.toString());
            cardLabel.setFont(new Font("Georgia", 20));
            dealerCardsBox.getChildren().add(cardLabel);
        }

        // Create labels or images for the cards in the player1's hand
        HBox player1CardsBox = new HBox(10);
        player1CardsBox.setAlignment(Pos.CENTER);

        for (Card card : game.getPlayer1().getHand()) {
            Label cardLabel = new Label(card.toString());
            cardLabel.setFont(new Font("Georgia", 20));
            player1CardsBox.getChildren().add(cardLabel);
        }

        // Create labels or images for the cards in the player2's hand
        HBox player2CardsBox = new HBox(10);
        player2CardsBox.setAlignment(Pos.CENTER);

        for (Card card : game.getPlayer2().getHand()) {
            Label cardLabel = new Label(card.toString());
            cardLabel.setFont(new Font("Georgia", 20));
            player2CardsBox.getChildren().add(cardLabel);
        }

        // Add the card displays to the balanceBox or create a new VBox for them
        VBox gameDetailsBox = new VBox(20);
        gameDetailsBox.setAlignment(Pos.CENTER);
        gameDetailsBox.getChildren().addAll(playerCardsBox, dealerCardsBox, player1CardsBox, player2CardsBox);

        balanceBox.getChildren().add(gameDetailsBox);
    }

    /**
     * Helper method to display load saved state scene
     * 
     * @param stage Current primaryStage
     */
    private void showLoadScene(Stage stage) {
        // Layout for dialog
        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(20));
        dialogVBox.setAlignment(Pos.CENTER);

        // Label to guide user
        Label instructionLabel = new Label("Please paste saved game state below:");
        instructionLabel.setFont(new Font("Georgia", 30));

        // Create text field
        loadStateField = new TextArea();
        loadStateField.setPromptText("Paste text here");
        loadStateField.setFont(new Font("Georgia", 20));

        // Button to Load game state
        Button loadGameButton = new Button("Load");
        loadGameButton.setFont(new Font("Georgia", 20));
        loadGameButton.setPrefWidth(100);

        // Event handler for "Load Game" button
        loadGameButton.setOnAction(event -> {
            String saveStateString = loadStateField.getText();

            if (!saveStateString.isEmpty()) {
                game.loadGameState(saveStateString);
                updateUI(stage);
            } else {
                gameManagerController.showAlert("Invalid Input", "Please enter a valid saved state string.");
            }
        });

        // Button to cancel loading
        Button cancelButton = new Button("Cancel");
        cancelButton.setFont(new Font("Georgia", 20));
        cancelButton.setPrefWidth(100);
        // Event handler for "Cancel" button
        cancelButton.setOnAction(event -> {
            BlackjackUI blackjackGame = new BlackjackUI(username);
            blackjackGame.start(stage);
        });

        // Add elements to dialog
        dialogVBox.getChildren().addAll(instructionLabel, loadStateField, loadGameButton, cancelButton);

        // Add dialog VBox to current stage
        stage.setScene(new Scene(dialogVBox, 600, 400));
        stage.setTitle("Load saved state");
        stage.show();
    }

}
