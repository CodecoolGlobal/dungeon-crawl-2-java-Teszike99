package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Items.*;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.ItemModel;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapLoader {

    public static GameMap loadMap(String level) {
        InputStream is = MapLoader.class.getResourceAsStream(level);
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case 'o':
                            cell.setType(CellType.OPENDOOR);
                            new OpenedDoor(cell);
                            break;
                        case 'c':
                            cell.setType(CellType.CLOSEDOOR);
                            new ClosedDoor(cell);
                            break;
                        case 'l':
                            cell.setType(CellType.STAIRS);
                            break;
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            map.setItem(new Sword(cell));
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            map.setItem(new Key(cell));
                            break;
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case 'v':
                            cell.setType(CellType.CAR);
                            break;
                        case 'h':
                            cell.setType(CellType.HOUSE);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            map.setEnemy(new Skeleton(cell));
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            map.setEnemy(new Ghost(cell));
                            break;
                        case 'p':
                            cell.setType(CellType.FLOOR);
                            map.setEnemy(new LazyWitch(cell));
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case 'x':
                            cell.setType(CellType.FLOOR);
                            map.setItem(new Potion(cell));
                            break;
                        case 't':
                            cell.setType(CellType.GOAL);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

    public static GameMap createMapFromDb(GameDatabaseManager dbManager){
        String currentMap = dbManager.getLoadedGameState().getCurrentMap();
        GameMap map = loadMap(currentMap);
        emptyingMap(map);
        loadActors(map, dbManager);
        return map;
    }

    private static void emptyingMap(GameMap map) {
        map.getPlayer().getCell().setActor(null);
        List<Enemy> enemies = map.getEnemies();
        for (Enemy enemy : enemies ) {
            enemy.getCell().setActor(null);
        }
        List<Item> items = map.getItemList();
        for (Item item : items ) {
            item.getCell().setItem(null);
        }
    }

    private static void loadActors(GameMap map, GameDatabaseManager dbManager) {
        createPlayer(dbManager.getLoadedPlayer(), map);
        createEnemies(dbManager.getLoadedEnemies(), map);
        createItemList(dbManager.getLoadedItems(), map);
    }

    private static void createPlayer(PlayerModel loadedPlayer, GameMap map) {
        Cell playerCell = map.getCell(loadedPlayer.getX(), loadedPlayer.getY());
        Player gamer = new Player(playerCell);
        gamer.setHealth(loadedPlayer.getHp());
        gamer.setStrength(loadedPlayer.getStrength());
        playerCell.setActor(gamer);
        map.setPlayer(gamer);
    }

    private static void createEnemies(List<EnemyModel> loadedEnemies, GameMap map) {
        List<Enemy> enemyList = new ArrayList<>();
        for (EnemyModel enemyModel: loadedEnemies) {
            Cell enemyCell = map.getCell(enemyModel.getX(), enemyModel.getY());
            if (enemyModel.getName().equals("ghost")) {
                Enemy ghost = new Ghost(enemyCell);
                enemyList.add(ghost);
                enemyCell.setActor(ghost);
            } else if (enemyModel.getName().equals("LazyWitch")) {
                Enemy lazyWitch = new LazyWitch(enemyCell);
                enemyList.add(lazyWitch);
                enemyCell.setActor(lazyWitch);
            } else {
                Enemy skeleton = new Skeleton(enemyCell);
                enemyList.add(skeleton);
                enemyCell.setActor(skeleton);
            }
        }
        map.changeEnemyList(enemyList);
    }
    private static void createItemList(List<ItemModel> loadedItems, GameMap map) {
        List<Item> itemList = new ArrayList<>();
        for (ItemModel itemModel : loadedItems) {
            Cell itemCell = map.getCell(itemModel.getX(), itemModel.getY());
            if (itemModel.getName().equals("uzi")) {
                Item uzi = new Sword(itemCell);
                itemList.add(uzi);
                itemCell.setItem(uzi);
            } else if (itemModel.getName().equals("cola")) {
                Item cola = new Potion(itemCell);
                itemList.add(cola);
                itemCell.setItem(cola);
            } else if (itemModel.getName().equals("key")) {
                Item key = new Key(itemCell);
                itemList.add(key);
                itemCell.setItem(key);
            }
        }
        map.changeItemList(itemList);
    }

}
