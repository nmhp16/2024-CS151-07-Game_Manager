package com.game.ui;

import com.game.model.Snake.Block;
import com.game.model.Snake.Field;
import com.game.model.Snake.Snake;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;




public class SnakeUI extends Application {

        public static int block_size = 10;
        int width = 30, height = 15;
        int il = 5;

    long then = System.nanoTime();

    boolean changed = false;
    int nextUpdate;
    boolean hasNext = false;

    Field f;

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
                if (now - then > 100000000/2){
                        f.update();
                        then = now;
                        score.setText("Score: "+f.score);
                        changed = false;
                        if (hasNext) { 
                                setDirection(f.snake, nextUpdate);
                                hasNext = false;
                        }

                        if (f.isDead()){
                                stop();
                                Alert al = new Alert(AlertType.INFORMATION);
                                al.setHeaderText("YOUR SNAKE DIED");
                                al.setContentText("Score reached : "+f.score);
                                al.showAndWait();
                                Platform.runLater(al::showAndWait);

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

}
