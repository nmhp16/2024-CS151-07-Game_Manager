package com.game.ui;

import java.util.List;

import com.game.GameManagerController;
import com.game.model.Blackjack.BlackjackGame;
import com.game.model.Blackjack.Card;
import com.game.model.Blackjack.HumanPlayer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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

    @Override
    public void start(Stage primaryStage) {
        gameManagerController = new GameManagerController(primaryStage);
        game = new BlackjackGame();
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

        // Create a VBox to hold the labels
        BorderPane gameBox = new BorderPane();
        BorderPane playerInfo = new BorderPane();
        playerInfo.setPadding(new Insets(20));

        // VBox for each player
        VBox userBox = new VBox(10);
        VBox player1Box = new VBox(10);
        VBox player2Box = new VBox(10);
        VBox dealerBox = new VBox(10);

        // Align each player VBox Center
        userBox.setAlignment(Pos.CENTER);
        player1Box.setAlignment(Pos.CENTER);
        player2Box.setAlignment(Pos.CENTER);
        dealerBox.setAlignment(Pos.CENTER);

        // Add balance to each user VBox
        userBox.getChildren().add(userBalanceLabel);
        player1Box.getChildren().add(player1BalanceLabel);
        player2Box.getChildren().add(player2BalanceLabel);

        // Highlight current player VBox
        highlightPlayerBox(game, dealerBox, userBox, player1Box, player2Box);

        // Add dealer player name
        Label dealerLabel = new Label("Dealer");
        dealerLabel.setFont(new Font("Georgia", 20));
        dealerBox.getChildren().add(dealerLabel);

        // Display cards
        updateCardsDisplay(dealerBox, userBox, player1Box, player2Box);

        // Set each player box to BorderPane
        playerInfo.setBottom(userBox);
        playerInfo.setRight(player1Box);
        playerInfo.setLeft(player2Box);
        playerInfo.setTop(dealerBox);

        // Elements to BorderPane gameBox
        gameBox.setCenter(playerInfo); // Player session

        // HBox for game action
        HBox gameActionBox = new HBox(30);
        gameActionBox.setAlignment(Pos.CENTER);

        HBox turnAndAction = new HBox(20);
        turnAndAction.getChildren().add(turnLabel); // Add turn indicator

        // Show User Action Box if user turn
        if (game.getCurrentPlayer() == game.getHumanPlayer()) {
            userAction(gameActionBox, stage, game);
        }

        // Add action box to turn and action box
        turnAndAction.getChildren().add(gameActionBox);

        // Set gameActionBox, at the bottom of game screen
        gameBox.setBottom(turnAndAction);

        // Set up the main layout
        AnchorPane blackjackGame = new AnchorPane();
        AnchorPane.setTopAnchor(gameBox, 20.0);
        AnchorPane.setLeftAnchor(gameBox, 20.0);
        AnchorPane.setRightAnchor(gameBox, 20.0);
        AnchorPane.setBottomAnchor(gameBox, 20.0);

        // Add balanceBox to the layout
        blackjackGame.getChildren().add(gameBox);

        // Set the new scene and show it
        Scene newScene = new Scene(blackjackGame, 900, 600);
        stage.setScene(newScene);
        stage.show();

    }

    /**
     * Helper method to handle user available action
     * 
     * @param gameActionBox HBox of game action
     */
    private void userAction(HBox gameActionBox, Stage stage, BlackjackGame game) {

        // Add "Hit" Button for user
        Button hitButton = new Button("Hit");
        hitButton.setFont(new Font("Georgia", 20));
        hitButton.setPrefWidth(100);

        // Add "Stand" button for user
        Button standButton = new Button("Stand");
        standButton.setFont(new Font("Georgia", 20));
        standButton.setPrefWidth(100);

        // Betting option
        Label betLabel = new Label("Bet:");
        betLabel.setFont(new Font("Georgia", 20));

        HBox betBox = new HBox(10);
        Button bet1 = new Button("$1");
        bet1.setFont(new Font("Georgia", 20));
        bet1.setPrefWidth(70);

        Button bet5 = new Button("$5");
        bet5.setFont(new Font("Georgia", 20));
        bet5.setPrefWidth(70);

        Button bet25 = new Button("$25");
        bet25.setFont(new Font("Georgia", 20));
        bet25.setPrefWidth(70);

        Button bet50 = new Button("$50");
        bet50.setFont(new Font("Georgia", 20));
        bet50.setPrefWidth(70);

        bet1.setOnAction(event -> game.getHumanPlayer().setBet(1));
        bet5.setOnAction(event -> game.getHumanPlayer().setBet(5));
        bet25.setOnAction(event -> game.getHumanPlayer().setBet(25));
        bet50.setOnAction(event -> game.getHumanPlayer().setBet(50));

        betBox.getChildren().addAll(betLabel, bet1, bet5, bet25, bet50);

        // Add button to game action HBox
        gameActionBox.getChildren().addAll(hitButton, standButton, betBox);

        // Event handler for "Hit"
        hitButton.setOnAction(event -> {
            HumanPlayer user = (HumanPlayer) game.getHumanPlayer();

            // Check if user busted
            if (user.calculateHandValue() > 21) {
                gameManagerController.showAlert("Busted!", "You have gone over 21!");
                updateUI(stage);

            } else {
                user.takeTurn(game.getDeck());
                updateUI(stage);
            }
        });

        // Event handler for "Stand"
        standButton.setOnAction(event -> {
            // User don't take turn, skip to next turn
            game.nextTurn();
            updateUI(stage);
        });

    }

    /**
     * Helper method to highlight current player VBox
     * 
     * @param game       Instance of Blackjack game
     * @param dealerBox  Dealer VBox
     * @param userBox    User VBox
     * @param player1Box Player 1 VBox
     * @param player2Box Player 2 VBox
     */
    private void highlightPlayerBox(BlackjackGame game, VBox dealerBox, VBox userBox, VBox player1Box,
            VBox player2Box) {

        // Reset all box styles
        resetBoxStyles(dealerBox, userBox, player1Box, player2Box);

        // Highlight the appropriate player's box based on whose turn it is
        if (game.getCurrentPlayer() == game.getHumanPlayer()) {
            highlightBox(userBox); // Highlight user’s box if it’s their turn

        } else if (game.getCurrentPlayer() == game.getPlayer1()) {
            highlightBox(player1Box); // Highlight player 1’s box if it’s their turn

        } else if (game.getCurrentPlayer() == game.getPlayer2()) {
            highlightBox(player2Box); // Highlight player 2’s box if it’s their turn

        } else if (game.getCurrentPlayer() == game.getDealer()) {
            highlightBox(dealerBox); // Highlight dealer’s box if it’s their turn
        }

    }

    /**
     * Helper method to reset the styles of all boxes
     */
    private void resetBoxStyles(VBox... boxes) {

        for (VBox box : boxes) {
            box.setStyle(""); // Reset style for all boxes
        }
    }

    /**
     * Helper method to highlight a specific box with a border
     * 
     * @param box VBox to highlight
     */
    private void highlightBox(VBox box) {
        box.setStyle("-fx-border-color: blue; -fx-border-width: 3px;"); // Set blue border to highlight the box
    }

    /**
     * Method to display player card
     * 
     * @param dealerBox  VBox info for dealer
     * @param userBox    VBox info for user
     * @param player1Box VBox info for player 1
     * @param player2Box VBox info for player 2
     */
    private void updateCardsDisplay(VBox dealerBox, VBox userBox, VBox player1Box, VBox player2Box) {
        // Create labels or images for the cards in the user's hand
        HBox playerCardsBox = new HBox(10);
        playerCardsBox.setAlignment(Pos.CENTER);

        for (Card card : game.getHumanPlayer().getHand()) {
            // Displaying card name as an example
            Label cardLabel = new Label(card.toString());
            cardLabel.setFont(new Font("Georgia", 20));
            playerCardsBox.getChildren().add(cardLabel);
        }

        // Create labels or images for the cards in the dealer's hand
        HBox dealerCardsBox = new HBox(10);
        dealerCardsBox.setAlignment(Pos.CENTER);

        List<Card> dealerHand = game.getDealer().getHand();
        Label firstCardLabel = new Label(dealerHand.get(0).toString());
        firstCardLabel.setFont(new Font("Georgia", 20));
        dealerCardsBox.getChildren().add(firstCardLabel);

        // Hide the second card until round end
        Label hiddenCard = new Label("?");
        hiddenCard.setFont(new Font("Georgia", 20));
        dealerCardsBox.getChildren().add(hiddenCard);

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

        userBox.getChildren().add(playerCardsBox);
        player1Box.getChildren().add(player1CardsBox);
        player2Box.getChildren().add(player2CardsBox);
        dealerBox.getChildren().add(dealerCardsBox);

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
            BlackjackUI blackjackGame = new BlackjackUI();
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
