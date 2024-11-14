package com.game.model.SnakeGame;
import java.util.ArrayList;

import com.game.ui.SnakeUI;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;



public class Field extends Pane{
    private int w,h;

    ArrayList<Block> blocks = new ArrayList<>();
    Snake snake; 

    public Field(int width, int height) { 
        w = width;
        h = height; 

        setMinSize(w * SnakeUI.block_size, h * SnakeUI.block_size);
        setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));


    }
    
    public void addSnake(Snake s){
        snake = s; 
        for (Block b:s.blocks){
            addBlock(b);
        }
    }

    private void addBlock(Block b){
        getChildren().add(b);
        blocks.add(b);

    }



    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
    

