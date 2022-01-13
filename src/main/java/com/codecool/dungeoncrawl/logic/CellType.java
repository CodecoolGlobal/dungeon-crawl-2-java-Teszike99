package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    STAIRS("stairs"),
    CLOSEDOOR("closeDoor"),
    OPENDOOR("openDoor"),
    GOAL("goal");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
