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

    public boolean checkMove(int moveX, int moveY, Actor actor){
        Cell nextCell = cell.getNeighbor(moveX, moveY);
        CellType typeOfTile = nextCell.getType();
        Actor enemy = nextCell.getActor();
        if (typeOfTile == CellType.FLOOR && enemy == null) {
            putActorOnMap(nextCell);
            return false;
        }else if (typeOfTile == CellType.FLOOR && enemy.getTileName().equals("skeleton") && actor.getTileName().equals("player")) {
            attack(enemy);
            putActorOnMap(nextCell);
            return false;
        }else {
            return true;
        }
    }

    private void putActorOnMap(Cell nextCell) {
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
            enemy.setHealth(player.getHealth()-2);

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
