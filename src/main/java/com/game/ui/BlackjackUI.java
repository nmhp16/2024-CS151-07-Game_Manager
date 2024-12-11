package com.game.ui;

import java.io.File;
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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private Button newRoundButton = new Button("New Round");
    public static boolean isBlackjackRunning = false;

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
        isBlackjackRunning = true;
        updateUI(stage);
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

            if (!saveStateString.isEmpty() && game.loadGameState(saveStateString)) {
                isBlackjackRunning = true;
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

    /**
     * Method to update UI with current game state
     * 
     * @param stage Current primary stage
     */
    private void updateUI(Stage stage) {

        // Stop updateUI if game is not running
        if (isBlackjackRunning == false) {
            return;
        }

        // Create the turn label
        Label turnLabel = createTurnLabel();

        // Create bet images layout in BorderPane, calculate result if session finished
        BorderPane boardMiddle = createBetImagesLayout(stage);

        // Create balance labels for players
        Label userBalanceLabel = createLabel("Your Balance: $" + game.getHumanPlayer().getBalance());
        Label player1BalanceLabel = createLabel("Player 1's Balance: $" + game.getPlayer1().getBalance());
        Label player2BalanceLabel = createLabel("Player 2's Balance: $" + game.getPlayer2().getBalance());

        // Create layout components
        BorderPane gameBox = new BorderPane();
        BorderPane playerInfo = createPlayerInfoLayout(userBalanceLabel, player1BalanceLabel, player2BalanceLabel);

        // Set boardMiddle at the center of BorderPane playerInfo
        playerInfo.setCenter(boardMiddle);

        // HBox for game action
        HBox gameActionBox = new HBox(30);
        gameActionBox.setAlignment(Pos.CENTER);

        HBox turnAndAction = new HBox(40);
        turnAndAction.setAlignment(Pos.CENTER_RIGHT);
        turnAndAction.getChildren().add(turnLabel); // Add turn indicator

        // Show User Action Box if user turn, or game finished
        if (game.getCurrentPlayer() == game.getHumanPlayer() || sessionFinished) {
            userAction(gameActionBox, stage, game);
        }

        // Add action box to turn and action box
        turnAndAction.getChildren().add(gameActionBox);

        // Add playerInfo to center of gameBox
        gameBox.setCenter(playerInfo);

        // Set gameActionBox, at the bottom of game screen
        gameBox.setBottom(turnAndAction);

        // Use VBox to anchor statusLabel
        VBox statusBox = new VBox();
        statusBox.getChildren().add(statusLabel);
        statusBox.setAlignment(Pos.CENTER);

        // Set status box top of BorderPane gameBox
        gameBox.setTop(statusBox);

        // Set up the main layout
        AnchorPane blackjackGame = new AnchorPane();
        AnchorPane.setTopAnchor(gameBox, 60.0);
        AnchorPane.setLeftAnchor(gameBox, 60.0);
        AnchorPane.setRightAnchor(gameBox, 60.0);
        AnchorPane.setBottomAnchor(gameBox, 60.0);

        // Set background image for game
        setBackgroundImage(blackjackGame);

        // Add balanceBox to the layout
        blackjackGame.getChildren().addAll(toolbar, gameBox);

        // Anchor toolbar
        AnchorPane.setTopAnchor(toolbar, 0.0);
        AnchorPane.setLeftAnchor(toolbar, 0.0);
        AnchorPane.setRightAnchor(toolbar, 0.0);

        // Get the primary screen size
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();

        // Set the new scene and show it
        Scene newScene = new Scene(blackjackGame, screenWidth - 20, screenHeight - 40);
        stage.setScene(newScene); // Set scene to stage
        stage.centerOnScreen();

        // If game is not finished
        if (!sessionFinished) {
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

            if (currentPlayer.calculateHandValue() == 21 && currentPlayer.getHand().size() == 2
                    || currentPlayer.calculateHandValue() == 22 && currentPlayer.getHand().size() == 2) {
                updateStatus(currentPlayer.getName() + " Blackjack!");
                isSessionFinished();
            } else if (currentPlayer.calculateHandValue() > 21) {
                updateStatus(currentPlayer.getName() + " Bust!");
                isSessionFinished();
            } else {
                // Delegate to the player's takeTurn method
                currentPlayer.takeTurn(game.getDeck());
                if (currentPlayer.calculateHandValue() >= minHandValue || currentPlayer.getHand().size() == 5) {
                    updateStatus(currentPlayer.getName() + " Stand!");
                    isSessionFinished();
                } else {
                    updateStatus(currentPlayer.getName() + " Hit!");
                }
            }

            updateUI(stage);
        });
        pause.play();
    }

    /**
     * Helper method to determine if the game session is finished
     * 
     * This method will check if the current player is the dealer. If the dealer is
     * the current player, the game session is finished.
     * If it is not finished, the game will move to the next turn.
     */
    private void isSessionFinished() {
        if (game.getCurrentPlayer().getName().equals("Dealer")) {
            sessionFinished = true;
        } else {
            game.nextTurn();
        }
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
        Label betLabel = createLabel("Bet:");

        HBox betBox = new HBox(10);
        Button bet10 = new Button("$10");
        bet10.setFont(new Font("Georgia", 20));
        bet10.setPrefWidth(90);

        Button bet50 = new Button("$50");
        bet50.setFont(new Font("Georgia", 20));
        bet50.setPrefWidth(90);

        Button bet100 = new Button("$100");
        bet100.setFont(new Font("Georgia", 20));
        bet100.setPrefWidth(90);

        Button bet500 = new Button("$500");
        bet500.setFont(new Font("Georgia", 20));
        bet500.setPrefWidth(90);

        bet10.setOnAction(event -> {
            if (game.getHumanPlayer().getBalance() < 10) {
                gameManagerController.showAlert("Error", "Insufficient balance!");
            } else {
                game.getHumanPlayer().setBet(10);
                updateUI(stage);
            }
        });
        bet50.setOnAction(event -> {
            if (game.getHumanPlayer().getBalance() < 50) {
                gameManagerController.showAlert("Error", "Insufficient balance!");
            } else {
                game.getHumanPlayer().setBet(50);
                updateUI(stage);
            }
        });
        bet100.setOnAction(event -> {
            if (game.getHumanPlayer().getBalance() < 100) {
                gameManagerController.showAlert("Error", "Insufficient balance!");
            } else {
                game.getHumanPlayer().setBet(100);
                updateUI(stage);
            }
        });
        bet500.setOnAction(event -> {
            if (game.getHumanPlayer().getBalance() < 500) {
                gameManagerController.showAlert("Error", "Insufficient balance!");
            } else {
                game.getHumanPlayer().setBet(500);
                updateUI(stage);
            }
        });

        betBox.getChildren().addAll(betLabel, bet10, bet50, bet100, bet500);

        // Stop button, to get score, and exit game
        stopButton.setFont(new Font("Georgia", 20));
        stopButton.setPrefWidth(150);

        // Save button, to get save state string
        saveButton.setFont(new Font("Georgia", 20));
        saveButton.setPrefWidth(150);

        // Add new round button to toolbar
        newRoundButton.setFont(new Font("Georgia", 20));
        newRoundButton.setPrefWidth(150);

        // Ensure buttons are added only once
        if (!toolbar.getItems().contains(stopButton)) {
            toolbar.getItems().add(stopButton);
        }
        // Ensure buttons are added only once
        if (!toolbar.getItems().contains(saveButton)) {
            toolbar.getItems().add(saveButton);
        }

        // Ensure buttons are added only once
        if (!toolbar.getItems().contains(newRoundButton)) {
            toolbar.getItems().add(newRoundButton);
        }

        // Game finished hide these
        if (sessionFinished) {
            hitAndStandBox.setVisible(false);
            hitAndStandBox.setManaged(false); // Remove from layout
            betBox.setVisible(false);
            betBox.setManaged(false); // Remove from layout
        }

        // Add hit and stand button to HBox
        hitAndStandBox.getChildren().addAll(hitButton, standButton);

        // Add button to game action HBox
        gameActionBox.getChildren().addAll(hitAndStandBox, betBox);
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
                boolean playerBlackjack = game.getHumanPlayer().calculateHandValue() == 21
                        && game.getHumanPlayer().getHand().size() == 2;

                // If user have blackjack
                if (playerBlackjack) {
                    updateStatus(user.getName() + " Blackjack!");
                    gameManagerController.showAlert("Invalid move!", "You have blackjack!");
                }
                // Check if user busted
                else if (user.calculateHandValue() > 21) {
                    gameManagerController.showAlert("Busted!", "You have gone over 21!");
                }
                // Hand value <= 21
                else {
                    // If hand size < 5
                    if (user.getHand().size() < 5) {
                        updateStatus("You hit!");
                        user.takeTurn(game.getDeck());

                        if (user.calculateHandValue() > 21) {
                            updateStatus(user.getName() + " Busted!");
                            game.nextTurn();
                        }
                        updateUI(stage);
                    }
                    // Hand size >= 5
                    else {
                        gameManagerController.showAlert("Limit reached!", "You have reached limit of 5 cards!");
                    }
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
                if (user.calculateHandValue() < 16 && user.getHand().size() < 5) {
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
            if (!sessionFinished) {
                gameManagerController.showAlert("Error", "Game is running!");
            } else {
                game.startNewRound();
                sessionFinished = false;
                updateStatus("Starting new round");
                updateUI(stage);
            }
        });

        // Event handler for stop
        stopButton.setOnAction(event -> {
            isBlackjackRunning = false;
            gameManagerController.showAlert("Game Finished!", "Thanks for playing!");
            HighScore highScore = new HighScore(username, game.getHumanPlayer().getBalance(),
                    "Blackjack");

            highScoresManager.addHighScores(username, highScore);

            BlackjackUI blackjackUI = new BlackjackUI(username);
            blackjackUI.start(stage);
        });

        // Event handler for save state
        saveButton.setOnAction(event -> {
            isBlackjackRunning = false;

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

            // Set up scene for new stage
            saveStateStage.setScene(new Scene(displaySaveState, 400, 200));
            saveStateStage.setResizable(false);
            saveStateStage.show();

            saveStateStage.setOnCloseRequest(event1 -> {
                isBlackjackRunning = true;
            });
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
     * Helper method to create card image view
     * 
     * @param card     Card to create
     * @param rotation Rotation needed
     * @param width    Width of card
     * @return ImageView of card
     */
    private ImageView createCardImageView(Card card, double rotation, int width) {
        File file = new File("images/" + card.toString().toLowerCase() + ".png");
        Image image = new Image(file.toURI().toString());
        ImageView cardImageView = new ImageView(image);
        cardImageView.setFitWidth(width);
        cardImageView.setPreserveRatio(true);
        cardImageView.setRotate(rotation);
        return cardImageView;
    }

    /**
     * Helper method to create HBox containing bot cards
     * 
     * @param hand Bot list of card
     * @return HBox with bot cards
     */
    private VBox createBotCardsBox(List<Card> hand) {
        VBox cardsBox = new VBox(10);
        cardsBox.setAlignment(Pos.CENTER);

        for (Card card : hand) {
            // Using Group to apply rotation to avoid extra space from rotating
            Group rotatedGroup = new Group(createCardImageView(card, 90, 70));

            cardsBox.getChildren().add(rotatedGroup);
        }
        return cardsBox;
    }

    /**
     * Helper method to create HBox containing dealer cards
     * 
     * @param hand Dealer list of card
     * @return HBox with dealer cards
     */
    private HBox createDealerCardsBox(List<Card> hand) {
        HBox dealerCardsBox = new HBox(10);
        dealerCardsBox.setAlignment(Pos.CENTER);

        // Show all dealer cards except the 2nd one until turn over
        for (int i = 0; i < hand.size(); i++) {
            ImageView cardImageView = createCardImageView(hand.get(i), 0, 70);

            // Hide 2nd card if not dealer turn
            if (i == 1 && sessionFinished == false) {
                cardImageView = getCardBackImageView(70);
            }
            dealerCardsBox.getChildren().add(cardImageView);
        }

        return dealerCardsBox;
    }

    /**
     * 
     * Helper method to create HBox containing user cards
     * 
     * @param hand User list of card
     * @return HBox with user cards
     */
    private HBox createUserCardsBox(List<Card> hand) {
        // Create labels or images for the cards in the user's hand
        HBox userCardsBox = new HBox(10);
        userCardsBox.setAlignment(Pos.CENTER);

        // Displaying card for user
        for (Card card : game.getHumanPlayer().getHand()) {
            userCardsBox.getChildren().add(createCardImageView(card, 0, 70));
        }
        return userCardsBox;
    }

    /**
     * Helper method to display player card
     * 
     * @param dealerBox  VBox info for dealer
     * @param userBox    VBox info for user
     * @param player1Box VBox info for player 1
     * @param player2Box VBox info for player 2
     */
    private void updateCardsDisplay(VBox dealerBox, VBox userBox, VBox player1Box, VBox player2Box) {

        userBox.getChildren().add(createUserCardsBox(game.getHumanPlayer().getHand()));
        player1Box.getChildren().add(createBotCardsBox(game.getPlayer1().getHand()));
        player2Box.getChildren().add(createBotCardsBox(game.getPlayer2().getHand()));
        dealerBox.getChildren().add(createDealerCardsBox(game.getDealer().getHand()));
    }

    /**
     * Helper method to set image for card back
     * 
     * @param gameBox    BorderPane for game information
     * @param playerInfo BorderPane for player information
     */
    private ImageView getCardBackImageView(int width) {
        File file = new File("images/back.png");
        Image image = new Image(file.toURI().toString());
        ImageView backImageView = new ImageView(image);

        // Adjust card back size
        backImageView.setFitWidth(width);
        backImageView.setPreserveRatio(true);

        return backImageView;
    }

    /**
     * Helper method to set game background
     * 
     * @param blackjackGame AnchorPane for game display
     */
    private void setBackgroundImage(AnchorPane blackjackGame) {
        // Set background image
        File backgroundFile = new File("images/background.jpg");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        ImageView imageView = new ImageView(backgroundImage);

        // Resize background image to fill the screen
        imageView.setFitWidth(2000);
        imageView.setFitHeight(2000);
        imageView.setPreserveRatio(true);

        // Add background image as the first child
        blackjackGame.getChildren().add(imageView);
    }

    /**
     * Helper method to display image for bet
     * 
     * @param betAmount Bet amount
     * @param width     Width of image
     * @return ImageView for bet
     */
    private ImageView getBetImage(int betAmount, int width) {
        File file = new File("images/" + Integer.toString(betAmount) + "Chip.png");
        Image image = new Image(file.toURI().toString());
        ImageView betImageView = new ImageView(image);
        betImageView.setFitWidth(width);
        betImageView.setPreserveRatio(true);

        return betImageView;
    }

    /**
     * Helper method to create images for bet amount
     * 
     * @return BorderPane containing images
     */
    private BorderPane createBetImagesLayout(Stage stage) {
        BorderPane boardMiddle = new BorderPane();

        // Display mage view for bet amount
        ImageView player1Bet = getBetImage(game.getPlayer1().getBet(), 60);
        ImageView player2Bet = getBetImage(game.getPlayer2().getBet(), 60);
        ImageView userBet = getBetImage(game.getHumanPlayer().getBet(), 60);

        // Alignment bet image view center inside BorderPane boardMiddle
        BorderPane.setAlignment(player1Bet, Pos.CENTER_RIGHT);
        BorderPane.setAlignment(player2Bet, Pos.CENTER_LEFT);
        BorderPane.setAlignment(userBet, Pos.CENTER);

        // If session finished
        if (sessionFinished) {

            Label player1Result = createLabel(game.calculateResults(game.getPlayer1()));
            player1Result.setTextFill(Color.RED);

            Label player2Result = createLabel(game.calculateResults(game.getPlayer2()));
            player2Result.setTextFill(Color.RED);

            Label userResult = createLabel(game.calculateResults(game.getHumanPlayer()));
            userResult.setTextFill(Color.RED);

            // Set location for label
            boardMiddle.setRight(player1Result);
            boardMiddle.setLeft(player2Result);
            boardMiddle.setBottom(userResult);

            // Alignment bet image view center inside BorderPane boardMiddle
            BorderPane.setAlignment(player1Result, Pos.CENTER_RIGHT);
            BorderPane.setAlignment(player2Result, Pos.CENTER_LEFT);
            BorderPane.setAlignment(userResult, Pos.CENTER);

            // Display game over if balance <= 0
            if (game.getHumanPlayer().getBalance() <= 0) {
                displayGameOverScreen(stage);
            }

        } else {
            // Set bet image location inside BorderPane boardMiddle
            boardMiddle.setRight(player1Bet); // Player 2 bet
            boardMiddle.setLeft(player2Bet); // Player 1 bet
            boardMiddle.setBottom(userBet); // User bet
        }

        return boardMiddle;
    }

    /**
     * Helper method to create turn label
     * 
     * @return Label for turn indication
     */
    private Label createTurnLabel() {
        Label turnLabel = createLabel("Current Turn: " + game.getCurrentPlayer().getName());

        // Hide turn indicator if round finish
        if (sessionFinished == true) {
            turnLabel.setVisible(false);
            turnLabel.setManaged(false); // Remove label
        }

        return turnLabel;
    }

    /**
     * Helper method to create label
     * 
     * @param message Message of label
     * @return Label
     */
    private Label createLabel(String message) {
        Label label = new Label(message);
        label.setFont(new Font("Georgia", 20));
        label.setTextFill(Color.WHITE);
        return label;
    }

    /**
     * Helper method to create BorderPane of player info
     * 
     * @param userBalanceLabel    User balance label
     * @param player1BalanceLabel Player 1 balance label
     * @param player2BalanceLabel Player 2 balance label
     * @return BorderPane of player info
     */
    private BorderPane createPlayerInfoLayout(Label userBalanceLabel, Label player1BalanceLabel,
            Label player2BalanceLabel) {
        BorderPane playerInfo = new BorderPane();
        playerInfo.setPadding(new Insets(20));

        // VBox for each player
        VBox userBox = new VBox(10);
        VBox player1Box = new VBox(10);
        VBox player2Box = new VBox(10);
        VBox dealerBox = new VBox(10);

        // Alignment for each user box inside BorderPane playerInfo
        BorderPane.setAlignment(dealerBox, Pos.TOP_CENTER);
        BorderPane.setAlignment(userBox, Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(player1Box, Pos.CENTER_RIGHT);
        BorderPane.setAlignment(player2Box, Pos.CENTER_LEFT);

        // Align each player VBox Center
        userBox.setAlignment(Pos.CENTER);
        player1Box.setAlignment(Pos.CENTER);
        player2Box.setAlignment(Pos.CENTER);
        dealerBox.setAlignment(Pos.CENTER);

        // Add balance to each user VBox
        userBox.getChildren().addAll(userBalanceLabel);
        player1Box.getChildren().addAll(player1BalanceLabel);
        player2Box.getChildren().addAll(player2BalanceLabel);

        // Highlight current player VBox
        highlightPlayerBox(game, dealerBox, userBox, player1Box, player2Box);

        // Add dealer player name
        Label dealerLabel = createLabel("Dealer");
        dealerBox.getChildren().add(dealerLabel);

        // Display cards
        updateCardsDisplay(dealerBox, userBox, player1Box, player2Box);

        // Set each player box to BorderPane
        playerInfo.setBottom(userBox);
        playerInfo.setRight(player1Box);
        playerInfo.setLeft(player2Box);
        playerInfo.setTop(dealerBox);

        return playerInfo;
    }

    /**
     * Displays a game over screen with a message and an instruction to restart
     * the game. The screen is set up with a new stage and shows a "Game Over"
     * label along with a prompt to press ENTER for restarting. The game is
     * restarted when the ENTER key is pressed.
     */
    private void displayGameOverScreen(Stage stage) {
        Stage ps = new Stage();

        AnchorPane root = new AnchorPane();

        VBox gameOverLayout = new VBox(10);
        gameOverLayout.setAlignment(Pos.CENTER);
        gameOverLayout.setPadding(new Insets(20));

        Label gameOverLabel = new Label("Game Over");
        gameOverLabel.setFont(Font.font("Tahoma", 32));

        Label restartInstruction = new Label("Press ENTER to restart or ESCAPE to exit");
        restartInstruction.setFont(Font.font("Tahoma", 16));

        Scene gameOverScene = new Scene(root, 600, 400);

        gameOverScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                sessionFinished = false;
                game.resetGame();
                updateUI(stage);
                ps.close();

            } else if (e.getCode() == KeyCode.ESCAPE) {
                BlackjackUI blackjackUI = new BlackjackUI(username);
                blackjackUI.start(stage);
                ps.close();
            }
        });

        gameOverLayout.getChildren().addAll(gameOverLabel, restartInstruction);
        root.getChildren().addAll(gameOverLayout);

        // Anchor gameOverLayout
        AnchorPane.setBottomAnchor(gameOverLayout, 40.0);
        AnchorPane.setLeftAnchor(gameOverLayout, 40.0);
        AnchorPane.setRightAnchor(gameOverLayout, 40.0);
        AnchorPane.setTopAnchor(gameOverLayout, 40.0);

        ps.setScene(gameOverScene);
        ps.setTitle("Game Over");
        ps.setResizable(false);
        ps.centerOnScreen();
        ps.show();
    }
}
