package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;


import java.util.LinkedList;

public class Player extends Actor {

    int strength;
    private int health;

    private LinkedList<String> playerInventory = new LinkedList<String>();

    public Player(Cell cell) {
        super(cell);
        strength = 5;
        health = 15;
    }

    @Override
    protected int getStrength() {
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
        if (item.equals("sword")){
            strength = 10;
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
        if (checkEmptyField(typeOfTile)) {
            if (checkAttack(enemy)) {
                attack(enemy);
            }else{
                move(nextCell);
            }
        }

    }

    private boolean checkAttack(Actor enemy){
        return enemy instanceof Enemy;
    }
}


