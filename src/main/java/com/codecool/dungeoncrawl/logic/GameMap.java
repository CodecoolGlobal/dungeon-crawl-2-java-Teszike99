package com.codecool.dungeoncrawl.logic;


import com.codecool.dungeoncrawl.logic.Items.Item;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Ghost;
import com.codecool.dungeoncrawl.logic.actors.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;
    private List<Enemy> enemy = new ArrayList<Enemy>();
    private List<Item> itemList = new ArrayList<Item>();
    private Enemy removableEnemy;

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


    public void setEnemy(Enemy enemy) {
        this.enemy.add(enemy);
    }

    public void setItem(Item item){this.itemList.add(item);}

    public Player getPlayer() {
        return player;
    }

    public void moveEnemies(){
        for (Enemy oneEnemy : enemy){
            if(oneEnemy instanceof Ghost){
                ((Ghost) oneEnemy).setPlayer(player);
            }
            if (oneEnemy.isRemoveAble()){
                removableEnemy = oneEnemy;
            }else{
                oneEnemy.move();
            }
        }
        if (removableEnemy != null){
            this.enemy.remove(removableEnemy);
            removableEnemy = null;
        }
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public List<Enemy> getEnemies() {
        return enemy;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> newItemList){
        this.itemList = newItemList;
    }

    public void changeEnemyList(List<Enemy> enemyList) {
        this.enemy = enemyList;
    }

    public void changeItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

}


