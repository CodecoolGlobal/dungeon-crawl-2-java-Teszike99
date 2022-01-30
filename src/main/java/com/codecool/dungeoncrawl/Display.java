package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.LinkedList;

public class Display {

    public static Canvas createCanvas(Stage primaryStage, GameMap map) {
        Label alertLabel = new Label();
        Label healthLabel = new Label();
        Label inventoryLabel = new Label();
        Canvas canvas = new Canvas(
                map.getWidth() * Tiles.TILE_WIDTH,
                map.getHeight() * Tiles.TILE_WIDTH);
        GraphicsContext context = canvas.getGraphicsContext2D();
        Button saveButton = new Button("Save Game");
        Button loadButton = new Button("Load Game");
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 3);
        ui.add(new Label("Items: "), 0, 6);
        ui.add(new Label(""), 1, 6);
        ui.add(new Label(""), 1, 7);
        ui.add(saveButton, 0, 8);
        ui.add(new Label(""), 0, 9);
        ui.add(loadButton,0,10);
        saveButton.setFocusTraversable(false);
        loadButton.setFocusTraversable(false);
        saveButton.setOnAction(e -> displaySaveButton());
        loadButton.setOnAction(e -> LoadGame());

        BorderPane borderPane = new BorderPane();
        ui.add(alertLabel, 0, 0);
        ui.add(healthLabel, 1, 3);
        ui.add(inventoryLabel, 1, 6);
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add("app.css");
        primaryStage.setScene(scene);
        refresh(map, context, canvas, healthLabel, inventoryLabel, alertLabel);
        scene.setOnKeyPressed(this::onKeyPressed);
        primaryStage.setScene(scene);
        refresh(map, context, canvas, healthLabel, inventoryLabel, alertLabel);
        scene.setOnKeyPressed(this::onKeyPressed);
        scene.setOnKeyReleased(this::onKeyReleased);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();

        return canvas;
    }

    private static void refresh(GameMap map, GraphicsContext context, Canvas canvas, Label healthLabel, Label inventoryLabel, Label alertLabel) {
        changeMap();
        checkLose();
        checkWin();
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        int centerX = (int) (canvas.getWidth() / (Tiles.TILE_WIDTH * 2));
        int centerY = (int) (canvas.getHeight() / (Tiles.TILE_WIDTH * 2));
        int offsetX = 0;
        int offsetY = 0;
        if (map.getPlayer().getX() > centerX) {
            offsetX = map.getPlayer().getX() - centerX;
        }
        if (map.getPlayer().getY() > centerY) {
            offsetY = map.getPlayer().getY() - centerY;
        }
        for (int x = 0; x + offsetX < map.getWidth(); x++) {
            for (int y = 0; y + offsetY < map.getHeight(); y++) {
                Cell cell = map.getCell(x + offsetX, y + offsetY);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getDoor() != null) {
                    Tiles.drawTile(context, cell.getDoor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());

        LinkedList playerInventory = map.getPlayer().getPlayerInventory();
        int itemCounter = 0;
        String newLabelText = "";
        for (Object item : playerInventory) {
            itemCounter += 1;
            if (itemCounter == 1) {
                newLabelText += "\n" + item.toString();
                inventoryLabel.setText(newLabelText);
            } else {
                itemCounter += 1;
                newLabelText += "\n" + item;
                inventoryLabel.setText(newLabelText);
            }
        }

        if (map.getPlayer().getCell().getItem() != null){
            alertLabel.setText("Press E!");
        }
        else {
            alertLabel.setText("");
        }
    }

    public static void displaySaveButton() {
        TextField nameInput = new TextField();
        Button save = new Button("Save");
        Button cancel = new Button("Cancel");
        VBox layout = new VBox(2);
        layout.getChildren().addAll(nameInput, save, cancel);
        Scene saveScene = new Scene(layout, 350, 150);
        Stage saveStage = new Stage();
        saveScene.getStylesheets().add("app.css");
        saveStage.setTitle("Save game");
        saveStage.setScene(saveScene);
        saveStage.show();
        save.setOnAction(e -> saveGame(saveStage, nameInput));
        cancel.setOnAction(e -> saveStage.close());
    }


}
