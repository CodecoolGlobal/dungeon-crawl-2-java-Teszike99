package com.codecool.dungeoncrawl.logic.actors;


import com.codecool.dungeoncrawl.logic.Items.Door;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Items.Item;


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
    public int getStrength() {
        return strength;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int newHealth) {
        health = newHealth;
    }

    public void setStrength(int strength){
        this.strength = strength;
    }

    public String getTileName() {
        return "player";
    }


    public void inventoryAddItem(String item){
        playerInventory.add(item);
        if (item.equals("uzi")){
            this.strength += 10;
        }else if(item.equals("cola")){
            this.health += 10;
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
         if (playerInventory.contains("key") && Boolean.FALSE.equals(checkDoorCondition(moveX, moveY))){
            openDoor(nextCell);
        } else if (Boolean.FALSE.equals(checkDoorCondition(moveX, moveY))) {
            return;
        } else if (checkEmptyField(typeOfTile)) {
            if (checkAttack(enemy)) {
                attack(enemy);
            }else{
                move(nextCell);
            }
        }

    }

    private boolean checkAttack(Actor enemy) {
        return enemy instanceof Enemy;
    }

    private Boolean checkDoorCondition(int moveX, int moveY){
        Door door = this.getCell().getNeighbor(moveX, moveY).getDoor();
        if (door != null){
            if(door.getTileName().equals("closedDoor")){
                return false;
            }
        }
        return null;
    }

    private void openDoor(Cell nextCell){
        if(nextCell.getType() == CellType.CLOSEDOOR){
            nextCell.setDoor(null);
            nextCell.setType(CellType.OPENDOOR);
        }

    }

    public Boolean checkItem(){
        Cell actualCell = this.getCell();
        if(actualCell.getItem() != null){
                return true;
            }
        return false;
    }


    public Item pickUpItem(){
        try{
            String itemName = this.getCell().getItem().getTileName();
            Item item = this.getCell().getItem();
            this.getCell().setItem(null);
            this.inventoryAddItem(itemName);
            return item;
        }
        catch (Exception e){
            System.out.println("There is no item.");
            return null;
        }
    }

}


