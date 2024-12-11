package com.game.model;

import com.game.model.Snake.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FieldTest extends Application {

    private static boolean initialized = false;

    @BeforeAll
    public static void initJavaFX() {
        if (!initialized) {
            // Launch a minimal JavaFX application to initialize the toolkit
            Thread javafxThread = new Thread(() -> {
                Application.launch(FieldTest.class);
            });
            javafxThread.setDaemon(true); // Set the thread as daemon so it doesn't block test completion
            javafxThread.start();
            initialized = true;
        }
    }

    @Override
    public void start(Stage stage) {
        Platform.runLater(() -> {

        });
    }

    @Test
    public void testGetH() {
        Field field = new Field(10, 10);
        assertEquals(10, field.getH());
    }

    @Test
    public void testGetW() {
        Field field = new Field(10, 10);
        assertEquals(10, field.getW());
    }

    @Test
    public void testGetSnake() {
        Field field = new Field(10, 10);
        Snake snake = new Snake(10, field);
        field.addSnake(snake);
        assertEquals(snake, field.getSnake());
    }

    @Test
    public void testGetBlocks() {
        Field field = new Field(10, 10);
        Snake snake = new Snake(10, field);
        field.addSnake(snake);
        assertEquals(snake.getBlocks(), field.getSnake().getBlocks());
    }

    @Test
    public void testAddSnake() {
        Field field = new Field(10, 10);
        Snake snake = new Snake(10, field);
        field.addSnake(snake);
        assertEquals(snake, field.getSnake());
    }

    @Test
    public void testIsDead() {
        Field field = new Field(10, 10);
        Snake snake = new Snake(10, field);
        field.addSnake(snake);
        assertEquals(false, field.isDead());
    }

    @Test
    public void testIsEaten() {
        Field field = new Field(10, 10);
        Snake snake = new Snake(10, field);
        field.addSnake(snake);
        assertEquals(false, field.isEaten(null));
    }

    @Test
    public void testAddFood() {
        Field field = new Field(10, 10);
        Snake snake = new Snake(10, field);
        field.addSnake(snake);
        field.addFood();
        assertEquals(false, field.isEaten(null));
    }

    @Test
    public void testGetFood() {
        Field field = new Field(10, 10);
        Snake snake = new Snake(10, field);
        field.addSnake(snake);
        snake.grow(field);
        assertEquals(21, field.getSnake().getBlocks().size());

    }

    @Test
    public void testSetScore() {
        Field field = new Field(10, 10);
        Snake snake = new Snake(10, field);
        field.addSnake(snake);
        field.setScore(10);
        assertEquals(10, field.getScore());
    }

    @Test
    public void testGetScore() {
        Field field = new Field(10, 10);
        Snake snake = new Snake(10, field);
        field.addSnake(snake);
        field.setScore(10);
        assertEquals(10, field.getScore());
    }
}
