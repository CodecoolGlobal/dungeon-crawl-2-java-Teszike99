package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.Items.*;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Ghost;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
    Player player = new Player(gameMap.getCell(1, 1));

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

    @Test
    void setPlayer_giveBackRightPlayer(){
        Player testPlayer = new Player(gameMap.getCell(2,2));
       gameMap.setPlayer(testPlayer);
       assertEquals(testPlayer, gameMap.getPlayer());
    }

    @Test
    void playerKillEnemy_enemyRemovesFromTheListWell(){
        List<Enemy> enemies = new ArrayList<>();
        Skeleton skeleton = new Skeleton(gameMap.getCell(1,1));
        gameMap.setEnemy(skeleton);
        Ghost ghost = new Ghost(gameMap.getCell(2,2));
        gameMap.setEnemy(ghost);
        enemies.add(ghost);
        Player player = new Player(gameMap.getCell(1,2));
        gameMap.setPlayer(player);
        player.move(0,-1);
        gameMap.moveEnemies();


        assertEquals(12, player.getHealth());
        assertEquals(0, skeleton.getHealth());
        assertEquals(enemies, gameMap.getEnemies());
    }

    @Test
    void checkOpenDoor_onlyCanPassWithKey(){
        player.inventoryAddItem("key");
        player.inventoryAddItem("uzi");
        LinkedList<String> playerInventory = new LinkedList<String>();
        playerInventory.add("uzi");
        ClosedDoor door = new ClosedDoor(gameMap.getCell(1,2));
        gameMap.getCell(1,2).setType(CellType.CLOSEDOOR);
        player.move(0,1);

        assertEquals(playerInventory, player.getPlayerInventory());
        assertEquals("player", player.getTileName());
        assertEquals(CellType.OPENDOOR, gameMap.getCell(1,2).getType());
    }

    @Test
    void returnPlayerName_giveBackGoodName(){

        assertEquals("player", player.getTileName());
    }
}
