package com.codecool.dungeoncrawl.logic.actors;

import Items.Door;
import Items.OpenDoor;
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
                    System.out.println("You already have this item!");
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

         if (playerInventory.contains("key")){
            openDoor(nextCell);
        } else if (Boolean.FALSE.equals(checkDoorCondition(moveX, moveY))) {
            return;
        } else if (checkEmptyField(typeOfTile, enemy)) {
            putActorOnMap(nextCell);
        }else if(checkAttack(typeOfTile, enemy)) {
            attack(enemy);
        }

    }

    private boolean checkAttack(CellType typeOfTile, Actor enemy){
        return typeOfTile == CellType.FLOOR &&
                enemy.getTileName().equals("skeleton");
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


    public void pickUpItem(){
        try{
            String item = this.getCell().getItem().getTileName();
            this.getCell().setItem(null);
            this.inventoryAddItem(item);
        }
        catch (Exception e){
            System.out.println("There is no item.");
        }
    }

}


