package com.game.model.Snake;
import com.game.ui.SnakeUI;

import javafx.scene.shape.Rectangle;


public class Block extends Rectangle{
    int posX, posY, oldPosX, oldPosY;

    static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;

    Block previous;

    int direction = LEFT;

    public Block(int x, int y, Block p){
        super(SnakeUI.block_size, SnakeUI.block_size);
        posX = x;
        posY = y; 

        setTranslateX(posX * SnakeUI.block_size);
        setTranslateY(posY * SnakeUI.block_size);

        previous = p;
    }


    public void update(){
        
        oldPosX = posX;
        oldPosY = posY;
        if (previous == null){
            switch (direction){
                case UP:
                    moveUp();
                    break;
                case RIGHT: 
                    moveRight();
                    break;
                case DOWN: 
                    moveDown();
                    break;
                case LEFT: 
                    moveLeft();
                    break;
            }


        }
        else { 
            posX = previous.oldPosX;
            posY = previous.oldPosY;

        }
    }

    public void moveUp(){
        posY--;
    }
    public void moveDown(){
        posY++;
    }
    public void moveLeft(){
        posX--;
    }
    public void moveRight(){
        posX++;
    }
}
