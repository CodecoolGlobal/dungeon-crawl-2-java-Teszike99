package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.Items.Item;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.*;

import static com.codecool.dungeoncrawl.logic.MapLoader.createMapFromDb;


public class Main extends Application {
    String level = "/map.txt";
    GameMap map = MapLoader.loadMap(level);
    GameDatabaseManager dbManager;
    Display display;
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        setupDbManager();
        display = new Display(map, dbManager, level);
        display.createCanvas(primaryStage);
    }


    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case E:
                Item item = map.getPlayer().pickUpItem();
                List<Item> itemList = map.getItemList();
                itemList.remove(item);
                map.setItemList(itemList);
                break;
            case R:
                map = MapLoader.loadMap("/map.txt");
                break;
            case UP:
                map.getPlayer().move(0, -1);
                map.moveEnemies();
                display.refresh(map);
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                map.moveEnemies();
                display.refresh(map);
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                map.moveEnemies();
                display.refresh(map);
                break;
            case RIGHT:
                map.getPlayer().move(1, 0);
                map.moveEnemies();
                display.refresh(map);
                break;
        }
    }

    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            exit();
        }
    }

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

    private void exit() {
        try {
            stop();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
