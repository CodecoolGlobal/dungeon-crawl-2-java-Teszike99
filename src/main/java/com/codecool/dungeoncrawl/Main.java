package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.Items.Item;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.ItemModel;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.sql.SQLException;
import java.util.Optional;


public class Main extends Application {
    String level = "/map.txt";
    GameMap map = MapLoader.loadMap(level);
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label alertLabel = new Label();
    Label healthLabel = new Label();
    Label inventoryLabel = new Label();
    Button saveButton = new Button("Save Game");
    Button loadButton = new Button("Load Game");
    GameDatabaseManager dbManager;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setupDbManager();
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 3);
        ui.add(new Label("Items: "), 0, 6);
        ui.add(new Label(""), 1, 6);
        ui.add(saveButton, 1, 7);
        ui.add(loadButton,1,8);

        saveButton.setOnAction(e -> displaySaveButton());
        loadButton.setOnAction(e -> displayLoadButton());

        BorderPane borderPane = new BorderPane();
        ui.add(alertLabel, 0, 0);
        ui.add(healthLabel, 1, 3);
        ui.add(inventoryLabel, 1, 6);
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add("app.css");
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        scene.setOnKeyReleased(this::onKeyReleased);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case E:
                map.getPlayer().pickUpItem();
                break;
            case R:
                map = MapLoader.loadMap("/map.txt");
                break;
            case UP:
                map.getPlayer().move(0, -1);
                map.moveEnemies();
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                map.moveEnemies();
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                map.moveEnemies();
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1, 0);
                map.moveEnemies();
                refresh();
                break;
            case S:
                Player player = map.getPlayer();
                PlayerModel model = new PlayerModel(player);
                dbManager.savePlayer(model);
                GameState state = new GameState(level, model);
                dbManager.saveGameState(state);
                dbManager.savePlayer(player);
               List<Item> items = map.getItem();
               items.forEach(item -> dbManager.saveItem(item));
                List<Enemy> enemies = map.getEnemies();
                for (Enemy enemy : enemies){
                    dbManager.saveEnemy(enemy, state);
                }


                break;
            case L:
                PlayerModel data = dbManager.loadPlayer();
                Cell playerCell = new Cell(map, data.getX(), data.getY(), CellType.FLOOR);
                Player gamer = new Player(playerCell);
                gamer.setHealth(data.getHp());
                gamer.setStrength(data.getStrength());
                map.setPlayer(gamer);
                List<EnemyModel> enemyList = dbManager.loadEnemies();
                List<ItemModel> itemList = dbManager.loadItems();
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


    private void refresh() {
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

        Boolean isItem = map.getPlayer().checkItem();
        if (isItem == true){
            alertLabel.setText("Press E!");
        }
        else {
            alertLabel.setText("");
        }
    }

    private void checkWin() {
        Player player = map.getPlayer();
        if (player.getCell().getType() == CellType.GOAL){
            map = MapLoader.loadMap("/win.txt");
        }
    }


    private void changeMap() {
        if (Objects.equals(map.getPlayer().getCell().getTileName(), "stairs")) {
            level = "/map2.txt";
            map = MapLoader.loadMap("/map2.txt");
        }
    }

    private void checkLose() {
        Player player = map.getPlayer();
        if (map.checkPlayerDeath(player)) {
            map = MapLoader.loadMap("/lose.txt");
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

    public static void displaySaveButton() {
        TextField nameInput = new TextField();
        Button save = new Button("Save");
        Button cancel = new Button("Cancel");
        VBox layout = new VBox(2);
        layout.getChildren().addAll(nameInput, save, cancel);
        Scene saveScene = new Scene(layout, 350, 150);
        Stage saveStage = new Stage();
        saveStage.setTitle("Save game");
        saveStage.setScene(saveScene);
        saveStage.show();
        save.setOnAction(e -> saveGame(saveStage, nameInput));
        cancel.setOnAction(e -> saveStage.close());

    }

    private static void saveGame(Stage saveStage, TextField nameInput) {
        String saveName = nameInput.getText();
        System.out.println(saveName);
        saveStage.close();
    }

    public static void displayLoadButton() {
        Optional<String> choice = getSaveChoiceFromUser();
        System.out.println(choice);
    }

    public static Optional<String> getSaveChoiceFromUser() {
        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>();
        choiceDialog.setTitle("Load Game");
        choiceDialog.getItems().addAll("a","v");
        choiceDialog.setHeaderText("Choose a save to load!");
        choiceDialog.getDialogPane().setContentText("Save name: ");
        Optional<String> choice = choiceDialog.showAndWait();
        return choice;
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
