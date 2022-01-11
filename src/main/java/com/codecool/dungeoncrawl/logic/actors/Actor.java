package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {
    private Cell cell;
    private Boolean monster = false;
    private int health = 10;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        CellType typeOfTile = nextCell.getType();
        Actor actor = nextCell.getActor();
        try{
            if (actor.getTileName().equals("skeleton")){
                monster = true;
            }
        }
        catch (Exception e){
        }
        if(typeOfTile == CellType.WALL || this.monster){
            monster = false;
            throw new Error();
        }
        else {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;

        }

    }

    public int getHealth() {
        return health;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }
}
