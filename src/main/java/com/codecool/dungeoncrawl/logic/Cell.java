package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.Items.Door;
import com.codecool.dungeoncrawl.logic.Items.Item;
import com.codecool.dungeoncrawl.logic.actors.Actor;

public class Cell implements Drawable {
    private CellType type;
    private Actor actor;
    private Item item;
    private Door door;
    private GameMap gameMap;
    private int x, y;

    Cell(GameMap gameMap, int x, int y, CellType type) {
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public CellType getType() {
        return type;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor() {
        return actor;
    }

    public void setItem(Item item){
        this.item = item;
    }

    public Item getItem(){
        return this.item;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public Door getDoor(){
        return this.door;
    }

    public Cell getNeighbor(int dx, int dy) {
        return gameMap.getCell(x + dx, y + dy);
    }

    @Override
    public String getTileName() {
        return type.getTileName();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }



    }




