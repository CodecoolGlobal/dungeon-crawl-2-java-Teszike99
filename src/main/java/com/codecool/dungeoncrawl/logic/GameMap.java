package com.codecool.dungeoncrawl.logic;

import Items.Item;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import javafx.scene.control.Alert;

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


    public Player getPlayer() {
        return player;
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


    public void moveActors(int x, int y) {
        checkMove(x,y,player);
        checkItem(player);
        for (Skeleton oneEnemy : enemy){
            moveOneEnemy(oneEnemy);
        }
        player.getCell().setActor(player);
    }

    private void moveOneEnemy(Skeleton oneEnemy) {
        boolean movingPossible = true;
        while (movingPossible){
            int randomX = rand.nextInt(-1,2);
            int randomY = rand.nextInt(-1,2);
            movingPossible = checkMove(randomX, randomY, oneEnemy);
        }
    }

    private boolean checkMove(int moveX, int moveY, Actor actor){
        Cell nextCell = actor.getCell().getNeighbor(moveX, moveY);
        CellType typeOfTile = nextCell.getType();
        Actor enemy = nextCell.getActor();
        if (typeOfTile == CellType.FLOOR ||typeOfTile == CellType.STAIRS && enemy == null) {
            actor.move(moveX, moveY);
            return false;
        }else if (typeOfTile == CellType.FLOOR && enemy.getTileName().equals("skeleton") && actor.equals(player)) {
            attack(enemy, moveX, moveY);
            return false;
        }else {
            return true;
        }
    }

    private void attack(Actor enemy, int moveX, int moveY) {
        if (enemy.getHealth() - 5 <= 0) {
            this.enemy.remove(enemy);
        }else if (player.getHealth() - 2 <= 0){
            gameOver();
        }else{
            player.setHealth(player.getHealth() - 2);
            enemy.setHealth(enemy.getHealth() - 5);
        }
        player.move(moveX, moveY);
    }

    private void gameOver() {

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
            String item = getPlayer().getCell().getItem().getTileName();
            System.out.println(item);
            if (item.equals(item)){
                 getPlayer().getCell().setItem(null);

            }
            player.inventoryAddItem(item);

        }
        catch (Exception e){
            System.out.println("There is no item.");
        }
    }
}


