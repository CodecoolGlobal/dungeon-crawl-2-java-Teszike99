package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.Items.Item;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.ItemModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private EnemyDao enemyDao;
    private ItemDao itemDao;
    private GameStateDao gameStateDao;
    private PlayerModel loadedPlayer;
    private List<EnemyModel>  loadedEnemies;
    private GameState loadedGameState;
    private List<ItemModel> loadedItems;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        enemyDao = new EnemyDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource);
        itemDao = new ItemDaoJdbc(dataSource);
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = System.getenv("DB_NAME");
        String user = System.getenv("USER_NAME");
        String password = System.getenv("PASSWORD");

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }

    public void save(Player player, String currentMap, String saveName, List<Enemy> enemies, List<Item> items) {
        PlayerModel playerModel = new PlayerModel(player);
        playerModel.setPlayerName(saveName);
        playerDao.add(playerModel);
        GameState gameModel = new GameState(currentMap, playerModel);
        gameStateDao.add(gameModel);
        items.forEach(item -> saveItem(item, gameModel));
        enemies.forEach(enemy -> saveEnemy(enemy, gameModel));
    }

    public void saveEnemy(Enemy enemy, GameState state) {
        EnemyModel model = new EnemyModel(enemy);
        enemyDao.add(model, state);
    }

    public void saveItem(Item item, GameState state){
        ItemModel ItemModel = new ItemModel(item);
        itemDao.add(ItemModel, state);
    }


    public void load(String loadedName){
        this.loadedPlayer = playerDao.get(loadedName);
        this.loadedGameState = gameStateDao.get(loadedPlayer.getId());
        this.loadedEnemies = enemyDao.getAll(loadedGameState.getId());
        this.loadedItems = itemDao.getAll(loadedGameState.getId());
    }

    public PlayerModel getLoadedPlayer() {
        return loadedPlayer;
    }

    public List<EnemyModel> getLoadedEnemies() {
        return loadedEnemies;
    }

    public GameState getLoadedGameState() {
        return loadedGameState;
    }

    public List<ItemModel> getLoadedItems() {
        return loadedItems;
    }

    public Set<String> loadChoices() {
        return playerDao.getAll().stream().map(PlayerModel::getPlayerName).collect(Collectors.toSet());
    }

    public void update(Player player, String currentMap, String saveName, List<Enemy> enemies, List<Item> items) {
        int playerId = playerDao.getPlayerId(saveName);
        int gameStateId = gameStateDao.getGameStateId(playerId);
        PlayerModel playerModel = new PlayerModel(player);
        playerModel.setId(playerId);
        playerModel.setPlayerName(saveName);
        GameState gameModel = new GameState(currentMap, playerModel);
        playerDao.update(playerModel);
        gameModel.setId(gameStateId);
        gameStateDao.update(gameModel);
        itemDao.deleteAllWithGameStateId(gameStateId);
        items.forEach(item -> saveItem(item, gameModel));
        enemyDao.deleteAllWithGameStateId(gameStateId);
        enemies.forEach(enemy -> saveEnemy(enemy, gameModel));

    }

}
