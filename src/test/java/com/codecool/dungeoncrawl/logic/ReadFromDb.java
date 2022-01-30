package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static com.codecool.dungeoncrawl.logic.MapLoader.createMapFromDb;
import static org.junit.jupiter.api.Assertions.*;

public class ReadFromDb {

    GameDatabaseManager dbManager = new GameDatabaseManager();

    @Test
    void readFromDatabase_doesItSuccssed(){
        try {
            dbManager.setup();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbManager.load("ákos");

        assertNotNull(createMapFromDb(dbManager));
    }


}
