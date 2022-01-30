package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;


public abstract class Actor implements Drawable {
    private Cell cell;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public boolean checkEmptyField(CellType typeOfTile){
        return typeOfTile == CellType.FLOOR ||
                typeOfTile == CellType.STAIRS ||
                typeOfTile == CellType.OPENDOOR ||
                typeOfTile == CellType.GOAL;
    }

    public void move(Cell nextCell) {
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void attack(Actor attacking, Actor attacked){
        int attackedStrength = attacked.getStrength();
        int attackingStrength = attacking.getStrength();
        attacked.setHealth(attacked.getHealth() - attackingStrength);
        attacking.setHealth(attacking.getHealth() - attackedStrength);
        checkDeath(attacked);
        checkDeath(attacking);
    }

    private void checkDeath(Actor checkingOne) {
        if (checkingOne.getHealth() <= 0) {
            if (checkingOne instanceof Enemy){
                ((Enemy) checkingOne).setRemoveAble();
            }
            checkingOne.getCell().setActor(null);
        }
    }


    protected abstract int getStrength();

    public abstract int getHealth();

    protected abstract void setHealth(int newHealth);
}
