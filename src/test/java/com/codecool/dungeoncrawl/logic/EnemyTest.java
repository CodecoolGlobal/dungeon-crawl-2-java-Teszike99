package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Ghost;
import com.codecool.dungeoncrawl.logic.actors.LazyWitch;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnemyTest {
    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);


    @Test
    void returnEnemyName_ReturnEnemyNameWell(){
        LazyWitch lazyWitch = new LazyWitch(new Cell(gameMap,1,1,CellType.FLOOR));
        Skeleton skeleton = new Skeleton(new Cell(gameMap, 2,2, CellType.FLOOR));
        Ghost ghost = new Ghost(new Cell(gameMap, 3,3, CellType.FLOOR));
        assertEquals("ghost", ghost.getTileName());
        assertEquals("skeleton", skeleton.getTileName());
        assertEquals("LazyWitch", lazyWitch.getTileName());

    }

    @Test
    void ghostMove_ghostMoveSmart(){
        Player player = new Player(new Cell(gameMap, 1,1, CellType.FLOOR));
        Ghost ghost = new Ghost(new Cell(gameMap, 3,3, CellType.FLOOR));
        ghost.move();
    }
}
