package com.game.model.Snake;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class Snake {

    ArrayList<Block> blocks = new ArrayList<Block>();

    Block head;
    Block tail;

    public Snake(int il, Field f) {
        int ipx = f.getW() / 2;
        int ipy = f.getH() / 2;

        head = new Block(ipx, ipy, null, f);
        blocks.add(head);

        head.setFill(Color.PURPLE.desaturate());
        tail = head;

        for (int i = 1; i < il; i++) {
            Block b = new Block(ipx + i, ipy, tail, f );
            blocks.add(b);
            tail = b;

        }
    }

    public void setDirection(int d){
        head.direction = d;

    }
    public int getDirection(){
        return head.direction;
    }

} 
