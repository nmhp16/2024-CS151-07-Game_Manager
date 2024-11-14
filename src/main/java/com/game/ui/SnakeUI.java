package com.game.ui;

import com.game.model.Snake.Field;
import com.game.model.Snake.Snake;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



// TODO: Complete Snake game UI
public class SnakeUI extends Application {

    public static int block_size = 10;
    int width = 30, height = 15; 
    int il = 5; 

    @Override
    public void start(Stage ps) {
        VBox gameVBox = new VBox(10);
        gameVBox.setPadding(new Insets(10));

        Field f = new Field(width, height);
        f.addSnake(new Snake(il,f));

        AnimationTimer timer = new AnimationTimer(){
            public void handle(long now){
            f.update();
            }
        };
        timer.start();

        gameVBox.getChildren().add(f);

        Scene scene = new Scene(gameVBox);

        ps.setResizable(false);
        ps.setScene(scene);

        ps.setTitle("Snake Game");

        ps.show();


    }

}
