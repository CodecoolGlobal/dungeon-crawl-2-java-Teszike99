package com.codecool.dungeoncrawl.logic;


import Items.Item;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.scene.control.Alert;
import com.codecool.dungeoncrawl.logic.actors.Enemy;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;
    private static int currentLevel = 0;

    private Player player;
    private List<Enemy> enemy = new ArrayList<Enemy>();
    private static final Random rand = new Random();
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


    public Player getPlayer() {
        return player;
    }

    public void moveEnemies(){
        for (Enemy oneEnemy : enemy){
            oneEnemy.move();
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

    public static void changeLevel(String status){
        if (Objects.equals(status, "next")) {
            currentLevel ++;
        }
        else {
            currentLevel --;
        }
    }

    public static int getCurrentLevel() {
        return currentLevel;
    }


    private void checkItem(Actor actor){
        Cell nextCell = actor.getCell();
        if(nextCell.getItem() != null){
            alertBox("Press E to pick up item");
        }

    }


    private void alertBox(String alertMessage){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText(alertMessage);
            alert.showAndWait();
        }


    public void pickUpItem(){
        try{
            String item = player.getCell().getItem().getTileName();
            getPlayer().getCell().setItem(null);
            player.inventoryAddItem(item);
        }
        catch (Exception e){
            System.out.println("There is no item.");
        }
    }

    public void checkDeath(Enemy enemy) {
        if (enemy.getHealth() <= 0) {
            this.removableEnemy= enemy;
            enemy.getCell().setActor(null);
        }else if (player.getHealth() <= 0){
            gameOver();
        }
    }

    private void gameOver() {
    }
}


