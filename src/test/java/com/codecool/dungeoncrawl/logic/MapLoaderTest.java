package com.codecool.dungeoncrawl.logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MapLoaderTest {

    @Test
    void loadMap_loadsMap() {
        GameMap map = MapLoader.loadMap("/map.txt");
        assertNotNull(map);
    }
}


