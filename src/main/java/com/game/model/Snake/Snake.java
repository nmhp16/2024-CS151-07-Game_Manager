package com.game.model.Snake;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.scene.paint.Color;

public class Snake {

    protected List<Block> blocks = new CopyOnWriteArrayList<Block>();
    protected Block head;
    protected Block tail;

    public Snake(int il, Field f) {
        int ipx = f.getW() / 2;
        int ipy = f.getH() / 2;

        head = new Block(ipx, ipy, null, null, f);
        blocks.add(head);

        head.setFill(Color.PURPLE.desaturate());
        tail = head;

        Block previous = head;

        for (int i = 1; i < il; i++) {
            Block b = new Block(ipx + i, ipy, previous, null, f);
            blocks.add(b);
            previous.next = b;
            previous = b;
            tail = b;
        }

        // Set direction for the head
        head.setDirection(Block.RIGHT);
    }

    /**
     * Sets the direction of the snake to the specified value.
     * 
     * This method updates the direction of the head block and propagates
     * the new direction to all blocks in the snake.
     * 
     * @param d The new direction to set for the snake. It should be one
     *          of the constants defined in the Block class.
     */
    public void setDirection(int d) {
        head.direction = d;

        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).setDirection(d);
        }

    }

    /**
     * Retrieves the current direction of the snake's head.
     *
     * @return The direction of the head block, represented as one of the
     *         constants defined in the Block class (UP, RIGHT, DOWN, LEFT).
     */
    public int getDirection() {
        return head.direction;
    }

    /**
     * Retrieves the head block of the snake.
     * 
     * @return The head block of the snake.
     */
    public Block getHead() {
        return head;
    }

    /**
     * Retrieves the tail block of the snake.
     * 
     * @return The tail block of the snake.
     */
    public Block getTail() {
        return tail;
    }

    /**
     * Retrieves the list of all blocks that make up the snake.
     * 
     * @return A list of all the blocks that make up the snake.
     */
    public List<Block> getBlocks() {
        return blocks;
    }

    /**
     * Grows the snake by adding a new block to the tail.
     * 
     * This method creates a new block with the same position as the tail's
     * previous position, and sets that block as the new tail. It adds the
     * new block to the list of blocks that make up the snake.
     * 
     * @param field The field that the snake is living in.
     */
    public void grow(Field field) {
        Block b = new Block(tail.getOldPosX(), tail.getOldPosY(), tail, null, field);
        tail.next = b;
        tail = b;
        blocks.add(b);
    }

    /**
     * Adds a block to the list of blocks that make up the snake.
     * 
     * @param b The block to be added to the snake.
     */
    public void addBlock(Block b) {
        blocks.add(b);
    }

    /**
     * Moves the snake by shifting all its blocks to the position of the
     * previous block, and then moving the head block to its new position
     * based on its current direction.
     */
    public void moveSnake() {
        for (int i = blocks.size() - 1; i > 0; i--) {
            Block current = blocks.get(i);
            Block previous = blocks.get(i - 1);
            current.setPosX(previous.getPosX());
            current.setPosY(previous.getPosY());
            current.direction = previous.direction;
            current.previous = previous;
        }

        // Move the head
        switch (head.direction) {
            case Block.UP:
                head.addPosY(-1);
                break;
            case Block.DOWN:
                head.addPosY(1);
                break;
            case Block.LEFT:
                head.addPosX(-1);
                break;
            case Block.RIGHT:
                head.addPosX(1);
                break;
        }
    }
}
