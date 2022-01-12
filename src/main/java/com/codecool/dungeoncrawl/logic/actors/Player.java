package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;


public class Player extends Actor {
    public Player(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "player";
    }

    public void move(int moveX, int moveY) {
        Cell playerCell = this.getCell();
        Cell nextCell = playerCell.getNeighbor(moveX, moveY);
        CellType typeOfTile = nextCell.getType();
        Actor enemy = nextCell.getActor();
        if (checkEmptyField(typeOfTile, enemy)) {
            putActorOnMap(nextCell);
        }else if(checkAttack(typeOfTile, enemy, "skeleton", this, "player")) {
            attack(enemy);
        }else{ }
    }
}


