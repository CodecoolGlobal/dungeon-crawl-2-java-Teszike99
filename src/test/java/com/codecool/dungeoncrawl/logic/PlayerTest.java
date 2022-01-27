package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.Items.Item;
import com.codecool.dungeoncrawl.logic.Items.Potion;
import com.codecool.dungeoncrawl.logic.actors.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PlayerTest {

    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
    Player player = new Player(gameMap.getCell(0, 0));

    @BeforeEach
    public void beforeEach() {

    }

    @Test
    void pickUpSword_AddingStrength() {
        player.inventoryAddItem("uzi");
        assertEquals(15,player.getStrength());
    }

    @Test
    void pickUpPotion_AddingHealth(){
        player.inventoryAddItem("cola");
        assertEquals(25, player.getHealth());
    }
}
