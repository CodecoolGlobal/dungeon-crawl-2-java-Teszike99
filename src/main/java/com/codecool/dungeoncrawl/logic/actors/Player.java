package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import javafx.scene.control.Alert;


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

        if (checkEmptyField(typeOfTile, enemy)) {
            putActorOnMap(nextCell);
        }else if(checkAttack(typeOfTile, enemy)) {
            attack(enemy);
        }
        checkItem(this);

    }

    private boolean checkAttack(CellType typeOfTile, Actor enemy){
        return typeOfTile == CellType.FLOOR &&
                enemy.getTileName().equals("skeleton");
    }


    private void checkItem(Actor actor){
        Cell nextCell = actor.getCell();
        if(nextCell.getItem() != null){
            alertBox("Press E to pick up item");
        }

    }

    private void alertBox(String alertMessage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }


    public void pickUpItem(){
        try{
            String item = this.getCell().getItem().getTileName();
            if (item.equals(item)){
                this.getCell().setItem(null);
            }
            this.inventoryAddItem(item);
        }
        catch (Exception e){
            System.out.println("There is no item.");
        }
    }

}


