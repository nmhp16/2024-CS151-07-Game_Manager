package com.game.model.SnakeGame;

import javafx.scene.shape.Rectangle;
import com.game.ui.SnakeUI; // Ensure SnakeUI is imported

public class Block extends Rectangle {
    int posX, posY, oldPosX, oldPosY;

    Block previous;

    public Block(int x, int y, Block p) {
        super(SnakeUI.block_size, SnakeUI.block_size);
        posX = x;
        posY = y;

        setTranslateX(posX * SnakeUI.block_size);
        setTranslateY(posY * SnakeUI.block_size);

    }

}