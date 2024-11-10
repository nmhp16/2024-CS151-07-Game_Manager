package com.game.ui;

import java.util.List;

import com.game.GameManagerController;
import com.game.model.HighScore;
import com.game.model.Blackjack.BlackjackGame;
import com.game.model.Blackjack.Card;
import com.game.model.Blackjack.HumanPlayer;
import com.game.model.Blackjack.Player;
import com.game.service.HighScoresManager;

import javafx.animation.PauseTransition;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

// TODO: Complete Blackjack game UI
public class BlackjackUI extends Application {
    private TextArea loadStateField;
    private Button startButton, loadButton;
    private BlackjackGame game;
    private GameManagerController gameManagerController;
    private ToolbarUI toolbar;
    private Label statusLabel;
    private boolean sessionFinished;
    private String username;
    private HighScoresManager highScoresManager = new HighScoresManager();
    private Button saveButton = new Button("Save State");
    private Button stopButton = new Button("Stop");

    /**
     * Overloaded Constructor
     * 
     * @param username
     */
    public BlackjackUI(String username) {
        this.username = username;
    }

    @Override
    public void start(Stage primaryStage) {
        gameManagerController = new GameManagerController(primaryStage);
        game = new BlackjackGame();
        toolbar = new ToolbarUI(gameManagerController, primaryStage);
        sessionFinished = false;

        // Set status label
        statusLabel = new Label("Welcome to Blackjack!");
        statusLabel.setFont(new Font("Georgia", 30));
        statusLabel.setTextFill(Color.RED);
        statusLabel.setAlignment(Pos.CENTER);

        // Main root
        AnchorPane blackjackGame = new AnchorPane();

        // VBox Setting
        VBox gameVBox = new VBox(10);
        gameVBox.setAlignment(Pos.CENTER);
        gameVBox.setPadding(new Insets(10));

        // Anchor the VBox to all sides of the AnchorPane to center it
        AnchorPane.setTopAnchor(gameVBox, 20.0); // Top margin
        AnchorPane.setLeftAnchor(gameVBox, 20.0); // Left margin
        AnchorPane.setRightAnchor(gameVBox, 20.0); // Right margin
        AnchorPane.setBottomAnchor(gameVBox, 20.0); // Bottom margin

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
        gameVBox.getChildren().addAll(gameTitle, startButton, loadButton);

        blackjackGame.getChildren().addAll(toolbar, gameVBox);

        // Anchor toolbar
        AnchorPane.setTopAnchor(toolbar, 0.0); // Top margin
        AnchorPane.setLeftAnchor(toolbar, 0.0); // Left margin
        AnchorPane.setRightAnchor(toolbar, 0.0); // Right margin

        primaryStage.setTitle("Blackjack Game");
        primaryStage.setScene(new Scene(blackjackGame, 600, 400));
        primaryStage.centerOnScreen();

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
        // Game finished reset for new game
        if (sessionFinished == true) {
            game.calculateResults();

        }

        // Create the labels
        Label turnLabel = new Label("Current Turn: " + game.getCurrentPlayer().getName());
        turnLabel.setFont(new Font("Georgia", 20));
        // Hide turn indicator if round finish
        if (sessionFinished == true) {
            turnLabel.setVisible(false);
            turnLabel.setManaged(false); // Remove label
        }

        // Create balance labels
        Label userBalanceLabel = new Label("Your Balance: $" + game.getHumanPlayer().getBalance());
        userBalanceLabel.setFont(new Font("Georgia", 20));

        Label player1BalanceLabel = new Label("Player 1's Balance: $" + game.getPlayer1().getBalance());
        player1BalanceLabel.setFont(new Font("Georgia", 20));

        Label player2BalanceLabel = new Label("Player 2's Balance: $" + game.getPlayer2().getBalance());
        player2BalanceLabel.setFont(new Font("Georgia", 20));

        // Display current bet amounts
        Label userBetLabel = new Label("Your Bet: $" + game.getHumanPlayer().getBet());
        userBetLabel.setFont(new Font("Georgia", 20));

        Label player1BetLabel = new Label("Player 1's Bet: $" + game.getPlayer1().getBet());
        player1BetLabel.setFont(new Font("Georgia", 20));

        Label player2BetLabel = new Label("Player 2's Bet: $" + game.getPlayer2().getBet());
        player2BetLabel.setFont(new Font("Georgia", 20));

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
        userBox.getChildren().addAll(userBalanceLabel, userBetLabel);
        player1Box.getChildren().addAll(player1BalanceLabel, player1BetLabel);
        player2Box.getChildren().addAll(player2BalanceLabel, player2BetLabel);

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

        HBox turnAndAction = new HBox(40);
        turnAndAction.setAlignment(Pos.CENTER_RIGHT);
        turnAndAction.getChildren().add(turnLabel); // Add turn indicator

        // Show User Action Box if user turn
        if (game.getCurrentPlayer() == game.getHumanPlayer() || sessionFinished == true) {
            userAction(gameActionBox, stage, game);
        }

        // Add action box to turn and action box
        turnAndAction.getChildren().add(gameActionBox);

        // Set gameActionBox, at the bottom of game screen
        gameBox.setBottom(turnAndAction);

        // Use VBox to anchor statusLabel
        VBox statusBox = new VBox();
        statusBox.getChildren().add(statusLabel);
        statusBox.setAlignment(Pos.CENTER);

        gameBox.setTop(statusBox);

        // Set up the main layout
        AnchorPane blackjackGame = new AnchorPane();
        AnchorPane.setTopAnchor(gameBox, 60.0);
        AnchorPane.setLeftAnchor(gameBox, 60.0);
        AnchorPane.setRightAnchor(gameBox, 60.0);
        AnchorPane.setBottomAnchor(gameBox, 60.0);

        // Add balanceBox to the layout
        blackjackGame.getChildren().addAll(toolbar, gameBox);

        // Anchor toolbar
        AnchorPane.setTopAnchor(toolbar, 0.0);
        AnchorPane.setLeftAnchor(toolbar, 0.0);
        AnchorPane.setRightAnchor(toolbar, 0.0);

        // Set the new scene and show it
        Scene newScene = new Scene(blackjackGame, 1100, 700);
        stage.setScene(newScene);
        stage.centerOnScreen();

        // If game is not finished
        if (sessionFinished == false) {
            handleBotTurn(stage);
        }

    }

    /**
     * Helper method to update status
     * 
     * @param message Message to display
     */
    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    /**
     * Helper method to handle turn for bot player
     * 
     * @param stage Primary stage
     */
    private void handleBotTurn(Stage stage) {

        String playerName = game.getCurrentPlayer().getName();

        if (playerName.equals("Player 1") || playerName.equals("Player 2")) {
            handlePlayerTurnWithDelay(stage, 16);

        } else if (playerName.equals("Dealer")) {
            handlePlayerTurnWithDelay(stage, 17);
        }
    }

    /**
     * Helper method to handle turn with added delay for animation
     * 
     * @param stage        Current stage
     * @param minHandValue Minimum hand value
     */
    private void handlePlayerTurnWithDelay(Stage stage, int minHandValue) {
        PauseTransition pause = new PauseTransition(Duration.seconds(2)); // Adjust time here
        pause.setOnFinished(event -> {
            Player currentPlayer = game.getCurrentPlayer();

            // Player busts if hand value > 21
            if (currentPlayer.calculateHandValue() > 21) {
                updateStatus(currentPlayer.getName() + " Bust!");

                if (currentPlayer.getName().equals("Dealer")) {
                    sessionFinished = true;
                } else {
                    game.nextTurn();
                }
            }

            // Hand value < minHandValue
            else if (currentPlayer.calculateHandValue() < minHandValue) {
                // Bot "Hit" if hand < minHandValue
                currentPlayer.takeTurn(game.getDeck());
                updateStatus(currentPlayer.getName() + " Hit!");
            }

            // Hand value >= minHandValue <= 21
            else {
                // Bot "Stand" if hand >= minHandValue
                updateStatus(currentPlayer.getName() + " Stand!");

                // If Dealer stop, don't go next turn
                if (currentPlayer.getName().equals("Dealer")) {
                    sessionFinished = true;
                } else {
                    game.nextTurn();
                }
            }

            updateUI(stage);
        });
        // Start pause transition
        pause.play();
    }

    /**
     * Helper method to handle user available action
     * 
     * @param gameActionBox HBox of game action
     */
    private void userAction(HBox gameActionBox, Stage stage, BlackjackGame game) {

        HBox hitAndStandBox = new HBox(10);

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

        bet1.setOnAction(event -> {
            game.getHumanPlayer().setBet(1);
            updateUI(stage);
        });
        bet5.setOnAction(event -> {
            game.getHumanPlayer().setBet(5);
            updateUI(stage);
        });
        bet25.setOnAction(event -> {
            game.getHumanPlayer().setBet(25);
            updateUI(stage);
        });
        bet50.setOnAction(event -> {
            game.getHumanPlayer().setBet(50);
            updateUI(stage);
        });

        betBox.getChildren().addAll(betLabel, bet1, bet5, bet25, bet50);

        // Add new round button to gameActionBox
        Button newRoundButton = new Button("New Round");
        newRoundButton.setFont(new Font("Georgia", 20));
        newRoundButton.setPrefWidth(150);

        // Stop button, to get score, and exit game

        stopButton.setFont(new Font("Georgia", 20));
        stopButton.setPrefWidth(150);

        // Save button, to get save state string

        saveButton.setFont(new Font("Georgia", 20));
        saveButton.setPrefWidth(150);

        // Ensure buttons are added only once
        if (!toolbar.getItems().contains(stopButton)) {
            toolbar.getItems().add(stopButton);
        }
        // Ensure buttons are added only once
        if (!toolbar.getItems().contains(saveButton)) {
            toolbar.getItems().add(saveButton);
        }

        if (sessionFinished == false) {
            newRoundButton.setVisible(false);
            newRoundButton.setManaged(false); // Remove from layout
        } else {
            hitAndStandBox.setVisible(false);
            hitAndStandBox.setManaged(false); // Remove from layout
            betBox.setVisible(false);
            betBox.setManaged(false); // Remove from layout
        }

        // Add hit and stand button to HBox
        hitAndStandBox.getChildren().addAll(hitButton, standButton);

        // Add button to game action HBox
        gameActionBox.getChildren().addAll(hitAndStandBox, betBox, newRoundButton);
        gameActionBox.setAlignment(Pos.CENTER);
        HumanPlayer user = (HumanPlayer) game.getHumanPlayer();

        // Event handler for "Hit"
        hitButton.setOnAction(event -> {
            // User bet = 0
            if (game.getHumanPlayer().getBet() == 0) {
                gameManagerController.showAlert("Invalid Bet Amount!", "Please place bet to continue!");
            }
            // User have bet
            else {
                // Check if user busted
                if (user.calculateHandValue() > 21) {
                    gameManagerController.showAlert("Busted!", "You have gone over 21!");
                } else {
                    updateStatus("You hit!");
                    user.takeTurn(game.getDeck());
                    updateUI(stage);
                }
            }
        });

        // Event handler for "Stand"
        standButton.setOnAction(event -> {
            // User bet = 0
            if (game.getHumanPlayer().getBet() == 0) {
                gameManagerController.showAlert("Invalid Bet Amount!", "Please place bet to continue!");
            }
            // User have bet
            else {
                // User don't take turn, skip to next turn
                if (user.calculateHandValue() < 16) {
                    gameManagerController.showAlert("Invalid!", "Make sure hand value at least 16!");
                } else {
                    updateStatus("You stands!");
                    game.nextTurn();
                    updateUI(stage);
                }
            }
        });

        // Event handler for new round
        newRoundButton.setOnAction(event -> {
            game.startNewRound();
            sessionFinished = false;
            updateStatus("Starting new round");
            updateUI(stage);
        });

        // Event handler for stop
        stopButton.setOnAction(event -> {
            HighScore highScore = new HighScore(username, game.getHumanPlayer().getBalance(),
                    "Blackjack");

            highScoresManager.addHighScores(username, highScore);
            highScoresManager.loadHighScores();
            BlackjackUI blackjackUI = new BlackjackUI(username);
            blackjackUI.start(stage);
        });

        // Event handler for save state
        saveButton.setOnAction(event -> {
            // Create new Stage for save state dialog
            Stage saveStateStage = new Stage();
            saveStateStage.setTitle("Save Game State");

            // BorderPane to hold TextArea
            BorderPane displaySaveState = new BorderPane();

            // Create TextArea to display saved game state
            TextArea saveStateString = new TextArea();
            saveStateString.appendText(game.saveGameState());

            // TextArea set at center of BorderPane
            displaySaveState.setCenter(saveStateString);

            // Set uo scene for new stage
            saveStateStage.setScene(new Scene(displaySaveState, 400, 200));
            saveStateStage.setResizable(false);
            saveStateStage.show();

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

        // Show all dealer cards except the 2nd one until turn over
        for (int i = 0; i < dealerHand.size(); i++) {
            Label cardLabel = new Label(dealerHand.get(i).toString());
            cardLabel.setFont(new Font("Georgia", 20));

            // Hide 2nd card if not dealer turn
            if (i == 1 && (game.getCurrentPlayer() != game.getDealer())) {
                cardLabel.setText("?");
            }
            dealerCardsBox.getChildren().add(cardLabel);
        }

        // Create labels or images for the cards in the player1's hand
        VBox player1CardsBox = new VBox(10);
        player1CardsBox.setAlignment(Pos.CENTER);

        for (Card card : game.getPlayer1().getHand()) {
            Label cardLabel = new Label(card.toString());
            cardLabel.setFont(new Font("Georgia", 20));
            player1CardsBox.getChildren().add(cardLabel);
        }

        // Create labels or images for the cards in the player2's hand
        VBox player2CardsBox = new VBox(10);
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
            BlackjackUI blackjackGame = new BlackjackUI(username);
            blackjackGame.start(stage);
        });

        // Add elements to dialog
        dialogVBox.getChildren().addAll(instructionLabel, loadStateField, loadGameButton, cancelButton);

        // Add dialog VBox to current stage
        stage.setScene(new Scene(dialogVBox, 600, 400));
        stage.setTitle("Load saved state");
        stage.centerOnScreen();
    }

}
