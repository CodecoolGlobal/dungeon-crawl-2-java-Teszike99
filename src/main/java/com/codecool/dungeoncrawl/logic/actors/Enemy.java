package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public abstract class Enemy extends Actor{
    boolean removeAble = false;


    public Enemy(Cell cell) {
        super(cell);
    }

    public abstract void move();

    public boolean checkAttack(Actor player){
        return player instanceof Player;
    }
    public boolean checkEnemy(Actor enemy){
        return enemy instanceof Enemy;
    }

    public abstract int getStrength();

    public abstract int getHealth();

    public abstract void setHealth(int newHealth);

    public boolean isRemoveAble() {
        return removeAble;
    }

    public void setRemoveAble() {
        this.removeAble = true;
    }
}
