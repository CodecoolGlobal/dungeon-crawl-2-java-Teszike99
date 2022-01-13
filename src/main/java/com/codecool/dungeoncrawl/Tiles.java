package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(8, 5));
        tileMap.put("wall", new Tile(7, 15));
        tileMap.put("floor", new Tile(0, 0));
        tileMap.put("player", new Tile(29, 10));
        tileMap.put("skeleton", new Tile(29, 1));
        tileMap.put("key", new Tile(16, 23));
        tileMap.put("sword", new Tile(9, 31));
        tileMap.put("stairs", new Tile(30, 21));
        tileMap.put("ghost", new Tile(26, 10));
        tileMap.put("car", new Tile(13, 23));
        tileMap.put("house", new Tile(4, 20));
        tileMap.put("LazyWitch", new Tile(20,20));
        tileMap.put("potion", new Tile(26, 11));
        tileMap.put("closedDoor", new Tile(3, 3));
        tileMap.put("openDoor", new Tile(4, 4));
        tileMap.put("goal", new Tile(9, 26));

    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
