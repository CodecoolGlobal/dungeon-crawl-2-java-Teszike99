package com.codecool.dungeoncrawl.logic.Items;

import com.codecool.dungeoncrawl.logic.Cell;

public class OpenDoor extends Door{
    public OpenDoor(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "openDoor";
    }
}
