package com.game.model.Snake;

import com.game.ui.SnakeUI;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {
    private int posX, posY, oldPosX, oldPosY;
    public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
    protected Block previous, next;
    protected int direction = LEFT;
    private int maxX, maxY;

    // Constructor
    public Block(int x, int y, Block p, Block n, Field f) {
        super(SnakeUI.block_size, SnakeUI.block_size);
        this.posX = x;
        this.posY = y;
        setTranslateX(posX * SnakeUI.block_size);
        setTranslateY(posY * SnakeUI.block_size);
        this.previous = p;
        this.next = n;
        this.maxX = f.getW();
        this.maxY = f.getH();
    }

    /**
     * Sets the direction of this block.
     * 
     * @param direction The direction to set the block to. Should be one of the
     *                  constants defined in the Block class.
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * Updates the position of the block based on its direction.
     * If the block has no previous block, it will move one block in its
     * current direction. Otherwise, it will move to the previous block's
     * old position.
     */
    public void update() {
        oldPosX = posX;
        oldPosY = posY;

        if (previous == null) {
            switch (direction) {
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
        } else {
            posX = previous.oldPosX;
            posY = previous.oldPosY;
        }
        updatePosition();
    }

    /**
     * Moves the block up by one position, or does nothing if the block is
     * already at the top of the grid.
     */
    public void moveUp() {
        posY--;
        if (posY < 0) {
            posY = 0;
        }
    }

    /**
     * Moves the block down by one position, or does nothing if the block is
     * already at the bottom of the grid.
     */
    public void moveDown() {
        posY++;
        if (posY >= maxY) {
            posY = maxY - 1;

        }
    }

    /**
     * Moves the block left by one position, or does nothing if the block is
     * already at the left edge of the grid.
     */
    public void moveLeft() {
        posX--;
        if (posX < 0) {
            posX = 0;
        }
    }

    /**
     * Moves the block right by one position, or does nothing if the block is
     * already at the right edge of the grid.
     */
    public void moveRight() {
        posX++;
        if (posX >= maxX) {
            posX = maxX - 1;
        }
    }

    /**
     * Updates the position of the block to the new position specified by the
     * posx and posy fields. The block is moved to the new position by setting
     * its translateX and translateY properties.
     */
    public void updatePosition() {
        setTranslateX(posX * SnakeUI.block_size);
        setTranslateY(posY * SnakeUI.block_size);
    }

    /**
     * Returns the x-coordinate of the block on the grid.
     * 
     * @return The x-coordinate of the block.
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Returns the y-coordinate of the block on the grid.
     * 
     * @return The y-coordinate of the block.
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Returns the x-coordinate of the block on the previous game loop iteration.
     * 
     * @return The x-coordinate of the block on the previous game loop iteration.
     */
    public int getOldPosX() {
        return oldPosX;
    }

    /**
     * Returns the y-coordinate of the block on the previous game loop iteration.
     * 
     * @return The y-coordinate of the block on the previous game loop iteration.
     */
    public int getOldPosY() {
        return oldPosY;
    }

    /**
     * Sets the x-coordinate of the block on the grid.
     * 
     * @param x The x-coordinate of the block on the grid.
     */
    public void setPosX(int x) {
        this.posX = x;
    }

    /**
     * Sets the y-coordinate of the block on the grid.
     * 
     * @param y The y-coordinate of the block on the grid.
     */
    public void setPosY(int y) {
        this.posY = y;
    }

    /**
     * Increments the x-coordinate of the block by the given amount.
     * 
     * @param x The amount to increment the x-coordinate by.
     */
    public void addPosX(int x) {
        this.posX += x;
    }

    /**
     * Increments the y-coordinate of the block by the given amount.
     * 
     * @param y The amount to increment the y-coordinate by.
     */
    public void addPosY(int y) {
        this.posY += y;
    }

    /**
     * Returns the current direction of the block.
     * 
     * @return The direction of the block, represented as one of the
     *         constants defined in the Block class (UP, RIGHT, DOWN, LEFT).
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Returns the block that is connected to this block in the direction
     * that this block is pointing. If this block is not pointing at any
     * other block, this method returns null.
     * 
     * @return The block that this block is pointing at, or null if there is
     *         no such block.
     */
    public Block getNext() {
        return next;
    }

    /**
     * Returns the block that is connected to this block in the opposite
     * direction to the one that this block is pointing. If this block is
     * not pointing at any other block, this method returns null.
     * 
     * @return The block that this block is pointing away from, or null if
     *         there is no such block.
     */
    public Block getPrevious() {
        return previous;
    }

    /**
     * Gets the maximum x-coordinate of the block's position on the grid.
     * 
     * @return The maximum x-coordinate of the block's position on the grid.
     */
    public int getMaxX() {
        return maxX;
    }

    /**
     * Gets the maximum y-coordinate of the block's position on the grid.
     * 
     * @return The maximum y-coordinate of the block's position on the grid.
     */
    public int getMaxY() {
        return maxY;
    }
}
