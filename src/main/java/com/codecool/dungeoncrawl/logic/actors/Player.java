package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

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
                    System.out.println(playerInventory);
                }
                else {
                    playerInventory.add(item);
                    System.out.println(playerInventory);
                }
            }


    public LinkedList<String> getPlayerInventory(){
        return playerInventory;
    }
}
