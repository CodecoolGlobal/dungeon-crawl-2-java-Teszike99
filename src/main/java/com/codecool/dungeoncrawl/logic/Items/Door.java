package com.codecool.dungeoncrawl.logic.Items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Door implements Drawable {
    Cell cell;

    public Door(Cell cell) {
        this.cell = cell;
        this.cell.setDoor(this);
    }

}
