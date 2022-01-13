package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class LazyWitch extends Enemy{

    int strength = 3;


    public LazyWitch(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "LazyWitch";
    }

    @Override
    public void move() {

    }

    @Override
    protected int getStrength() {
        return strength;
    }
}
