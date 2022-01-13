package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;


import java.util.LinkedList;

public class Player extends Actor {

    private LinkedList<String> playerInventory = new LinkedList<String>();

    public Player(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "player";
    }


    public void inventoryAddItem(String item){
                if(playerInventory.contains(item)){
                    System.out.println("You alredy have this item!");
                }
                else {

                    playerInventory.add(item);
                }
            }


    public LinkedList getPlayerInventory() {
        return playerInventory;
    }

    public void move(int moveX, int moveY) {
        Cell playerCell = this.getCell();
        Cell nextCell = playerCell.getNeighbor(moveX, moveY);
        CellType typeOfTile = nextCell.getType();
        Actor enemy = nextCell.getActor();
        if (checkEmptyField(typeOfTile, enemy)) {
            move(nextCell);
        }else if(checkAttack(typeOfTile, enemy)) {
            attack(enemy);
        }

    }

    private boolean checkAttack(CellType typeOfTile, Actor enemy){
        boolean isEnemy = enemy instanceof Enemy;
        return typeOfTile == CellType.FLOOR && isEnemy;


    }
}


