package com.game.model.Snake;
import com.game.ui.SnakeUI;

import javafx.scene.shape.Rectangle;


public class Block extends Rectangle{
    int posX, posY, oldPosX, oldPosY;

    public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;

    Block previous;

    
    int direction = LEFT;
    int maxX, maxY;

    public Block(int x, int y, Block p , Field f){
        super(SnakeUI.block_size, SnakeUI.block_size);
        posX = x;
        posY = y; 

        setTranslateX(posX * SnakeUI.block_size);
        setTranslateY(posY * SnakeUI.block_size);

        previous = p;
        maxX = f.getW();
        maxY = f.getH();

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
        updatePosition();
    }

    public void moveUp(){
        posY--;
        if(posY < 0) { 
            posY = maxY - 1;

        }
    }
    public void moveDown(){
        posY++;
        if ( posY >= maxY) { 
            posY = 0;
        }
    }
    public void moveLeft(){
        posX--;
        if (posX < 0){
            posX = maxX - 1;

        }
    }
    public void moveRight(){
        posX++;
        if (posX >= maxX){
            posX = 0;
        }
    }

    public void updatePosition(){
        setTranslateX(posX * SnakeUI.block_size);
        setTranslateY(posY * SnakeUI.block_size);

    }
}
