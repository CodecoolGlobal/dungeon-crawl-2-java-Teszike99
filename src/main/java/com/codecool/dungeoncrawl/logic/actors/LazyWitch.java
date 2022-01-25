package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class LazyWitch extends Enemy{

    int strength = 3;
    int health;


    public LazyWitch(Cell cell) {
        super(cell);
        health = 15;
    }

    @Override
    public String getTileName() {
        return "LazyWitch";
    }

    @Override
    public void move() {
    }

    @Override
    public int getStrength() {
        return strength;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    protected void setHealth(int newHealth) {
        health = newHealth;
    }
}
