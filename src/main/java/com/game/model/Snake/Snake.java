package com.game.model.Snake;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class Snake {

    protected ArrayList<Block> blocks = new ArrayList<Block>();
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

}
