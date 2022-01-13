package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;


public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }


    public boolean checkEmptyField(CellType typeOfTile){
        return typeOfTile == CellType.FLOOR;
    }


    public void move(Cell nextCell) {
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int newHealth){
        health = newHealth;
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



    public void attack(Actor enemy){
        Actor player = cell.getGameMap().getPlayer();
        player.setHealth(player.getHealth() - 2);
        enemy.setHealth(enemy.getHealth() - 5);
        cell.getGameMap().checkDeath((Enemy)enemy);

    }

}
