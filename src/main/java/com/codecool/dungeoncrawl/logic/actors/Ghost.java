package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Ghost extends Enemy{


    public Ghost(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "Ghost";
    }

    @Override
    public void move() {

    }
}
