package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.Items.Item;

public class ItemModel extends BaseModel {
    private String name;
    private int x;
    private int y;
    public int gameStateId;

    public ItemModel(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }


    public ItemModel(Item item){
        this.name = item.getTileName();
        this.x    = item.getX();
        this.y    = item.getY();
    }


    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setGameStateId(int gameStateId) {
        this.gameStateId = gameStateId;
    }
}
