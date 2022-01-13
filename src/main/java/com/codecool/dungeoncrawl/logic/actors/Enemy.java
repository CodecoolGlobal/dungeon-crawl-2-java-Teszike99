package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public abstract class Enemy extends Actor{


    public Enemy(Cell cell) {
        super(cell);
    }

    public abstract void move();

    public boolean checkAttack(Actor player){
        return player instanceof Player;
    }

    protected abstract int getStrength();

    public abstract int getHealth();

    protected abstract void setHealth(int newHealth);


}
