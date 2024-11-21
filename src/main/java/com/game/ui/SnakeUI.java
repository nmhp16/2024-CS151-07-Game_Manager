package com.game.ui;

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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class SnakeUI extends Application {

        public static int block_size = 15;
        int width = 70, height = 30;
        int il = 2;

    private long then = System.nanoTime();

    private boolean changed = false;
    private  int nextUpdate;
    private boolean hasNext = false;

    Field f;

    public Stage pauseStage;
    private String username;
    boolean paused = false; 

    private HighScoresManager highScoresManager;


public SnakeUI(String username) {

        this.username = username; // Initialize the username
        this.highScoresManager = new HighScoresManager(); // Initialize highScoresManager
    }


    @Override
    public void start(Stage ps) {

        VBox gameVBox = new VBox(10);
        gameVBox.setPadding(new Insets(10));

        f = new Field(width, height);
        f.addSnake(new Snake(il,f));


        Label score = new Label("Score : 0");
        score.setFont(Font.font("tahoma", 32));
        //speed control for the snake
        AnimationTimer timer = new AnimationTimer(){
            public void handle(long now){
                if(paused) return; 
                if (now - then > 100000000/1.5){
                        f.update();
                        then = now;
                        score.setText("Score: "+f.score);
                        changed = false;
                        if (hasNext) { 
                                setDirection(f.snake, nextUpdate);
                                hasNext = false;
                        }

                        if (f.isDead()){
                                if (f.isDead()) {
                                        stop();
                                    
                                        int currentScore = f.getScore();
                                        updateHighScore(username, currentScore);
                                    
                                        displayGameOverScreen(currentScore, ps); // Replace Alert with a dedicated Game Over Screen
                                    }



                        
                        Alert al = new Alert(AlertType.INFORMATION);
                        al.setOnHidden(e->{
                                gameVBox.getChildren().clear();
                                f = new Field(width, height);
                                f.addSnake(new Snake(il, f));
                                score.setText("Score: 0");
                                gameVBox.getChildren().addAll(f,score);
                                start();

                        
                        });
                }
        }
            }
        };
        timer.start();

        gameVBox.getChildren().addAll(f, score);

                Scene scene = new Scene(gameVBox);

        
        scene.setOnKeyPressed(e -> {
                
                if (e.getCode() == KeyCode.ESCAPE){
                        initPausePopup(ps);
                        togglePause(timer);

                }
                if(e.getCode().equals(KeyCode.UP) && f.snake.getDirection() != Block.DOWN){
                        f.snake.setDirection(Block.UP);

                }
                if(e.getCode().equals(KeyCode.DOWN) && f.snake.getDirection() != Block.UP){
                        f.snake.setDirection(Block.DOWN);

                }
                if(e.getCode().equals(KeyCode.RIGHT) && f.snake.getDirection() != Block.LEFT){
                        f.snake.setDirection(Block.RIGHT);

                }
                if(e.getCode().equals(KeyCode.LEFT) && f.snake.getDirection() != Block.RIGHT){
                        f.snake.setDirection(Block.LEFT);

                }

                


        });
        ps.setResizable(false);
        ps.setScene(scene);

        ps.setTitle("Snake Game");

        ps.show();

        if (paused){
                Alert pause_alert = new Alert(AlertType.INFORMATION);
                pause_alert.setHeaderText("You Paused The Game");
                pause_alert.setContentText("Score reached : "+f.score);
                pause_alert.show();
        }




       
}


private void displayGameOverScreen(int finalScore, Stage ps) {
        VBox gameOverLayout = new VBox(10);
        gameOverLayout.setAlignment(Pos.CENTER);
        gameOverLayout.setPadding(new Insets(20));
    
        Label gameOverLabel = new Label("Game Over");
        gameOverLabel.setFont(Font.font("tahoma", 32));
    
        Label scoreLabel = new Label("Your Final Score: " + finalScore);
        scoreLabel.setFont(Font.font("tahoma", 20));
    
        Label restartInstruction = new Label("Press ENTER to restart");
        restartInstruction.setFont(Font.font("tahoma", 16));
    
        gameOverLayout.getChildren().addAll(gameOverLabel, scoreLabel, restartInstruction);
    
        Scene gameOverScene = new Scene(gameOverLayout, block_size * width, block_size * height);
        gameOverScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                restartGame(ps); // Restart the game when ENTER is pressed
            }
        });
    
        ps.setScene(gameOverScene);
        ps.show();
    }
    

        public void setDirection(Snake s, int d){ 
        if(!changed){
                s.setDirection(d);
                changed = true;
        }
        else { 
                hasNext = true;
                nextUpdate = d;
        }
        }


        private void togglePause(AnimationTimer timer){
                paused =!paused; 
                if(paused){
                        pauseStage.show();
                        System.out.println("You Have Paused the Game");
                
                }
                else { 
                        pauseStage.hide();
                        System.out.println("You have resumed the Game");
                        then = System.nanoTime();
                }
        }

        private void initPausePopup(Stage ps) {

                
                pauseStage = new Stage();
                pauseStage.initOwner(ps);
                pauseStage.initModality(Modality.WINDOW_MODAL);  // Makes the pause window modal
                pauseStage.setResizable(false);
                pauseStage.setTitle("Paused");
        
                Label pauseLabel = new Label("Game Paused");
                pauseLabel.setFont(Font.font("tahoma", 24));
        
                VBox pauseLayout = new VBox(pauseLabel);
                pauseLayout.setSpacing(20);
                pauseLayout.setPrefSize(200, 100);

                pauseLayout.setAlignment(Pos.CENTER);
                
                int currentScore = f.getScore();
                updateHighScore(username, currentScore);
                
                Scene pauseScene = new Scene(pauseLayout);
                pauseScene.setOnKeyPressed(e -> {
                        

                        if (e.getCode() == KeyCode.ESCAPE){
                                togglePause(null);
                        }       
                });

                
                pauseStage.setScene(pauseScene);
                
            }

        private void updateHighScore(String username, int score) {

                String gameName = "Snake";
                HighScore highScore = new HighScore(username, score, gameName);
                highScoresManager.addHighScores(username, highScore);
        }

        private void restartGame(Stage ps) {
                f = new Field(width, height);
                f.addSnake(new Snake(il, f));
            
                start(ps); // Restart the game using the `start` method
            }
            

}


