package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {
    GameMap gameMap = new GameMap(4, 4, CellType.FLOOR);


    @Test
    void returnEnemiesAndTheirNames_ReturnEnemiesAndTheirNameWell(){
        List<Enemy> enemyList = new ArrayList<>();
        LazyWitch lazyWitch = new LazyWitch(gameMap.getCell(1,1));
        gameMap.setEnemy(lazyWitch);
        enemyList.add(lazyWitch);
        Skeleton skeleton = new Skeleton(gameMap.getCell(2,2));
        gameMap.setEnemy(skeleton);
        enemyList.add(skeleton);
        Ghost ghost = new Ghost(gameMap.getCell(3,3));
        gameMap.setEnemy(ghost);
        enemyList.add(ghost);
        assertEquals("ghost", ghost.getTileName());
        assertEquals("skeleton", skeleton.getTileName());
        assertEquals("LazyWitch", lazyWitch.getTileName());
        assertEquals(enemyList, gameMap.getEnemies());

    }

    @Test
    void ghostMove_ghostMoveSmart(){
        Player player = new Player(gameMap.getCell(1,1));
        Ghost ghost = new Ghost(gameMap.getCell(3,2));
        ghost.setPlayer(player);
        ghost.move();
        assertEquals(ghost, gameMap.getCell(2,2).getActor());
    }

    @Test
    void skeletonMove_doesNotMoveEachOther(){
        Skeleton skeleton1 = new Skeleton(gameMap.getCell(2,2));
        Skeleton skeleton2 = new Skeleton(gameMap.getCell(2,3));
        skeleton1.move();
        assertNotEquals(skeleton1, gameMap.getCell(2,3).getActor());
     }

     @Test
    void setAndCheckHealth_checkHealthMethod(){
        Ghost ghost = new Ghost(gameMap.getCell(2,2));
        ghost.setHealth(3);
        Skeleton skeleton = new Skeleton(gameMap.getCell(1,1));
        skeleton.setHealth(4);
        LazyWitch lazyWitch = new LazyWitch(gameMap.getCell(3,3));
        lazyWitch.setHealth(1);

        assertEquals(3, lazyWitch.getStrength());
        assertEquals(3, ghost.getHealth());
        assertEquals(4, skeleton.getHealth());
        assertEquals(1, lazyWitch.getHealth());

     }

}
