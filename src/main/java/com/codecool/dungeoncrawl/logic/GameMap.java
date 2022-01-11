package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;
    private List<Skeleton> enemy = new ArrayList<Skeleton>();
    private static final Random rand = new Random();

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public void setEnemy(Skeleton enemy) {
        this.enemy.add(enemy);
    }


    public Actor getPlayer() {
        return player;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public void moveActors(int x, int y) {
        checkMovePossible(x,y,player);
        for (Skeleton oneEnemy : enemy){
            moveOneEnemy(oneEnemy);
        }
    }

    private void moveOneEnemy(Skeleton oneEnemy) {
        boolean movingPossible = true;
        while (movingPossible){
            int randomX = rand.nextInt(-1,2);
            int randomY = rand.nextInt(-1,2);
            movingPossible = checkMovePossible(randomX, randomY, oneEnemy);
        }
    }

    private boolean checkMovePossible(int moveX, int moveY, Actor actor){
        Cell nextCell = actor.getCell().getNeighbor(moveX, moveY);
        CellType typeOfTile = nextCell.getType();
        if (typeOfTile == CellType.FLOOR  || nextCell.getActor().getTileName().equals("skeleton")) {
            actor.move(moveX, moveY);
            return false;
        }else {
            return true;
        }
    }

}
