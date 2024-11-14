<<<<<<< HEAD:src/main/java/com/game/model/Snake/Field.java
package com.game.model.Snake;
=======
package com.game.model.SnakeGame;

>>>>>>> a56e1fdb6349a82193d0484eab8f675c55c7c694:src/main/java/com/game/model/SnakeGame/Field.java
import java.util.ArrayList;

import com.game.ui.SnakeUI;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Field extends Pane {
    private int w, h;

    ArrayList<Block> blocks = new ArrayList<>();
    Snake snake;

    public Field(int width, int height) {
        w = width;
        h = height;

        setMinSize(w * SnakeUI.block_size, h * SnakeUI.block_size);
        setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));

    }

    public void addSnake(Snake s) {
        snake = s;
        for (Block b : s.blocks) {
            addBlock(b);
        }
    }

<<<<<<< HEAD:src/main/java/com/game/model/Snake/Field.java
    public void update(){
        for (Block b:blocks){
            b.update();

    }

    }
    private void addBlock(Block b){
=======
    private void addBlock(Block b) {
>>>>>>> a56e1fdb6349a82193d0484eab8f675c55c7c694:src/main/java/com/game/model/SnakeGame/Field.java
        getChildren().add(b);
        blocks.add(b);

    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
