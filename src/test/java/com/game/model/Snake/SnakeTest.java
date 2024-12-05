package com.game.model.Snake;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SnakeTest {

    @BeforeAll
    static void initJavaFX() {
        Platform.startup(() -> {
        }); // Initialize JavaFX toolkit
    }

    @Test
    void testConstructor() {
        int initialLength = 5;
        Field field = new Field(20, 20);
        Snake snake = new Snake(initialLength, field);

        // Test head and tail initialization
        assertNotNull(snake.getHead());
        assertNotNull(snake.getTail());

        // Test initial block list size
        assertEquals(initialLength, snake.getBlocks().size());

        // Test head's position and color
        Block head = snake.getHead();
        assertEquals(field.getW() / 2, head.getPosX());
        assertEquals(field.getH() / 2, head.getPosY());
        assertEquals(Color.PURPLE.desaturate(), head.getFill());

        // Test tail is correctly set to the last block
        Block tail = snake.getTail();
        assertEquals(head.getPosX() + initialLength - 1, tail.getPosX());
        assertEquals(head.getPosY(), tail.getPosY());

        // Test the chain of blocks
        Block current = head;
        for (int i = 1; i < initialLength; i++) {
            assertNotNull(current.getNext());
            current = current.getNext();
        }
        assertNull(current.getNext()); // Tail has no next block
    }

    @Test
    void testSetDirection() {
        int initialLength = 5;
        Field field = new Field(20, 20);
        Snake snake = new Snake(initialLength, field);

        // Test initial direction
        assertEquals(Block.RIGHT, snake.getDirection());

        // Change direction to UP
        snake.setDirection(Block.UP);
        assertEquals(Block.UP, snake.getDirection());

        // Verify all blocks have the updated direction
        for (Block block : snake.getBlocks()) {
            assertEquals(Block.UP, block.getDirection());
        }
    }

    @Test
    void testGetDirection() {
        int initialLength = 3;
        Field field = new Field(10, 10);
        Snake snake = new Snake(initialLength, field);

        // Test default direction
        assertEquals(Block.RIGHT, snake.getDirection());

        // Change direction and test
        snake.setDirection(Block.LEFT);
        assertEquals(Block.LEFT, snake.getDirection());
    }

    @Test
    void testSnakeIntegrityAfterDirectionChange() {
        int initialLength = 4;
        Field field = new Field(15, 15);
        Snake snake = new Snake(initialLength, field);

        // Save initial block positions
        List<Block> blocks = snake.getBlocks();
        int[] initialX = blocks.stream().mapToInt(Block::getPosX).toArray();
        int[] initialY = blocks.stream().mapToInt(Block::getPosY).toArray();

        // Change direction to UP
        snake.setDirection(Block.UP);

        // Ensure all blocks retain their positions after changing direction
        for (int i = 0; i < blocks.size(); i++) {
            assertEquals(initialX[i], blocks.get(i).getPosX());
            assertEquals(initialY[i], blocks.get(i).getPosY());
        }
    }
}
