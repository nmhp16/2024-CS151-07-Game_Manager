package com.game.model.Snake;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockTest {

    @BeforeAll
    static void initJavaFX() {
        Platform.startup(() -> {
        }); // Initialize JavaFX toolkit
    }

    @Test
    void testConstructor() {
        Field field = new Field(10, 10);
        Block block = new Block(0, 0, null, null, field);

        assertEquals(0, block.getPosX());
        assertEquals(0, block.getPosY());
        assertEquals(0, block.getOldPosX());
        assertEquals(0, block.getOldPosY());
        assertEquals(25, block.getWidth());
        assertEquals(25, block.getHeight());
        assertEquals(0, block.getTranslateX());
        assertEquals(0, block.getTranslateY());
        assertTrue(block.getPrevious() == null);
        assertTrue(block.getNext() == null);
        assertEquals(10, block.getMaxX());
        assertEquals(10, block.getMaxY());
    }

    @Test
    void testSetDirection() {
        Block block = new Block(0, 0, null, null, new Field(10, 10));
        block.setDirection(Block.UP);
        assertEquals(Block.UP, block.getDirection());
    }

    @Test
    void testUpdate() {
        Block block = new Block(0, 0, null, null, new Field(10, 10));
        block.update();
        assertEquals(0, block.getOldPosX());
        assertEquals(0, block.getOldPosY());
        assertEquals(0, block.getPosX());
        assertEquals(0, block.getPosY());
        assertEquals(0, block.getTranslateX());
        assertEquals(0, block.getTranslateY());
    }

    @Test
    void testMoveUp() {
        Block block = new Block(0, 0, null, null, new Field(45, 25));
        block.moveUp();
        assertEquals(0, block.getPosY()); // Assuming moveUp decrements posY
        assertEquals(0, block.getTranslateY());
    }

    @Test
    void testMoveDown() {
        Block block = new Block(0, 0, null, null, new Field(45, 25));
        block.moveDown();
        assertEquals(1, block.getPosY()); // Assuming moveDown increments posY
        assertEquals(0.0, block.getTranslateY());
    }

    @Test
    void testMoveLeft() {
        Block block = new Block(0, 0, null, null, new Field(45, 25));
        block.moveLeft();
        assertEquals(0, block.getPosX()); // Assuming moveLeft decrements posX
        assertEquals(0, block.getTranslateX());
    }

    @Test
    void testMoveRight() {
        Block block = new Block(0, 0, null, null, new Field(10, 10));
        block.moveRight();
        assertEquals(1, block.getPosX()); // Assuming moveRight increments posX
        assertEquals(0, block.getTranslateX());
    }

    @Test
    void testUpdatePosition() {
        Block block = new Block(1, 1, null, null, new Field(10, 10));
        block.updatePosition();
        assertEquals(1 * 25, block.getTranslateX());
        assertEquals(1 * 25, block.getTranslateY());
    }
}
