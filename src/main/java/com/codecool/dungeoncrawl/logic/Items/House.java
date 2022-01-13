package com.codecool.dungeoncrawl.logic.Items;

import com.codecool.dungeoncrawl.logic.Cell;

public class House extends Item {


    public House(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "house";
    }

}

