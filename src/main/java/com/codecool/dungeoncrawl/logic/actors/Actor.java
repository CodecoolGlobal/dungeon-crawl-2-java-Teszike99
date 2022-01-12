package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.GameMap;


public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }


    public boolean checkEmptyField(CellType typeOfTile, Actor enemy){
        return typeOfTile == CellType.FLOOR && enemy == null;
    }


    public void putActorOnMap(Cell nextCell) {
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
        Actor player = cell.getActor();
        if (enemy.getHealth() - 5 <= 0) {
            player.setHealth(player.getHealth()-2);
            cell.getGameMap().removeEnemy((Enemy)enemy);
            enemy.getCell().setActor(null);
        }else if (player.getHealth() - 2 <= 0){
            gameOver();
        }else{
            player.setHealth(player.getHealth() - 2);
            enemy.setHealth(enemy.getHealth() - 5);
        }
    }

    private void gameOver() {

    }
}
