package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.*;



public class Main extends Application {
    GameDatabaseManager dbManager;
    Display display;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String level = "/map.txt";
        GameMap map = MapLoader.loadMap(level);
        setupDbManager();
        display = new Display(map, dbManager, level);
        display.createCanvas(primaryStage);
    }

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

}
