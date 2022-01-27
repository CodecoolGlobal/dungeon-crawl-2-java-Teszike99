package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.Items.Potion;
import com.codecool.dungeoncrawl.logic.Items.Sword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTest {
    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);


    @Test
    void itemGetCell_givesBackRightCell(){
        Cell swordCell = new Cell(gameMap, 1,1,CellType.FLOOR);
        Sword sword = new Sword(swordCell);
        assertEquals(swordCell, sword.getCell());
    }

    @Test
    void getXandY_givesBackRightCoordinate(){
        Cell PotionCell = new Cell(gameMap, 1,1, CellType.FLOOR);
        Potion potion = new Potion(PotionCell);
        assertEquals(1, potion.getX());
    }
}
