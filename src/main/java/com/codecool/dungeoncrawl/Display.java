package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.Items.Item;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Player;
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

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.*;

import static com.codecool.dungeoncrawl.logic.MapLoader.createMapFromDb;
import static jdk.jfr.internal.consumer.EventLog.stop;

public class Display {

    Button saveButton = new Button("Save Game");
    Button loadButton = new Button("Load Game");
    Label alertLabel = new Label();
    Label healthLabel = new Label();
    Label inventoryLabel = new Label();
    Canvas canvas;
    GraphicsContext context;
    GameMap map;
    GameDatabaseManager dbManager;
    String level;

    public Display(GameMap map, GameDatabaseManager dbManager, String level) {
        this.dbManager = dbManager;
        this.map = map;
        this.level = level;
        canvas = new Canvas(
                map.getWidth() * Tiles.TILE_WIDTH,
                map.getHeight() * Tiles.TILE_WIDTH);
        context = canvas.getGraphicsContext2D();
    }

    public void createCanvas(Stage primaryStage) {

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
        refresh(map);
        scene.setOnKeyPressed(this::onKeyPressed);
        primaryStage.setScene(scene);
        refresh(map);
        scene.setOnKeyPressed(this::onKeyPressed);
        scene.setOnKeyReleased(this::onKeyReleased);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    public void refresh(GameMap map) {
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
                refresh(map);
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                map.moveEnemies();
                refresh(map);
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                map.moveEnemies();
                refresh(map);
                break;
            case RIGHT:
                map.getPlayer().move(1, 0);
                map.moveEnemies();
                refresh(map);
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

    private void exit() {
        try {
            stop();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }

    public void displaySaveButton() {
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

    private void saveGame(Stage saveStage, TextField nameInput) {
        String saveName = nameInput.getText();
        saveStage.close();
        Player player = map.getPlayer();
        List<Item> items = map.getItemList();
        List<Enemy> enemies = map.getEnemies();
        if (checkPlayerName(saveName)) {
            dbManager.update(player,level,saveName, enemies, items);
        }
        else {
            dbManager.save(player, level, saveName, enemies, items);
        }
    }

    public void LoadGame() {
        Optional<String> choice = getSaveChoiceFromUser();
        String loadedName = choice.get();
        dbManager.load(loadedName);
        map = createMapFromDb(dbManager);
        refresh(map);
    }

    public Optional<String> getSaveChoiceFromUser() {
        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>();
        choiceDialog.setTitle("Load Game");
        choiceDialog.getItems().addAll(dbManager.loadChoices());
        choiceDialog.setHeaderText("Choose a save!");
        choiceDialog.getDialogPane().setContentText("Player name: ");
        Optional<String> choice = choiceDialog.showAndWait();
        return choice;
    }

    private boolean checkPlayerName (String playerName) {
        return dbManager.loadChoices().contains(playerName);
    }

    public void changeMap() {
        if (map.getPlayer().getCell().getType() == CellType.STAIRS){
            level = "/map2.txt";
            map = MapLoader.loadMap(level);
        }
    }

    private void checkLose() {
        if (map.getPlayer() == null){
            level = "/lose.txt";
            map = MapLoader.loadMap(level);
        }
    }

    private void checkWin() {
        if (map.getPlayer().getCell().getType() == CellType.GOAL){
            level = "/win.txt";
            map = MapLoader.loadMap(level);
        }
    }


}
