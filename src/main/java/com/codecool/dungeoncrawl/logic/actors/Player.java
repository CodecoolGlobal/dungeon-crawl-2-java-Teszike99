package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Main;
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
                    System.out.println("You alredy have this item!");
                }
                else {

                    playerInventory.add(item);
                }
            }


    public LinkedList getPlayerInventory(){
        return playerInventory;
    }
}
