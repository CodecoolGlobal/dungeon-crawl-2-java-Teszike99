package com.codecool.dungeoncrawl.logic.Items;

import com.codecool.dungeoncrawl.logic.Cell;

public class OpenedDoor extends Door{
    public OpenedDoor(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "openDoor";
    }
}
