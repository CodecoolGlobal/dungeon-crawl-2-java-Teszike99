package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public abstract class Enemy extends Actor{


    public Enemy(Cell cell) {
        super(cell);
    }

    public abstract void move();

    public boolean checkAttack(CellType typeOfTile, Actor player){
        boolean isPlayer = player instanceof Player;
        return typeOfTile == CellType.FLOOR && isPlayer;
    }


}
