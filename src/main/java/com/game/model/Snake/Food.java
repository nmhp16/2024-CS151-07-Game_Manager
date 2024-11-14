package com.game.model.Snake;

import com.game.ui.SnakeUI;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food extends Rectangle{
    int posX, posY;

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Food (int x, int y){
        super (SnakeUI.block_size, SnakeUI.block_size);
        posX = x;
        posY = y;

        setTranslateX(posX * SnakeUI.block_size);
        setTranslateY(posY * SnakeUI.block_size);


        setFill(Color.YELLOW);
        setStroke(Color.PINK);

    }
    
}
