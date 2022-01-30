package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.Items.Key;
import com.codecool.dungeoncrawl.logic.Items.OpenedDoor;
import com.codecool.dungeoncrawl.logic.Items.Potion;
import com.codecool.dungeoncrawl.logic.Items.Sword;
import com.codecool.dungeoncrawl.logic.actors.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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


    @Test
    void pickUpItem_playerCanPickUpItem(){
        Player player = new Player(gameMap.getCell(2,2));
        Sword sword = new Sword(gameMap.getCell(2,2));
        LinkedList<String> playerInventory = new LinkedList<String>();
        playerInventory.add("uzi");

        player.pickUpItem();

        assertEquals(playerInventory, player.getPlayerInventory());
        assertNull(gameMap.getCell(2,2).getItem());
    }

    @Test
    void openDoor_givesBackItsName(){
        OpenedDoor door = new OpenedDoor(gameMap.getCell(2,2));
        gameMap.getCell(2,2).setType(CellType.OPENDOOR);

        assertEquals("openDoor", gameMap.getCell(2,2).getTileName());
        assertEquals("openDoor", door.getTileName());
    }

    @Test
    void keyName_askingForKeyGivesItsName(){
        Key key = new Key(gameMap.getCell(2,2));

        assertEquals(gameMap.getCell(2,2), key.getCell());
        assertEquals(2, key.getX());
        assertEquals(2, key.getY());
        assertEquals("key", key.getTileName());
    }

    @Test
    void potionName_askingForPotionName(){
        Potion potion = new Potion(gameMap.getCell(2,2));


        assertEquals(potion, gameMap.getCell(2,2).getItem());
        assertEquals("cola", potion.getTileName());
    }
}
