package com.game.ui;

import com.game.GameManagerController;
import com.game.model.HighScore;
import com.game.model.Snake.Block;
import com.game.model.Snake.Field;
import com.game.model.Snake.Snake;
import com.game.service.HighScoresManager;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SnakeUI extends Application {

<<<<<<< Aarons-Branch
        public static int block_size = 15;
        int width = 70, height = 30;
        int il = 2;

    private long then = System.nanoTime();

    private boolean changed = false;
    private  int nextUpdate;
    private boolean hasNext = false;

    Field f;
=======
        public static int block_size = 25;
        public static boolean isSnakeRunning = false;
        private int width = 45, height = 25;
        private int il = 2;
        private long then = System.nanoTime();
        private boolean changed = false;
        private int nextUpdate;
        private boolean hasNext = false;
        private Field f;
        private Stage pauseStage;
        private String username;
        private boolean paused = false;
        private HighScoresManager highScoresManager;
        private ToolbarUI toolbar;
        private GameManagerController gameManagerController;

        // Overloaded constructor
        public SnakeUI(String username) {
                this.username = username; // Initialize the username
                this.highScoresManager = new HighScoresManager(); // Initialize highScoresManager
        }
>>>>>>> main

        /**
         * Start the game of snake, create the game window, toolbar, Field (snake game
         * board)
         * and display the score. Add event handler for key presses, start the game loop
         * and display the game over screen when the game is over.
         * 
         * @param ps The primary stage
         */
        @Override
        public void start(Stage ps) {
                isSnakeRunning = true;
                gameManagerController = new GameManagerController(ps);
                toolbar = new ToolbarUI(gameManagerController, ps);

                AnchorPane root = new AnchorPane();

                VBox gameVBox = new VBox(10);
                gameVBox.setPadding(new Insets(10));

                f = new Field(width, height);
                f.addSnake(new Snake(il, f));

                Label score = new Label("Score : 0");
                score.setFont(Font.font("Tahoma", 32));
                // speed control for the snake
                AnimationTimer timer = new AnimationTimer() {
                        public void handle(long now) {
                                if (isSnakeRunning == false) {
                                        stop(); // Stop the game
                                        return; // Exit the loop
                                }
                                if (paused) {
                                        return;
                                }
                                if (now - then > 100000000 / 1.5) {
                                        f.update();
                                        then = now;
                                        score.setText("Score: " + f.getScore());
                                        changed = false;
                                        if (hasNext) {
                                                setDirection(f.getSnake(), nextUpdate);
                                                hasNext = false;
                                        }

                                        if (f.isDead()) {
                                                if (f.isDead()) {
                                                        stop();
                                                        int currentScore = f.getScore();
                                                        updateHighScore(username, currentScore);
                                                        displayGameOverScreen(currentScore, ps); // Replace Alert with a
                                                                                                 // dedicated Game Over
                                                                                                 // Screen
                                                }

                                                Alert al = new Alert(AlertType.INFORMATION);
                                                al.setOnHidden(e -> {
                                                        gameVBox.getChildren().clear();
                                                        f = new Field(width, height);
                                                        f.addSnake(new Snake(il, f));
                                                        score.setText("Score: 0");
                                                        gameVBox.getChildren().addAll(f, score);
                                                        start();

                                                });
                                        }
                                }
                        }
                };
                timer.start();

                gameVBox.getChildren().addAll(f, score);

                // Anchor the toolbar to the top of the AnchorPane
                AnchorPane.setTopAnchor(toolbar, 0.0);
                AnchorPane.setLeftAnchor(toolbar, 0.0);
                AnchorPane.setRightAnchor(toolbar, 0.0);

                // Anchor gameVBox
                AnchorPane.setBottomAnchor(gameVBox, 0.0);
                AnchorPane.setLeftAnchor(gameVBox, 0.0);
                AnchorPane.setRightAnchor(gameVBox, 0.0);
                AnchorPane.setTopAnchor(gameVBox, 40.0);

                root.getChildren().addAll(toolbar, gameVBox);

                Scene scene = new Scene(root);

                // Set focus to the Field when scene loads
                scene.setOnMouseClicked(e -> f.requestFocus());
                f.setFocusTraversable(true);
                f.requestFocus(); // Focus on the Field

                scene.setOnKeyPressed(e -> {
                        f.requestFocus(); // Redirect focus back the game field

                        if (e.getCode() == KeyCode.ESCAPE) {
                                initPausePopup(ps);
                                togglePause(timer);
                        }
                        if (e.getCode().equals(KeyCode.UP) && f.getSnake().getDirection() != Block.DOWN) {
                                f.getSnake().setDirection(Block.UP);
                        }
                        if (e.getCode().equals(KeyCode.DOWN) && f.getSnake().getDirection() != Block.UP) {
                                f.getSnake().setDirection(Block.DOWN);
                        }
                        if (e.getCode().equals(KeyCode.RIGHT) && f.getSnake().getDirection() != Block.LEFT) {
                                f.getSnake().setDirection(Block.RIGHT);
                        }
                        if (e.getCode().equals(KeyCode.LEFT) && f.getSnake().getDirection() != Block.RIGHT) {
                                f.getSnake().setDirection(Block.LEFT);
                        }

                });
                ps.setResizable(false);
                ps.setScene(scene); // Set the scene
                f.requestFocus(); // Focus on the Field
                ps.centerOnScreen(); // Center the window
                ps.setTitle("Snake Game"); // Scene title
                ps.show();
        }

        /**
         * Displays a game over screen with the final score and instruction to restart
         * the game.
         * When the user presses ENTER, the game is restarted.
         * 
         * @param finalScore The user's final score.
         * @param ps         The Stage to display the game over screen on.
         */
        private void displayGameOverScreen(int finalScore, Stage ps) {
                isSnakeRunning = false;
                AnchorPane root = new AnchorPane();

                VBox gameOverLayout = new VBox(10);
                gameOverLayout.setAlignment(Pos.CENTER);
                gameOverLayout.setPadding(new Insets(20));

                Label gameOverLabel = new Label("Game Over");
                gameOverLabel.setFont(Font.font("Tahoma", 32));

                Label scoreLabel = new Label("Your Final Score: " + finalScore);
                scoreLabel.setFont(Font.font("Tahoma", 20));

                Label restartInstruction = new Label("Press ENTER to restart");
                restartInstruction.setFont(Font.font("Tahoma", 16));

                gameOverLayout.getChildren().addAll(gameOverLabel, scoreLabel, restartInstruction);
                root.getChildren().addAll(toolbar, gameOverLayout);

                // Anchor toolbar to the top of the AnchorPane
                AnchorPane.setTopAnchor(toolbar, 0.0);
                AnchorPane.setLeftAnchor(toolbar, 0.0);
                AnchorPane.setRightAnchor(toolbar, 0.0);

                // Anchor gameOverLayout
                AnchorPane.setBottomAnchor(gameOverLayout, 0.0);
                AnchorPane.setLeftAnchor(gameOverLayout, 0.0);
                AnchorPane.setRightAnchor(gameOverLayout, 0.0);
                AnchorPane.setTopAnchor(gameOverLayout, 40.0);

                Scene gameOverScene = new Scene(root, block_size * width, block_size * height);
                gameOverScene.setOnKeyPressed(e -> {
                        if (e.getCode() == KeyCode.ENTER) {
                                restartGame(ps); // Restart the game when ENTER is pressed
                        }
                });

                ps.setScene(gameOverScene);
                ps.show();
        }

        /**
         * Sets the direction of the snake. If the snake has not been changed
         * direction recently, this sets the direction immediately. If the snake
         * has recently been changed direction, this sets the direction to be
         * updated in the next game loop iteration.
         * 
         * @param s The snake whose direction is to be set.
         * @param d The direction to set the snake to. Should be one of the
         *          constants defined in the Block class.
         */
        public void setDirection(Snake s, int d) {
                if (!changed) {
                        s.setDirection(d);
                        changed = true;
                } else {
                        hasNext = true;
                        nextUpdate = d;
                }
        }

        /**
         * Toggles the pause state of the game. If the game is paused, calling this
         * method will unpause it. If the game is not paused, calling this method
         * will pause it.
         * 
         * @param timer The AnimationTimer for the game.
         */
        private void togglePause(AnimationTimer timer) {
                paused = !paused;

                if (paused) {
                        pauseStage.show();

                } else {
                        pauseStage.close();
                        then = System.nanoTime();
                }
        }

        /**
         * Initializes the pause popup window. This window appears when the game
         * is paused and contains the text "Game Paused". The window is modal, so
         * the user must close it to continue the game. The window also updates the
         * high score for the user if the user has paused the game.
         * 
         * @param ps The Stage for the game.
         */
        private void initPausePopup(Stage ps) {

                pauseStage = new Stage();
                pauseStage.initOwner(ps);
                pauseStage.initModality(Modality.WINDOW_MODAL); // Makes the pause window modal
                pauseStage.setResizable(false);
                pauseStage.setTitle("Paused");

                Label pauseLabel = new Label("Game Paused");
                pauseLabel.setFont(Font.font("Tahoma", 24));

                VBox pauseLayout = new VBox(pauseLabel);
                pauseLayout.setSpacing(20);
                pauseLayout.setPrefSize(200, 100);

                pauseLayout.setAlignment(Pos.CENTER);

                int currentScore = f.getScore();
                updateHighScore(username, currentScore);

                Scene pauseScene = new Scene(pauseLayout);
                pauseScene.setOnKeyPressed(e -> {
                        if (e.getCode() == KeyCode.ESCAPE) {
                                togglePause(null);
                        }

                });

                // Handle the close button of the pause window (X button)
                pauseStage.setOnCloseRequest(e -> {
                        togglePause(null);
                        e.consume();
                });

                pauseStage.setScene(pauseScene);

        }

        /**
         * Updates the high score for a given user and game.
         * 
         * This method creates a new HighScore object with the provided username,
         * score, and the game name "Snake". It then adds this high score to the
         * HighScoresManager for tracking and persistence.
         * 
         * @param username The username of the player whose score is being updated.
         * @param score    The score achieved by the player.
         */
        private void updateHighScore(String username, int score) {
                HighScore highScore = new HighScore(username, score, "Snake");
                highScoresManager.addHighScores(username, highScore);
        }

        /**
         * Restarts the game when the user presses ENTER after the game over
         * screen appears.
         * 
         * This method creates a new Field and Snake and starts the game again by
         * calling the `start` method.
         * 
         * @param ps The Stage to display the game on.
         */
        private void restartGame(Stage ps) {
                f = new Field(width, height);
                f.addSnake(new Snake(il, f));

                start(ps); // Restart the game using the `start` method
        }

}
