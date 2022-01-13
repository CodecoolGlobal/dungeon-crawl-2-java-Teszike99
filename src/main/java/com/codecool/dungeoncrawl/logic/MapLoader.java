package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.Items.*;
import com.codecool.dungeoncrawl.logic.actors.Ghost;
import com.codecool.dungeoncrawl.logic.actors.LazyWitch;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;

import java.io.InputStream;
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
                            new OpenDoor(cell);
                        case 'c':
                            cell.setType(CellType.CLOSEDOOR);
                            new ClosedDoor(cell);
                            break;
                        case 'l':
                            cell.setType(CellType.STAIRS);
                            break;
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            new Sword(cell);
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case 'v':
                            cell.setType(CellType.WALL);
                            new Car(cell);
                            break;
                        case 'h':
                            cell.setType(CellType.WALL);
                            new House(cell);
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
                            new Potion(cell);
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

}
