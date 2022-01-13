package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    String level = "/map.txt";
    GameMap map = MapLoader.loadMap(level);
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.moveActors(0,-1);
                refresh();
                break;
            case DOWN:
                map.moveActors(0,1);
                refresh();
                break;
            case LEFT:
                map.moveActors(-1,0);
                refresh();
                break;
            case RIGHT:
                map.moveActors(1,0);
                refresh();
                break;
        }
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        int centerX = (int) (canvas.getWidth() / (Tiles.TILE_WIDTH * 2));
        int centerY = (int) (canvas.getHeight() / (Tiles.TILE_WIDTH * 2));
        int offsetX = 0;
        int offsetY = 0;
        if (map.getPlayer().getX() > centerX){
            offsetX = map.getPlayer().getX() - centerX;
        }
        if (map.getPlayer().getY() > centerY) {
            offsetY = map.getPlayer().getY() - centerY;
        }
        for (int x = 0; x + offsetX < map.getWidth(); x++) {
            for (int y = 0; y + offsetY < map.getHeight(); y++) {
                Cell cell = map.getCell(x + offsetX, y + offsetY);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x , y );
                }
                else if (cell.getItem() != null){
                    Tiles.drawTile(context, cell.getItem(), x , y );
                }
                else {
                    Tiles.drawTile(context, cell, x , y );
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
        changeMap();
    }

    private void changeMap() {
        if (Objects.equals(map.getPlayer().getCell().getTileName(), "stairs") && GameMap.getCurrentLevel() ==0) {
            map = MapLoader.loadMap("/map2.txt");
            GameMap.changeLevel("next");
        }
        else if (GameMap.getCurrentLevel() == 1 && Objects.equals(map.getPlayer().getCell().getTileName(), "stairs")) {
            map = MapLoader.loadMap("/map.txt");
            GameMap.changeLevel("back");
        }
    }
}
