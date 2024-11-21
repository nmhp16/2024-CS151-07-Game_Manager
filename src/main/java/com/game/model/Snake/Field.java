package com.game.model.Snake;

import java.io.File;
import java.util.ArrayList;
import com.game.ui.SnakeUI;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Field extends Canvas {
    private int w, h;
    private ArrayList<Block> blocks = new ArrayList<>();
    private Food f;
    private int score = 0;
    private Snake snake;
    private GraphicsContext gc;

    // Images for snake
    private Image headUp, headDown, headLeft, headRight, bodyHorizontal, bodyVertical, bodyTopLeft, bodyTopRight,
            bodyBottomLeft, bodyBottomRight, tailUp, tailDown, tailLeft, tailRight;
    private Image food; // Image for food

    // Constructor
    public Field(int width, int height) {
        this.w = width;
        this.h = height;

        setWidth(w * SnakeUI.block_size);
        setHeight(h * SnakeUI.block_size);

        // Initialize the canvas and graphics context
        gc = getGraphicsContext2D();

        // Load images
        loadImages();

        // Draw grid
        drawGrid();

        // Add food initially
        addFood();
    }

    /**
     * Loads the images for the snake body and food.
     */
    private void loadImages() {
        String basePath = "images-snake/";
        headUp = new Image(new File(basePath + "head_up.png").toURI().toString());
        headDown = new Image(new File(basePath + "head_down.png").toURI().toString());
        headLeft = new Image(new File(basePath + "head_left.png").toURI().toString());
        headRight = new Image(new File(basePath + "head_right.png").toURI().toString());
        bodyHorizontal = new Image(new File(basePath + "body_horizontal.png").toURI().toString());
        bodyVertical = new Image(new File(basePath + "body_vertical.png").toURI().toString());
        bodyTopLeft = new Image(new File(basePath + "body_tl.png").toURI().toString());
        bodyTopRight = new Image(new File(basePath + "body_tr.png").toURI().toString());
        bodyBottomLeft = new Image(new File(basePath + "body_bl.png").toURI().toString());
        bodyBottomRight = new Image(new File(basePath + "body_br.png").toURI().toString());
        tailUp = new Image(new File(basePath + "tail_up.png").toURI().toString());
        tailDown = new Image(new File(basePath + "tail_down.png").toURI().toString());
        tailLeft = new Image(new File(basePath + "tail_left.png").toURI().toString());
        tailRight = new Image(new File(basePath + "tail_right.png").toURI().toString());
        food = new Image(new File(basePath + "apple.png").toURI().toString());
    }

    /**
     * Draws a grid on the game board. The grid is drawn with light gray lines, with
     * each cell being SnakeUI.block_size x SnakeUI.block_size in size. The grid is
     * centered on the game board.
     */
    private void drawGrid() {
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(1);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                gc.strokeRect(i * SnakeUI.block_size, j * SnakeUI.block_size, SnakeUI.block_size, SnakeUI.block_size);
            }
        }
    }

    /**
     * Draws the food on the game board at the position specified by the f (Food)
     * object. The image is drawn at the position of the food object, scaled to
     * SnakeUI.block_size x SnakeUI.block_size. The image is centered on the
     * position of the food object.
     */
    private void drawFood() {
        gc.drawImage(food, f.getPosX() * SnakeUI.block_size, f.getPosY() * SnakeUI.block_size, SnakeUI.block_size,
                SnakeUI.block_size);
    }

    /**
     * Draws the game board, including the grid, snake, and food.
     * <p>
     * This method is called by the game loop to update the game board.
     */
    private void draw() {
        // Clear the canvas
        gc.clearRect(0, 0, getWidth(), getHeight());

        // Draw grid
        drawGrid();

        // Draw the snake
        for (int index = 0; index < blocks.size(); ++index) {
            Block b = blocks.get(index);
            drawBlock(b); // Call drawBlock for each block
        }

        // Draw food
        drawFood();
    }

    /**
     * Returns the image for the snake's head based on its current direction.
     * Uses the direction of the given block to determine the appropriate head
     * image.
     *
     * @param b The block representing the snake's head. Its direction field
     *          is used to select the correct image.
     * @return The Image object corresponding to the snake's head facing in its
     *         current direction. Returns null if the direction is invalid.
     */
    private Image getHeadImage(Block b) {
        // Return the head image based on direction
        switch (b.direction) {
            case Block.UP:
                return headUp;
            case Block.DOWN:
                return headDown;
            case Block.LEFT:
                return headLeft;
            case Block.RIGHT:
                return headRight;
            default:
                return null;
        }
    }

    /**
     * Returns the image for the snake's tail based on its direction and position.
     * Determines the tail image by calculating the direction vector between the
     * tail and its previous block. It handles both straight and corner connections.
     *
     * @param tail The tail block of the snake.
     * @return The Image object representing the tail's appearance. Returns null if
     *         the tail has no previous block or if no specific image is determined.
     */
    private Image getTailImage(Block tail) {
        if (tail.previous == null) {
            return null; // No previous block means no tail
        }

        // Calculate direction vector for the previous block relative to the tail
        int dxPrev = tail.previous.getPosX() - tail.getPosX();
        int dyPrev = tail.previous.getPosY() - tail.getPosY();

        // Handle straight connections:
        if (dxPrev == 0 && dyPrev == -1) {
            // Tail is at the top, facing down
            return tailDown;
        }
        if (dxPrev == 0 && dyPrev == 1) {
            // Tail is at the bottom, facing up
            return tailUp;
        }
        if (dxPrev == -1 && dyPrev == 0) {
            // Tail is to the left, facing right
            return tailRight;
        }
        if (dxPrev == 1 && dyPrev == 0) {
            // Tail is to the right, facing left
            return tailLeft;
        }

        // Handle corner cases (when the tail is at a corner or turning):
        // Top-left corner (turning down and right)
        if (dxPrev == -1 && dyPrev == -1) {
            return tailDown; // Tail turning down-right
        }
        // Top-right corner (turning down and left)
        if (dxPrev == 1 && dyPrev == -1) {
            return tailDown; // Tail turning down-left
        }
        // Bottom-left corner (turning up and right)
        if (dxPrev == -1 && dyPrev == 1) {
            return tailUp; // Tail turning up-right
        }
        // Bottom-right corner (turning up and left)
        if (dxPrev == 1 && dyPrev == 1) {
            return tailUp; // Tail turning up-left
        }

        return null; // Fallback for any unhandled cases
    }

    /**
     * Returns the correct image for the given body segment based on its connection
     * to
     * the previous and next blocks. If the block is not connected to any other
     * blocks
     * or if it is neither the head nor the tail, this method returns null.
     *
     * @param b The block to get the image for
     * @return The correct image for the body segment or null if it can't be
     *         determined
     */
    private Image getBodyImage(Block b) {
        // If the block has no previous or next, it can't form a valid body connection
        if (b.previous == null && b.next == null) {
            return null; // Block is neither head nor tail
        }

        // Handle standard body segments (both previous and next blocks exist)
        if (b.previous != null && b.next != null) {
            // Check if the body segment is vertical or horizontal
            if (b.previous.getPosX() == b.getPosX() && b.next.getPosX() == b.getPosX()) {
                return bodyVertical;
            }
            if (b.previous.getPosY() == b.getPosY() && b.next.getPosY() == b.getPosY()) {
                return bodyHorizontal;
            }

            // Calculate direction vectors for previous and next blocks
            int dxPrev = b.previous.getPosX() - b.getPosX();
            int dyPrev = b.previous.getPosY() - b.getPosY();
            int dxNext = b.next.getPosX() - b.getPosX();
            int dyNext = b.next.getPosY() - b.getPosY();

            // Handle corners:
            // Top-left corner
            if ((dxPrev == 0 && dyPrev == -1 && dxNext == -1 && dyNext == 0) ||
                    (dxPrev == -1 && dyPrev == 0 && dxNext == 0 && dyNext == -1)) {
                return bodyTopLeft;
            }
            // Top-right corner
            if ((dxPrev == 0 && dyPrev == -1 && dxNext == 1 && dyNext == 0) ||
                    (dxPrev == 1 && dyPrev == 0 && dxNext == 0 && dyNext == -1)) {
                return bodyTopRight;
            }
            // Bottom-left corner
            if ((dxPrev == 0 && dyPrev == 1 && dxNext == -1 && dyNext == 0) ||
                    (dxPrev == -1 && dyPrev == 0 && dxNext == 0 && dyNext == 1)) {
                return bodyBottomLeft;
            }
            // Bottom-right corner
            if ((dxPrev == 0 && dyPrev == 1 && dxNext == 1 && dyNext == 0) ||
                    (dxPrev == 1 && dyPrev == 0 && dxNext == 0 && dyNext == 1)) {
                return bodyBottomRight;
            }
        }

        // Fallback for any unhandled cases
        return null;
    }

    /**
     * Adds a Snake to the Field and all its blocks to the blocks list.
     * 
     * @param s The Snake to be added.
     */
    public void addSnake(Snake s) {
        this.snake = s;
        for (Block b : s.blocks) {
            addBlock(b);
        }
    }

    /**
     * Updates the game state and redraws the game.
     * 
     * This method will update the position of each block in the snake, move the
     * head of the snake based on its current direction, and check if the snake
     * has eaten the food. If the snake has eaten the food, it will add a new
     * block to the snake to extend it, and add new food to the game.
     */
    public void update() {
        // Move each block (starting from the tail to the head)
        for (int i = blocks.size() - 1; i > 0; i--) {
            Block current = blocks.get(i);
            Block previous = blocks.get(i - 1);
            current.setPosX(previous.getPosX());
            current.setPosY(previous.getPosY());
            current.direction = previous.direction; // Update direction
            current.previous = previous;
            if (i < blocks.size() - 1) {
                current.next = blocks.get(i + 1);
            }
        }

        // Move the head based on its current direction
        Block head = snake.head;
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

        head.previous = blocks.get(1);

        // If the snake eats food
        if (isEaten(f)) {
            score += 20;
            addFood(); // Add new food
            addNewBlock(); // Add a new block at the tail to extend the snake
        }

        draw(); // Redraw the game
    }

    /**
     * Draws a single block of the snake on the game field. Determines the type
     * of block (head, tail, or body) and selects the appropriate image to
     * represent it. If no specific image is assigned, the block is drawn as a
     * green rectangle.
     * 
     * @param b The block to be drawn, which is part of the snake.
     */
    private void drawBlock(Block b) {
        Image image = null;

        // Check the type of block (head, tail, or body)
        if (b == snake.head) {
            image = getHeadImage(b); // Head image
        } else if (b == snake.tail) {
            image = getTailImage(b); // Tail image
        } else {
            image = getBodyImage(b); // Body image
        }

        // Draw the block's image or color
        if (image != null) {
            if (b == snake.head) {
                gc.drawImage(image, b.getPosX() * SnakeUI.block_size, b.getPosY() * SnakeUI.block_size,
                        SnakeUI.block_size, SnakeUI.block_size);
            } else {
                gc.drawImage(image, b.getPosX() * SnakeUI.block_size, b.getPosY() * SnakeUI.block_size,
                        SnakeUI.block_size, SnakeUI.block_size);
            }
        }
    }

    /**
     * Checks if the snake is dead. The snake is considered dead if its head is out
     * of bounds or if its head is at the same position as one of its body segments.
     * 
     * @return true if the snake is dead, false otherwise
     */
    public boolean isDead() {
        if (snake.head.getPosX() < 0 || snake.head.getPosX() >= w || snake.head.getPosY() < 0
                || snake.head.getPosY() >= h) {
            return true;
        }

        for (Block b : blocks) {
            if (b != snake.head) {
                if (b.getPosX() == snake.head.getPosX() && b.getPosY() == snake.head.getPosY()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Adds a new block to the snake by creating a new block at the position of the
     * current tail and setting the tail to the new block. The new block is added
     * to the list of blocks.
     */
    public void addNewBlock() {
        Block b = new Block(snake.tail.getOldPosX(), snake.tail.getOldPosY(), snake.tail, null, this);
        snake.tail.next = b;
        snake.tail = b;
        addBlock(b);
    }

    private void addBlock(Block b) {
        blocks.add(b);
    }

    /**
     * Adds a new Food block to the field at a random position. The Food block
     * is added to the field and the new Food block is stored in the field's
     * food field.
     */
    public void addFood() {
        int randomX = (int) (Math.random() * w);
        int randomY = (int) (Math.random() * h);

        Food food = new Food(randomX, randomY);
        f = food;
    }

    /**
     * Checks if the snake has eaten the given food block. The snake has eaten
     * the food block if the head of the snake is at the same position as the
     * food block.
     * 
     * @param f The Food block to check
     * @return true if the snake has eaten the food block, false otherwise
     */
    public boolean isEaten(Food f) {
        if (f == null) {
            return false;
        }
        return f.getPosX() == snake.head.getPosX() && f.getPosY() == snake.head.getPosY();
    }

    /**
     * Gets the width of the field.
     * 
     * @return The width of the field
     */
    public int getW() {
        return w;
    }

    /**
     * Gets the height of the field.
     * 
     * @return The height of the field
     */
    public int getH() {
        return h;
    }

    /**
     * Gets the current score of the snake game.
     * 
     * @return The current score of the snake game
     */
    public int getScore() {
        return score;
    }

    /**
     * Retrieves the current snake object in the field.
     *
     * @return The Snake object representing the snake on the field.
     */
    public Snake getSnake() {
        return snake;
    }
}
