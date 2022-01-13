package com.codecool.dungeoncrawl.logic;

import Items.OpenDoor;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    STAIRS("stairs"),
    CLOSEDOOR("closeDoor"),
    OPENDOOR("openDoor");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
