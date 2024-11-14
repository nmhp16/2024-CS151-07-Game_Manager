package com.game.model.SnakeGame;

import java.util.ArrayList;

public class Snake {

    ArrayList<Block> blocks = new ArrayList<Block>();

    Block head;

    public Snake(int il, Field f) {
        int ipx = f.getW() / 2;
        int ipy = f.getH() / 2;

        head = new Block(ipx, ipy, null);

        Block previous = head;

        for (int i = 1; i < il; i++) {
            Block b = new Block(ipx + i, ipy, previous);
            blocks.add(b);
            previous = b;

        }
    }
}